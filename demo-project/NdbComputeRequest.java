import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("NdbComputeRequest")
public class NdbComputeRequest implements Processor {

    public void process(Exchange exchange) throws Exception {

        Gson gson = new Gson();

        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        Map<String, Object> issue = (Map<String, Object>) respBody.get("issue");
        Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
        Map<String, Object> issueType = (Map<String, Object>) fields.get("issuetype");
        String key = (String) issue.get("key");
        String issueTypeName = (String) issueType.get("name");

        String ndbProfileName = (String) fields.get("customfield_10118");

        Double cpuDouble = (Double) fields.get("customfield_10119");
        Integer cpu = cpuDouble != null ? cpuDouble.intValue() : null;

        Double vCpuDouble = (Double) fields.get("customfield_10120");
        Integer vCpu = vCpuDouble != null ? vCpuDouble.intValue() : null;

        Double memoryDouble = (Double) fields.get("customfield_10121");
        Integer memory = memoryDouble != null ? memoryDouble.intValue() : null;

        System.out.println("Name : " + ndbProfileName);
        System.out.println("cpu : " + cpu);
        System.out.println("vcpu : " + vCpu);
        System.out.println("memory : " + memory);
        System.out.println("issue key : " + key);


        String execArgs = String.format(
            "main.yml -e {'ndb_ip':'%s','ndb_port':'%s','basic_auth':'%s','cpu':'%s','core_per_cpu':'%s','memory':'%s','profile_name':'%s','profile_desc':'%s'} -vvvv",
            "10.8.135.235", // ndb_ip
            "443",          // ndb_port
            "YWRtaW46SGVsaW9zMTIzIQ==", // basic_auth
            cpu,            // cpu
            vCpu,            // core_per_cpu
            memory,            // memory
            ndbProfileName, // profile_name
            "ndb-from-jira"  // profile_desc
        );

        exchange.getIn().setHeader("execArgs", execArgs);
        exchange.setProperty("issueKey", key);

    }
}