import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("GetNdbComputeNameProcessor")
public class GetNdbComputeNameProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();

        Map<String, Object> reqBody = exchange.getProperty("reqBody", Map.class);

        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        Map<String, Object> issue = (Map<String, Object>) respBody.get("issue");
        Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
        Map<String, Object> issueType = (Map<String, Object>) fields.get("issuetype");
        String key = (String) issue.get("key");
        String issueTypeName = (String) issueType.get("name");

        Double cpuDouble = (Double) fields.get("customfield_10119");
        Integer cpu = cpuDouble != null ? cpuDouble.intValue() : null;

        Double vCpuDouble = (Double) fields.get("customfield_10120");
        Integer vCpu = vCpuDouble != null ? vCpuDouble.intValue() : null;

        Double memoryDouble = (Double) fields.get("customfield_10121");
        Integer memory = memoryDouble != null ? memoryDouble.intValue() : null;

        // String ndbProfileName = (String) fields.get("customfield_10118");
        String ndbProfileName = "COMPUTE_" + String.valueOf(cpu) + String.valueOf(vCpu) + String.valueOf(memory);

        exchange.setProperty("computeName", ndbProfileName);
    }
}