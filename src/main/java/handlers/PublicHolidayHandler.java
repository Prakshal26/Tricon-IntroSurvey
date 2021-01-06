package handlers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PublicHolidayHandler {

    static void handleHoliday(NodeList nodeList, StringBuilder stringBuilder) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("DATE")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(element.getTextContent());
                }
                if (element.getTagName().equalsIgnoreCase("HOLIDAY-NAME")) {
                    stringBuilder.append("(");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append(");");
                }
                if (element.getTagName().equalsIgnoreCase("ENDNOTEREF")) {
                    //Make * and create a link to go down.
                }
            }
        }
    }


    static void handleHolidayYear(NodeList nodeList, StringBuilder stringBuilder) {


        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("YEAR")) {
                    stringBuilder.append("<b>");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append("</b>");
                }
                if (element.getTagName().equalsIgnoreCase("HOLIDAY")) {
                    handleHoliday(element.getChildNodes(), stringBuilder);
                }
                if (element.getTagName().equalsIgnoreCase("NOTE")) {
                    stringBuilder.append(element.getTextContent());
                }
            }
        }
    }

    public static void handlePublicHoliday(NodeList nodeList, StringBuilder stringBuilder) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("P")) {
                    stringBuilder.append("<p>");
                    IsSectionHandler.handleParagraph(element.getChildNodes(),stringBuilder);
                    stringBuilder.append("</p>");
                }
                if (element.getTagName().equalsIgnoreCase("HOLIDAY-YEAR")) {
                    handleHolidayYear(element.getChildNodes(), stringBuilder);
                }
                if (element.getTagName().equalsIgnoreCase("NOTE")) {
                    stringBuilder.append("<p>");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append("</p>");
                }
                if (element.getTagName().equalsIgnoreCase("ENDNOTE")) {
                    stringBuilder.append("<p>");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append("</p>");
                }
            }
        }
    }
}

