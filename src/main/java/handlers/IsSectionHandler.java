package handlers;

import database.PostgreConnect;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pojo.Country;


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

    public static void handleIsSection(Element element, int parentId) {


        int insertedId = 0;
        boolean inserted = false;
        NodeList nodeList = element.getChildNodes();
        StringBuilder stringBuilder = new StringBuilder();
        Country country = new Country();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element subElement = (Element) node;
                if (subElement.getTagName().equalsIgnoreCase("HEADING")) {
                    country.setHeading(subElement.getTextContent());
                }
                if (subElement.getTagName().equalsIgnoreCase("P")) {
                    stringBuilder.append(subElement.getTextContent());
                }
                if (subElement.getTagName().equalsIgnoreCase("IS-SECTION")) {
                    country.setDescription(stringBuilder.toString());
                    country.setParent_id(parentId);
                    if (!inserted) {
                        insertedId = PostgreConnect.insertCountry(country);
                        inserted = true;
                    }
                   handleIsSection(subElement, insertedId);
                }
            }
        }
        if (!inserted) {
            country.setDescription(stringBuilder.toString());
            country.setParent_id(parentId);
            insertedId = PostgreConnect.insertCountry(country);
        }
    }

}
