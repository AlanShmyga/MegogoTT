package utils;

import data_objects.Program;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLHelper {

    private static Document parseXmlFromString(String source)
            throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(source));

        return dBuilder.parse(inputSource);
    }

    public static List<Program> getReferenceProgramsFrom(String source)
            throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        Document responseDoc = parseXmlFromString(source);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(
                "//programme[@start='2017-09-04 12:20:00' and @stop='2017-09-04 13:50:00']");
        NodeList programs = (NodeList)expr.evaluate(responseDoc, XPathConstants.NODESET);

        List<Program> providerProgramList = new ArrayList<>();

        for(int i = 0; i < programs.getLength(); i++) {
            Program program = Program
                    .builder()
                    .title(responseDoc.getElementsByTagName("title").item(i).getTextContent())
                    .start(programs.item(i).getAttributes().getNamedItem("start").getNodeValue())
                    .end(programs.item(i).getAttributes().getNamedItem("stop").getNodeValue())
                    .build();

            providerProgramList.add(program);
        }

        return providerProgramList;
    }
}
