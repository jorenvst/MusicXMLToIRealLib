package util;

import org.jdom2.Element;

import java.util.Arrays;
import java.util.List;

public class JDOMUtils {

    /**
     * utility class for performing operations on XML files using JDOM
     */
    public JDOMUtils() {

    }

    /**
     * get the text of a JDOM Element
     * @param element the element to get its children from
     * @param children the hierarchy to search through
     * @return the text of the Element if it exists, else it returns null
     */
    public static String getChildTextIfExists(Element element, String... children) {
        Element child = getChildIfExists(element, children);
        if (child == null) {
            return null;
        } else {
            return child.getText();
        }
    }

    /**
     * get a JDOM Element if it exists
     * @param element the element to get its children from
     * @param children the hierarchy to search through
     * @return the Element if it exists, else it returns null
     */
    public static Element getChildIfExists(Element element, String... children) {
        return getChildIfExists(element, 0, children);
    }

    /**
     * get a list of children on the lowest level
     * @param element the element to get its children from
     * @param children the hierarchy to search through. form the last child name is a list requested
     * @return a list of Elements, this list is empty if it doesn't exist
     */
    public static List<Element> getChildrenIfExists(Element element, String... children) {
        return getChildIfExists(element, Arrays.copyOfRange(children, 0, children.length - 1)).getChildren(children[children.length - 1]);
    }

    /**
     * get a child if it exists and there's an attribute with a specific value
     * @param element the element to get its children from
     * @param attributeName the name of the attribute that should be checked
     * @param attributeValue the expected value of the attribute that should be checked
     * @param children the hierarchy to search through, the last child is checked to see if it has the correct attribute and value
     * @return an Element if it is found, else null
     */
    public static Element getChildWithAttribute(Element element, String attributeName, String attributeValue, String... children) {
        List<Element> candidates = getChildrenIfExists(element, children).stream().filter(e -> e.getAttributeValue(attributeName).equals(attributeValue)).toList();
        if (candidates.isEmpty()) {
            return null;
        } else {
            return candidates.getFirst();
        }
    }

    /**
     * recursive search to find if an Element exists
     * @param element the element to get its children from
     * @param index current level of recursion
     * @param children the children to search in the hierarchy
     * @return an Element if found, else null
     */
    private static Element getChildIfExists(Element element, int index, String... children) {
        if (element == null) return null;
        if (children.length == index) return element;
        return getChildIfExists(element.getChild(children[index]), index + 1, children);
    }
}
