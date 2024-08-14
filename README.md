<h1>MusicXMLToIRealLib</h1>

<h2> 1. The project</h2>

This is a Java library for converting uncompressed <code>.musicxml</code> to <code>.html</code> for [IReal Pro](https://www.irealpro.com).

<h2>2. Usage</h2>
The <code>Converter</code> class is capable of reading a file from a path and converting it to an IReal Pro <code>Chart</code>.<br>
You can use it in your code as follows:

    File file = new File("path/to/file.musicxml");
    Chart chart = Converter.convert(FromFormat.MUSICXML, ToFormat.IREAL_PRO, file);

This code converts and builds each part of a partwise <code>.musicxml</code> into a <code>.html</code> for IReal Pro. 
A chart can be exported as follows:
    
    File file = new File("path/to/file.musicxml");
    Converter.convert(FromFormat.MUSICXML, ToFormat.IREAL_PRO, file).export();


Full documentation: see <b>4.</b>

<h2>3. Dependencies</h2>

This project needs [JDOM v2.0.6.1](https://mvnrepository.com/artifact/org.jdom/jdom2/2.0.6.1) to run.

<h2>4. Documentation</h2>

This documentation is incomplete, but includes the most important methods for converting files.

<h3><i>interface</i> core.exportable.Exportable</h3>
<b><i>methods:</i></b>
<ul>
    <li>
    <code>default void export();</code><br>
    export this exportable to a file into the users working directory.
    </li>
    <li>
    <code>void export(String path);</code><br>
    export this exportable to a file in the directory specified by <code>path</code>.
    this should be implemented by the inheritor from Exportable.
    </li>
</ul>

<h3><i>enum</i> core.FromFormat</h3>
<b><i>values:</i></b>
<ul>
    <li>
    <code>MUSICXML</code>
    </li>
</ul>
<b><i>methods:</i></b>
<ul>
    <li>
    <code>public SongReader reader();</code><br>
    provides an implementation for SongReader that can read the file format associated with the enum value.
    </li><br>
</ul>

<h3><i>enum</i> core.ToFormat</h3>
<b><i>values:</i></b>
<ul>
    <li>
    <code>IREAL_PRO</code>
    </li>
</ul>
<b><i>methods:</i></b>
<ul>
    <li>
    <code>public SongConverter converter();</code><br>
    provides an implementation for SongConverter that can convert a Song to the associated enum value.
    </li><br>
</ul>

<h3><i>class</i> core.Converter</h3>
<b><i>methods:</i></b>
<ul>
    <li>
    <code>public static Exportable convert(FromFormat from, ToFormat to, File file);</code><br>
    convert a file from a format to a format.
    Returns an Exportable
    </li><br>
    <li>
    <code>public static void convertThenConvert(FromFormat from, ToFormat to, File file);</code><br>
    export a file from a format to a format in the current working directory.
    </li><br>
<li>
    <code>public static void convertThenConvert(FromFormat from, ToFormat to, File file, String path);</code><br>
    export a file from a format to a format in the directory specified by <code>path</code>.
    </li><br>
</ul>
