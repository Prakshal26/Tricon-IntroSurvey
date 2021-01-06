package handlers;

import database.PostgreConnect;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pojo.CountryData;


public class IsSectionHandler {

    static int var = 1;
    public static void handleSubSection(NodeList nodeList) {

    }

//    public static void handleIsSection(Element element, int parentId, int insertAtId) {
//
//
//        NodeList nodeList = element.getChildNodes();
//        StringBuilder stringBuilder = new StringBuilder();
//        Country country = new Country();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                Element subElement = (Element) node;
//                if (subElement.getTagName().equalsIgnoreCase("HEADING")) {
//                    country.setHeading(subElement.getTextContent());
//                }
//                if (subElement.getTagName().equalsIgnoreCase("P")) {
//                    stringBuilder.append(subElement.getTextContent());
//                }
//                if (subElement.getTagName().equalsIgnoreCase("IS-SECTION")) {
//                   // country.setParent_id(id);
//                    handleIsSection(subElement, insertAtId,insertAtId+1);
//                }
//            }
//        }
//        country.setDescription(stringBuilder.toString());
//        country.setParent_id(parentId);
//        country.setEntryId(insertAtId);
//        PostgreConnect.insertCountry(country);
//    }

    static void handleGeneralList(NodeList nodeList, StringBuilder stringBuilder) {

        for (int i=0;i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                stringBuilder.append(node.getNodeValue().trim());
            }
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element subElement = (Element) node;
                if (subElement.getTagName().equalsIgnoreCase("ITEM-NAME")) {
                    stringBuilder.append("<b>");
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("</b>");
                    stringBuilder.append("<br>");
                }
                if (subElement.getTagName().equalsIgnoreCase("ITEM")) {
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("<br>");
                }
                if (subElement.getTagName().equalsIgnoreCase("I")) {
                    stringBuilder.append("<i> ");
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("</i>");
                }
                if (subElement.hasChildNodes() && subElement.getTagName()!="I" &&
                        subElement.getTagName()!="ITEM") {
                    handleGeneralList(subElement.getChildNodes(),stringBuilder);
                }
            }
        }
    }

    public static void handleParagraph(NodeList nodeList, StringBuilder stringBuilder) {

        for (int i=0;i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType()==Node.TEXT_NODE) {
                stringBuilder.append(node.getNodeValue().trim());
            }
            if (node.getNodeType()==Node.ELEMENT_NODE) {
                Element subElement = (Element) node;
                if (subElement.getTagName().equalsIgnoreCase("I")) {
                    stringBuilder.append("<i> ");
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("</i>");
                }
                if (subElement.getTagName().equalsIgnoreCase("B")) {
                    stringBuilder.append("<b> ");
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("</b>");
                }
                if (subElement.getTagName().equalsIgnoreCase("XR")) {
                    if (subElement.hasAttribute("REF")) {
                        stringBuilder.append(" ");
                        stringBuilder.append("<a href =\"https://www.europaworld.com/entry/");
                        stringBuilder.append(subElement.getAttribute("REF"));
                        stringBuilder.append("\">");
                        stringBuilder.append(subElement.getTextContent());
                        stringBuilder.append("</a>");
                    }
                }
                if (subElement.getTagName().equalsIgnoreCase("SC")) {
                    stringBuilder.append("<span style=\"font-variant:small-caps; font-weight:bold\">");
                    stringBuilder.append(" ");
                    stringBuilder.append(subElement.getTextContent());
                    stringBuilder.append("</span>");
                }
                if (subElement.hasChildNodes() && subElement.getTagName()!="B" &&
                        subElement.getTagName()!="I" &&
                        subElement.getTagName()!="XR" &&
                        subElement.getTagName()!="SC" &&
                        subElement.getTagName()!="PERSON-NAME") {
                    handleParagraph(subElement.getChildNodes(),stringBuilder);
                }
            }
        }
    }

    public static void handleIsSection(Element element, int parentId, int countryId) {

        int insertedId = 0;
        boolean inserted = false;
        NodeList nodeList = element.getChildNodes();
        StringBuilder stringBuilder = new StringBuilder();
        CountryData countryData = new CountryData();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element subElement = (Element) node;
                if (subElement.getTagName().equalsIgnoreCase("HEADING")) {
                    countryData.setHeading(subElement.getTextContent());
                }
                if (subElement.getTagName().equalsIgnoreCase("PUBLIC-HOLIDAYS")) {
                    PublicHolidayHandler.handlePublicHoliday(subElement.getChildNodes(),stringBuilder);
                }
                if (subElement.getTagName().equalsIgnoreCase("GEN-LIST")) {
                    //more changes needed in it
                    handleGeneralList(subElement.getChildNodes(), stringBuilder);
                }
                if (subElement.getTagName().equalsIgnoreCase("P")) {
                    handleParagraph(subElement.getChildNodes(), stringBuilder);
                }
                if (subElement.getTagName().equalsIgnoreCase("IS-SECTION")) {
                    countryData.setDescription(stringBuilder.toString());
                    countryData.setParent_id(parentId);
                    countryData.setCountry_id(countryId);
                    if (!inserted) {
                        insertedId = PostgreConnect.insertCountryData(countryData);
                        inserted = true;
                    }
                   handleIsSection(subElement, insertedId, countryId);
                }
            }
        }
        if (!inserted) {
            countryData.setDescription(stringBuilder.toString());
            countryData.setParent_id(parentId);
            countryData.setCountry_id(countryId);
            insertedId = PostgreConnect.insertCountryData(countryData);
        }
    }
}
