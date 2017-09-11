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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static utils.ProgramHelper.filterListOfProgramsByDate;

public class XMLHelper {

    private static Document parseXmlFromString(String source)
            throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(source));

        return dBuilder.parse(inputSource);
    }

    public static List<Program> getReferenceProgramsFrom(String source, String channelName, LocalDateTime currentTime)
            throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        Document responseDoc = parseXmlFromString(source);
        String xpathExpression = "//programme[@channel='" + getChannelId(responseDoc, channelName) + "']";
        NodeList programs = (NodeList) findByXpathInDocument(responseDoc, xpathExpression);

        List<Program> providerProgramList = new ArrayList<>();

        for(int i = 0; i < programs.getLength(); i++) {
            String startTime = programs.item(i).getAttributes().getNamedItem("start").getNodeValue();
            String stopTime = programs.item(i).getAttributes().getNamedItem("stop").getNodeValue();

            Program program = Program
                    .builder()
                    .title(responseDoc.getElementsByTagName("title").item(i).getTextContent())
                    .programStartTime(DateHelper.parseDateTimeFromReference(startTime))
                    .programEndTime(DateHelper.parseDateTimeFromReference(stopTime))
                    .build();

            providerProgramList.add(program);
        }

        return filterListOfProgramsByDate(providerProgramList, currentTime);
    }

    private static String getChannelId(Document responseDoc, String channelName) throws XPathExpressionException {
        String xpathExpression = "//channel[child::display-name[text()='" + channelName + "']]/@id";

        return (String) findByXpathInDocument(responseDoc, xpathExpression);
    }

    private static Object findByXpathInDocument(Document source, String xpathExpression)
            throws XPathExpressionException {

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathExpression);

        return expr.evaluate(source, XPathConstants.NODESET);
    }
}
