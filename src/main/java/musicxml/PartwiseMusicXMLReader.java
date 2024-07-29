package musicxml;

import music.Part;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.IgnoreDTDEntityResolver;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class PartwiseMusicXMLReader {

    public Collection<Part> readParts(String path) {
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            return builder.build(path).getRootElement().getChildren("part").stream()
                    .map(part -> new PartBuilder(part).build()).collect(Collectors.toSet());
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("could not read " + path, e);
        }
    }
}
