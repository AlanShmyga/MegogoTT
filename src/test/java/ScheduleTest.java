import data_objects.Program;
import org.junit.Test;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.get;
import static utils.DateFormatter.formatDate;
import static utils.JsonHelper.getBroadcastProgramsFrom;
import static utils.XMLHelper.getReferenceProgramsFrom;

public class ScheduleTest {

    private final String providerRequestUrl =  "http://www.vsetv.com/export/megogo/epg/3.xml";
    private final String broadcastRequestUrl = "http://epg.megogo.net/channel?external_id=295";

    @Test
    public void broadcastScheduleEqualsToProviderSchedule()
            throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {

        List<Program> referenceSchedule = getReferenceProgramsFrom(get(providerRequestUrl).asString());
        List<Program> broadcastSchedule = getBroadcastProgramsFrom(get(broadcastRequestUrl).asString());
        formatDate(broadcastSchedule);

        assertThat(broadcastSchedule).isEqualTo(referenceSchedule);
    }
}
