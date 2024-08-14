package util;

import org.jdom2.Element;

import java.util.Arrays;
import java.util.List;

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

    public static List<Element> getChildrenIfExists(Element element, String... children) {
        return getChildIfExists(element, Arrays.copyOfRange(children, 0, children.length - 1)).getChildren(children[children.length - 1]);
    }

    public static Element getChildWithAttribute(Element element, String attributeName, String attributeValue, String... children) {
        List<Element> candidates = getChildrenIfExists(element, children).stream().filter(e -> e.getAttributeValue(attributeName).equals(attributeValue)).toList();
        if (candidates.isEmpty()) {
            return null;
        } else {
            return candidates.getFirst();
        }
    }

    private static Element getChildIfExists(Element element, int index, String... children) {
        if (element == null) return null;
        if (children.length == index) return element;
        return getChildIfExists(element.getChild(children[index]), index + 1, children);
    }
}
