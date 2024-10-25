import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Configuration
@BindToRegistry("PalaybookAlert")
public class PalaybookAlert implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("Hello World");

        // Build the ansible-playbook args string dynamically
        String execArgs = String.format(
            "main.yml -e {'pc_ip':'%s','basic_auth':'%s','webhook_uuid':'%s','card_id':'%s','vm_uuid':'%s','alert_uuid':'%s'}",
            "10.8.130.168",
            "YWRtaW46bnV0NG5peFBAc3N3MHJk",
            "29b738ff-f048-449b-50c5-c34d1f61119f",
            projectName,
            vmTypeValue,
            appProfileReferenceId
        );

        // Set the dynamically built args in the header
        exchange.getIn().setHeader("execArgs", execArgs);
    }
}