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

    static void match(Element element, CountryList countryList, int countryId) {

        String tagName = element.getTagName();
        switch (tagName) {

            case "IS-SECTION":
                IsSectionHandler.handleIsSection(element, 0, countryId);
                break;
            default:
                break;
        }
    }

    static void parseFiles(Document doc) {

        doc.getDocumentElement().normalize();
        Node entryNode = doc.getDocumentElement();

        CountryList countryList = new CountryList();
        NodeList nodeList = entryNode.getChildNodes();

        Element entryElement = (Element) entryNode;

        if (entryElement.hasAttribute("ISO")) {
            countryList.setAltHeading(entryElement.getAttribute("ISO"));
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
                ElementParse.match(element, countryList, countryId);
            }
        }
    }
}

