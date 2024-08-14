package util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class IgnoreDTDEntityResolver implements EntityResolver {

    /**
     * class for ignoring DTDs
     * this is done because the DTD MuseScore provides is deprecated and doesn't work anymore
     */
    public IgnoreDTDEntityResolver() {

    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        if (systemId.contains("partwise.dtd")) {
            return new InputSource(new StringReader(""));
        } else {
            return null;
        }
    }
}

