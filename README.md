<h1>MusicXMLToIRealLib</h1>

<h2> 1. The project</h2>

This is a Java library for converting <code>.musicxml</code> to <code>.html</code> for [IReal Pro](https://www.irealpro.com).

<h2>2. Usage</h2>
The <code>MusicXMLConverter</code> class is capable of reading a file from a path and converting it to an <code>IRealProDocument</code>.<br>
You can use it in your code as follows:

    MusicXMLConverter converter = new MusicXMLConverter();
    List<IRealProDocument> docs = converter.convert("path/to/file.musicxml");
    
    for (IRealProDocument doc : docs) {
        doc.build();
    }

This code converts and builds each part of a partwise <code>.musicxml</code> into a <code>.html</code> for IReal Pro. The files are built in the directory from where the application was launched.<br>
Full documentation: see next chapter.

<h2>3. Documentation</h2>

<h3>MusicXMLReader</h3>

<b><i>methods:</i></b>

<ul>
    <li>
    <code>public List&lt;Song> readSongs(String path)</code><br>
    read each part of a partwise <code>.musicxml</code> into a <code>Song</code>.
    </li><br>
    <li>
    <code>public Song readSongPart(String path, int partNum)</code><br>
    read a part by index of a partwise <code>.musicxml</code> into a <code>Song</code>.
    </li><br>
</ul>


<h3>MusicXMLConverter</h3>

<b><i>methods:</i></b>
<ul>
    <li>
    <code>public List&lt;IRealProDocument> convert(String path)</code><br>
    convert each part of a partwise <code>.musicxml</code> into a <code>IRealProDocument</code>.
    </li><br>
    <li>
    <code>public IRealProDocument convertPart(String path, int part)</code><br>
    convert a part by index from a partwise <code>.musicxml</code> into a <code>IRealProDocument</code>.
    </li><br>
    <li>
    <code>public IRealProDocument convertPart(String path)</code><br>
    convert the first part from a partwise <code>.musicxml</code> into a <code>IRealProDocument</code>.<br>
    Is equivalent to <code>convertPart(path, 0)</code>.
    </li>
</ul>


<h3>IRealProDocument</h3>

<b><i>methods:</i></b>

<ul>
    <li>
    <code>public void build(String path)</code><br>
    save the <code>IRealProDocument</code> to <code>.html</code> for IReal Pro.<br>
    The file is saved in the directory where the path points to.
    </li><br>
    <li>
    <code>public void build()</code><br>
    save the <code>IRealProDocument</code> to <code>.html</code> for IReal Pro.<br>
    The file is saved to the directory where the application was launched from.
    </li><br>
</ul>
