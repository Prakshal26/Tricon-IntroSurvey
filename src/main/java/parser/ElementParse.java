package parser;

import database.PostgreConnect;
import handlers.IsSectionHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pojo.Country;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ElementParse {

    static void match(Element element, Country country, StringBuilder generalSectionBuilder, StringBuilder introductionBuilder, StringBuilder chronListBuilder) {

        String tagName = element.getTagName();
        switch (tagName) {

            case "HEADING":
                country.setHeading(element.getTextContent());
                break;
            case "P":
                introductionBuilder.append("<p>");
             //   GeneralSection.handleParagraph(element.getChildNodes(),introductionBuilder);
                introductionBuilder.append("</p>");
                break;
            case "IS-SECTION":
                IsSectionHandler.handleIsSection(element, 0);
                generalSectionBuilder.append("<br>");
                break;
            default:
                break;
        }
    }

    static void parseFiles(Document doc) {

        doc.getDocumentElement().normalize();
        Node entryNode = doc.getDocumentElement();

        Country country = new Country();
        NodeList nodeList = entryNode.getChildNodes();

        Element entryElement = (Element) entryNode;

        StringBuilder generalSectionBuilder = new StringBuilder();
        StringBuilder introductionBuilder = new StringBuilder();
        StringBuilder chronListBuilder = new StringBuilder();

        for (int i=0; i<nodeList.getLength();i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)nNode;
                ElementParse.match(element, country, generalSectionBuilder, introductionBuilder, chronListBuilder);
            }
        }
    }
}

