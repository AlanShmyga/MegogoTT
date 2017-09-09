package utils;

import data_objects.Program;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static utils.DateFormatter.formatDate;

public class XMLHelper {

    private static Document parseXmlFromString(String source)
            throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(source));

        return dBuilder.parse(inputSource);
    }

    public static List<Program> getReferenceProgramsFrom(String source)
            throws IOException, SAXException, ParserConfigurationException {

        Document responseDoc = parseXmlFromString(source);
        NodeList programs = responseDoc.getElementsByTagName("programme");

        List<Program> providerProgramList = new ArrayList<>();

        for(int i = 0; i < programs.getLength(); i++) {
            Program program = Program
                    .builder()
                    .title(responseDoc.getElementsByTagName("title").item(i).getTextContent())
                    .start(programs.item(i).getAttributes().getNamedItem("start").getNodeValue())
                    .end(programs.item(i).getAttributes().getNamedItem("start").getNodeValue())
                    .build();

            providerProgramList.add(program);
        }

        return providerProgramList;
    }
}
