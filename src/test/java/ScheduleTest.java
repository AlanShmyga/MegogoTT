import data_objects.Program;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.get;
import static utils.DateHelper.parseDateTimeFromSynchronizer;
import static utils.JsonHelper.getBroadcastProgramsFrom;
import static utils.JsonHelper.getCurrentDateTimeFromSynchronizer;
import static utils.XMLHelper.getReferenceProgramsFrom;

public class ScheduleTest {

    private final String providerRequestUrl =  "http://www.vsetv.com/export/megogo/epg/3.xml";
    private final String broadcastRequestUrl = "http://epg.megogo.net/channel?external_id=295";
    private final String currentTimeRequestUrl = "http://epg.megogo.net/time";

    @Test
    public void broadcastScheduleEqualsToProviderSchedule()
            throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {

        String desiredChannelName = "1+1";

        LocalDateTime currentDateTime = parseDateTimeFromSynchronizer(
                getCurrentDateTimeFromSynchronizer(get(currentTimeRequestUrl).asString()));
        String providerResponse = get(providerRequestUrl).asString();
        String broadcastResponse = get(broadcastRequestUrl).asString();

        List<Program> referenceSchedule =
                getReferenceProgramsFrom(providerResponse, desiredChannelName, currentDateTime);
        List<Program> broadcastSchedule =
                getBroadcastProgramsFrom(broadcastResponse, desiredChannelName, currentDateTime);

        assertThat(broadcastSchedule).isEqualTo(referenceSchedule);
    }
}
