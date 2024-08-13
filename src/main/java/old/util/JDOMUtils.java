package old.util;

import org.jdom2.Element;

/**
 * utility class for performing operations on XML files using JDOM
 */
public class JDOMUtils {

    public static String getChildTextIfExists(Element element, String... children) {
        Element child = getChildIfExists(element, children);
        if (child == null) {
            return null;
        } else {
            return child.getText();
        }
    }

    public static Element getChildIfExists(Element element, String... children) {
        return getChildIfExists(element, 0, children);
    }

    private static Element getChildIfExists(Element element, int index, String... children) {
        if (element == null) return null;
        if (children.length == index) return element;
        return getChildIfExists(element.getChild(children[index]), index + 1, children);
    }
}
