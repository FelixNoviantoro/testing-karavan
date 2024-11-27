import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("NdbGetProfileId")
public class NdbGetProfileId implements Processor {

    public void process(Exchange exchange) throws Exception {

        Gson gson = new Gson();
        
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        System.out.println("======================================================= NdbGetProfileId");
        System.out.println("respBody NdbGetProfileId : " + gson.toJson(respBody));
        String id = (String) respBody.get("id");

        Map<String, Object> dataReqMap = exchange.getProperty("dataReqMap", Map.class);
        System.out.println("dataReqMap : " + gson.toJson(dataReqMap));

        System.out.println((String) dataReqMap.get("ndbInstanceName"));
        System.out.println((String) dataReqMap.get("softwareProfileId"));
        System.out.println((String) dataReqMap.get("softwareProfileVersionId"));
        System.out.println(id); // Assuming `id` is a variable in scope
        System.out.println((String) dataReqMap.get("networkProfileId"));
        System.out.println((String) dataReqMap.get("paramProfileId"));
        System.out.println((Integer) dataReqMap.get("databasePort"));
        System.out.println((Integer) dataReqMap.get("databaseStorage"));
        System.out.println((String) dataReqMap.get("dbName"));
        System.out.println((String) dataReqMap.get("databasePassword"));
        System.out.println((String) dataReqMap.get("vmName"));
        System.out.println((String) dataReqMap.get("networkProfileId"));
        System.out.println((String) dataReqMap.get("vmPassword"));
        System.out.println((String) dataReqMap.get("key"));

        String execArgs = String.format(
            "main.yml -e {'ndb_ip':'%s','ndb_port':'%s','basic_auth':'%s','database_type':'%s','instance_name':'%s','database_description':'%s','softwareProfileId':'%s','softwareProfileVersionId':'%s','computeProfileId':'%s','networkProfileId':'%s','dbParameterProfileId':'%s','time_machine_description':'%s','sla_id':'%s','listener_port':'%s','database_size':'%s','dbserver_description':'%s','database_names':'%s','db_password':'%s','nx_cluster_id':'%s','vm_name':'%s','network_profile_id':'%s','vm_password':'%s','card_id':'%s'} -vvvv",
            "10.8.135.235",
            "443",
            "YWRtaW46SGVsaW9zMTIzIQ==",
            "postgres_database",
            (String) dataReqMap.get("ndbInstanceName"),
            "create-database-instance-from-camel",
            (String) dataReqMap.get("softwareProfileId"),
            (String) dataReqMap.get("softwareProfileVersionId"),
            id,
            (String) dataReqMap.get("networkProfileId"),
            (String) dataReqMap.get("paramProfileId"),
            "testing-instance-ansible_TM",
            "4d9dcd6d-b6f8-47f0-8015-9e691c1d3cf4",
            (Integer) dataReqMap.get("databasePort"),
            (Integer) dataReqMap.get("databaseStorage"),
            "testing-desc",
            (String) dataReqMap.get("dbName"),
            (String) dataReqMap.get("databasePassword"),
            "9a82460f-fb0b-4fa4-8359-a4f75788eaca",
            // sshKey,  // Sanitized SSH key with spaces preserved
            (String) dataReqMap.get("vmName"),
            (String) dataReqMap.get("networkProfileId"),
            (String) dataReqMap.get("vmPassword"),
            (String) dataReqMap.get("key")
        );

        // Set the dynamically built args in the header
        exchange.getIn().setHeader("execArgs", execArgs);

    }
}