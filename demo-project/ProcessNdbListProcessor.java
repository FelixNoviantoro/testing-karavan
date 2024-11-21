import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("ProcessNdbListProcessor")
public class ProcessNdbListProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();

        ArrayList<Map<String, Object>> response = exchange.getIn().getBody(ArrayList.class);
        System.out.println("=================== List");
        System.out.println(gson.toJson(response));

        Map<String, Object> ndbCallback = exchange.getProperty("ndbCallback", Map.class);
        String vmName = exchange.getProperty("vmName", String.class);
        String ipAddress = "";
        String databaseName = "";

        for (Map<String, Object> n : response) {
            System.out.println(n.get("name") + " : " + vmName);
            if (vmName.equals(n.get("name"))) {
                List<String> ipAddressList = (List<String>) n.get("ipAddresses");
                ipAddress = ipAddressList.get(0);
                break; // Exit the loop once the match is found
            }
        }

        System.out.println("=================== IPADDRESS");
        System.out.println(ipAddress);
        System.out.println("=================== IPADDRESS");

        exchange.getIn().setHeader("IssueKey", ndbCallback.get("cardId"));

        String multiLineString = "Vm Name : "+ vmName +"\n" +
                         "IP : "+ ipAddress +"\n" +
                         "Port : "+ ndbCallback.get("port") +"\n" +
                         "VM Username : "+ ndbCallback.get("vmUsername") +"\n" +
                        //  "VM Password : "+ ndbCallback.get("vmPass") +"\n" +
                         "DB Username : "+ ndbCallback.get("dbUsername") +"\n";
                        //  "DB Password : "+ ndbCallback.get("dbPass") +"\n";
        exchange.getIn().setBody(multiLineString);
    }
}