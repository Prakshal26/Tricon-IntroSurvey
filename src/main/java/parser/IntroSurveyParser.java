package parser;

import database.PostgreConnect;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class IntroSurveyParser {


    public static void main (String args[]) {
        try {

            PostgreConnect.connect();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File dir = new File("C:\\Users\\lenovo\\IdeaProjects\\EuropaDbWork\\XML_Files\\Test");

            File [] files = dir.listFiles();
            int insertedId = 0;
            int file_count =1;
            for(File file : files) {
                if(file.isFile() && file.getName().endsWith(".xml")) {
                    Document doc = dBuilder.parse(file);
                    System.out.println("Parsing file "+file_count);
                    file_count++;
                    doc.getDocumentElement().normalize();
                    Node entryNode = doc.getDocumentElement();
                   insertedId = ElementParse.parseFiles(doc, insertedId);
                    System.out.println(insertedId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
