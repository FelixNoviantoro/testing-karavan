import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Configuration
@BindToRegistry("StringExecFormat")
public class StringExecFormat implements Processor {

    public void process(Exchange exchange) throws Exception {
        // exchange.getIn().setBody("Hello World");
        String jsonString = "{\"pc_ip\":\"10.8.130.168\",\"bp_id\":\"9145e6c8-a25d-482a-a6f4-52cffa7bf744\", ...}";
        String finalString = "'" + jsonString + "'";
        System.out.println(finalString);
        exchange.getIn().setBody(finalString);
    }
}