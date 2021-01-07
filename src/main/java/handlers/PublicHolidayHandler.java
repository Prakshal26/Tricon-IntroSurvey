package handlers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PublicHolidayHandler {

    static void handleDate (NodeList nodeList, StringBuilder stringBuilder, HashMap<String,String> endNoteMap) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("ENDNOTEREF")) {
                    stringBuilder.append("<sup>");
                    stringBuilder.append(endNoteMap.get(element.getAttribute("IDREF")));
                    stringBuilder.append("</sup>");
                }
            }
        }
    }
    static void handleHoliday(NodeList nodeList, StringBuilder stringBuilder, HashMap<String,String> endNoteMap) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("DATE")) {
                    stringBuilder.append(" ");
                    stringBuilder.append(element.getTextContent());
                    handleDate(element.getChildNodes(),stringBuilder,endNoteMap);
                    stringBuilder.append(" ");

                }
                if (element.getTagName().equalsIgnoreCase("ENDNOTEREF")) {
                    stringBuilder.append("<sup>");
                    stringBuilder.append(endNoteMap.get(element.getAttribute("IDREF")));
                    stringBuilder.append("</sup>");
                }
                if (element.getTagName().equalsIgnoreCase("HOLIDAY-NAME")) {
                    stringBuilder.append("(");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append(");");
                }
            }
        }
    }


    static void handleHolidayYear(NodeList nodeList, StringBuilder stringBuilder, HashMap<String,String> endNoteMap) {


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
                    handleHoliday(element.getChildNodes(), stringBuilder, endNoteMap);
                }
                if (element.getTagName().equalsIgnoreCase("NOTE")) {
                    stringBuilder.append(element.getTextContent());
                }
            }
        }
    }

    public static void handlePublicHoliday(NodeList nodeList, StringBuilder stringBuilder) {


        HashMap<String,String> endNoteMap = new LinkedHashMap<>();

        //This is to store * or + in the ENdNote. Check Public Holiday for BN.IS
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node nodeEndNode = nodeList.item(j);
            if (nodeEndNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeEndNode;
                if (element.getTagName().equalsIgnoreCase("ENDNOTE")) {
                    if (element.hasAttribute("ID")) {
                        endNoteMap.put(element.getAttribute("ID"),element.getAttribute("REF-SYMBOL"));
                    }
                }
            }
        }
            for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equalsIgnoreCase("P")) {
                    IsSectionHandler.handleParagraph(element.getChildNodes(),stringBuilder);
                }
                if (element.getTagName().equalsIgnoreCase("HOLIDAY-YEAR")) {
                    handleHolidayYear(element.getChildNodes(), stringBuilder, endNoteMap);
                }
                if (element.getTagName().equalsIgnoreCase("NOTE")) {
                    stringBuilder.append("<p>");
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append("</p>");
                }
                if (element.getTagName().equalsIgnoreCase("ENDNOTE")) {
                    stringBuilder.append("<p>");
                    if (element.hasAttribute("REF-SYMBOL")) {
                        stringBuilder.append("<sup>");
                        stringBuilder.append(element.getAttribute("REF-SYMBOL"));
                        stringBuilder.append("</sup>");
                    }
                    stringBuilder.append(element.getTextContent());
                    stringBuilder.append("</p>");
                }
            }
        }
    }
}

