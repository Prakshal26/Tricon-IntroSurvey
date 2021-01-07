package parser;

import database.PostgreConnect;
import handlers.IsSectionHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pojo.CountryData;
import pojo.CountryList;

public class ElementParse {

    static int match(Element element, int parentId, int countryId) {

        String tagName = element.getTagName();
        int insertedId = 0;
        switch (tagName) {

            case "IS-SECTION":
                insertedId = IsSectionHandler.handleIsSection(element, parentId, countryId);
                break;
            default:
                break;
        }
        return insertedId;
    }

    static int parseFiles(Document doc, int insertedId) {

        doc.getDocumentElement().normalize();
        Node entryNode = doc.getDocumentElement();

        CountryList countryList = new CountryList();
        NodeList nodeList = entryNode.getChildNodes();

        Element entryElement = (Element) entryNode;

        if (entryElement.hasAttribute("ISO")) {
            countryList.setName(entryElement.getAttribute("ISO"));
        }
        if (entryElement.hasAttribute("ID")) {
            countryList.setXmlId((entryElement.getAttribute("ID")).toLowerCase());
        }

        for (int j=0;j<nodeList.getLength();j++) {
            Node altHead = nodeList.item(j);
            if (altHead.getNodeType() == Node.ELEMENT_NODE) {
                Element altHeadElement = (Element) altHead;
                if (altHeadElement.getTagName().equalsIgnoreCase("ALT-HEADING")) {
                    countryList.setAltHeading(altHeadElement.getTextContent());
                }
            }
        }

        //Insert Country and Get id.
        int countryId = PostgreConnect.insertCountryList(countryList);


        for (int i=0; i<nodeList.getLength();i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)nNode;
                insertedId = ElementParse.match(element,insertedId, countryId);
            }
        }
        return  insertedId;
    }
}

