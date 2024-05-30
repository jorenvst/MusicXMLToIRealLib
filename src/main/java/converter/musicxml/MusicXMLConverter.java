package converter.musicxml;

import converter.ireal.IRealProDocument;
import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MusicXMLConverter --- class for converting a song to an IReal Pro document
 */
public class MusicXMLConverter {

    private final MusicXMLReader reader = new MusicXMLReader();
    private final Set<String> validQualities;
    private final Properties time;
    private final Properties chords;
    private final Properties barLines;

    private final Map<Repetition, String> barLineMap;

    public MusicXMLConverter() {
        // load properties upon creation
        try (
                InputStream in1 = this.getClass().getResourceAsStream("/resources/time.properties");        // for translating time signatures
                InputStream in2 = this.getClass().getResourceAsStream("/resources/chords.properties");      // for translating chords
                InputStream in3 = this.getClass().getResourceAsStream("/resources/bar-lines.properties")    // for translating bar lines
        ) {
            // valid-alterations contains a list with all IReal Pro valid chord extensions/alterations
            Document document = new SAXBuilder().build(this.getClass().getResourceAsStream("/resources/valid-alterations.xml"));
            validQualities = document.getRootElement().getChildren("element")
                    .stream().map(Element::getText).collect(Collectors.toSet());

            time = new Properties();
            time.load(in1);

            chords = new Properties();
            chords.load(in2);

            barLines = new Properties();
            barLines.load(in3);

        } catch (IOException | JDOMException e) {
            throw new RuntimeException("Could not read the required resources", e);
        }

        // used for translating repetition for bar lines
        barLineMap = new HashMap<>();
        barLineMap.put(Repetition.NONE, "");
        barLineMap.put(Repetition.FORWARD, "-forward");
        barLineMap.put(Repetition.BACKWARD, "-backward");
    }

    /**
     * convert a file to an IReal Pro document
     * @param path the path to the musicxml file that needs to be converted
     * @return a new IReal Pro Document
     */
    public List<IRealProDocument> convert(String path) {
        // read the song from the musicxml file
        List<Song> songs = reader.readSong(path);
        List<IRealProDocument> documents = new ArrayList<>();
        for (Song song : songs) {
            documents.add(new IRealProDocument(songToURL(song), song.getTitle()));
        }
        return documents;
    }

    public IRealProDocument convertPart(String path, int part) {
        Song song = reader.readSongPart(path, part);
        return new IRealProDocument(songToURL(song), song.getTitle());
    }

    public IRealProDocument convertPart(String path) {
        Song song = reader.readSongPart(path, 0);
        return new IRealProDocument(songToURL(song), song.getTitle());
    }

    /**
     * convert a song to a valid IReal Pro url
     * @param song the song that needs to be converted
     * @return the valid IReal Pro url
     */
    private String songToURL(Song song) {
        return "irealbook://" +
                song.getTitle() + "=" +                                                 // title
                song.getComposerLastName() + " " + song.getComposerFirstName() + "=" +  // composer
                "Undefined" + "=" +                                                     // style
                song.getKey() + "=" + "n=" +                                            // key
                measuresToURL(song.getMeasures());                                      // chord progression
    }

    /**
     * translates measures of a song into the IReal Pro format
     * @param measures the measures of the song that need translating
     * @return a String of the measures in IReal Pro format
     */
    private String measuresToURL(List<Measure> measures) {

        StringBuilder builder = new StringBuilder();

        String lastChord = null;
        Measure lastMeasure = null;
        for (Measure measure : measures) {
            if (measure.hasTime()) {
                // if the key signature doesn't exist in IReal Pro, throw an exception
                if (!time.containsKey(measure.getTime().toString())) {
                    throw new RuntimeException("This time signature is invalid for IReal Pro");
                }
                builder.append(time.getProperty(measure.getTime().toString()));
            }
            // only include the measure if it is not implicit
            if (!measure.isImplicit()) {
                builder.append(barLines.getProperty(measure.getBarLineType() + barLineMap.get(measure.getRepetition())));
                if (measure.getChords().isEmpty()) {
                    if (lastMeasure != null && lastMeasure.getChords().size() == 1) {
                        // repeat last measure if this measure has no chords and last measure with a chord only had one chord
                        builder.append("x ");
                    } else if (lastChord == null) {
                        // play no chord if this is the first measure
                        builder.append("n ");
                    } else {
                        // repeat only the last chord if the previous measure with a chord had multiple chords
                        builder.append(lastChord).append(" ");
                    }
                }

                // put each measure into the IReal Pro song builder
                for (Chord chord : measure.getChords()) {
                    StringBuilder iRealChord = new StringBuilder();
                    iRealChord.append(chord.root());

                    StringBuilder quality = new StringBuilder();
                    quality.append(chords.getProperty(chord.kind()));
                    for (String alteration : chord.alterations()) {
                        quality.append(alteration);
                    }

                    if (qualityIsValid(quality.toString())) {
                        iRealChord.append(quality);
                    } else {
                        throw new RuntimeException(quality + "is an invalid quality for IReal Pro");
                    }

                    if (chord.hasBass()) {
                        iRealChord.append("/").append(chord.bass());
                    }

                    builder.append(iRealChord);
                    lastChord = iRealChord.toString();
                    lastMeasure = measure;
                    builder.append(" ");
                }
            }
        }
        builder.append("Z");
        return builder.toString();
    }

    /**
     * check if a certain quality is valid for IReal Pro
     */
    private boolean qualityIsValid(String quality) {
        return validQualities.contains(quality);
    }
}
