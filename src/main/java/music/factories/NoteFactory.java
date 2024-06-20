package music.factories;

import music.Note;
import org.jdom2.Element;

public class NoteFactory {

    public static Note buildNote(Element note) {
        return new Note(Integer.parseInt(note.getChildText("duration")), note.getChild("rest") != null);
    }
}
