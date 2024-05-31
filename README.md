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

<h3>MusicXMLTo</h3>
