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

        String databaseType = "postgres_database";

        String ndbInstanceName = (String) fields.get("customfield_10109");
        String databaseUsername = "postgres";
        String databasePassword = (String) fields.get("customfield_10104");
        String vmPassword = (String) fields.get("customfield_10084");
        String vmName = (String) fields.get("customfield_10093");
        String dbName = "postgres";

        Double databasePortDouble = (Double) fields.get("customfield_10106");
        Integer databasePort = databasePortDouble != null ? databasePortDouble.intValue() : null;

        Double databaseStorageDouble = (Double) fields.get("customfield_10096");
        Integer databaseStorage = databaseStorageDouble != null ? databaseStorageDouble.intValue() : null;

        Map<String, Object> softwareProfileMap = (Map<String, Object>) fields.get("customfield_10111");
        String softwareProfile = (String) softwareProfileMap.get("value");
        String softwareProfileVersionId = "a70c3202-b7a4-40cb-99a9-ec14e057fe4f";

        Map<String, Object> networkProfileMap = (Map<String, Object>) fields.get("customfield_10114");
        String networkProfile = (String) networkProfileMap.get("value");

        Map<String, Object> paramProfileMap = (Map<String, Object>) fields.get("customfield_10116");
        String paramProfile = (String) paramProfileMap.get("value");

        String softwareProfileId = "";
        String computeProfileId = "";
        String networkProfileId = "";
        String paramProfileId = "";

        switch(softwareProfile) {
            case "POSTGRES_15.6_ROCKY_LINUX_8_OOB":
                softwareProfileId = "246bc618-5afd-4811-83fc-95889849aafd";
                break;
            case "POSTGRES_15.6_HA_ENABLED_ROCKY_LINUX_8_OOB":
                softwareProfileId = "14936a4f-c36c-440c-b2e7-701d62c67af7";
                break;
            case "POSTGRES_10.23_HA_ENABLED_ROCKY_LINUX_8_OOB":
                softwareProfileId = "1595b90d-edbc-4c4d-b0c9-89d012ade8d3";
                break;
            case "MARIADB_10.11_ROCKY_LINUX_8_OOB":
                softwareProfileId = "e60812dc-0713-4d41-88aa-d5b7503366ec";
                break;
            case "MYSQL_8.0_ROCKY_LINUX_8_OOB":
                softwareProfileId = "c3ba81a7-d442-4dbc-bb1a-ed2328fd69ad";
                break;
        }

        switch(networkProfile) {
            case "DEFAULT_OOB_ORACLE_NETWORK":
                networkProfileId = "85f308b6-5026-4096-999c-66dcf0928c94";
                break;
            case "DEFAULT_OOB_POSTGRESQL_NETWORK":
                networkProfileId = "edb42bdb-a38f-4a6f-a5b5-fb8d90acd311";
                break;
            case "DEFAULT_OOB_SQLSERVER_NETWORK":
                networkProfileId = "0615ccd7-0c6b-4dce-9b19-afb648dd9517";
                break;
            case "DEFAULT_OOB_MARIADB_NETWORK":
                networkProfileId = "59a5e4a4-d670-4712-a8fa-8c5f0f6dc91f";
                break;
            case "DEFAULT_OOB_MYSQL_NETWORK":
                networkProfileId = "bfc63fad-1952-417a-abcf-8f03a0c5eba5";
                break;
            case "DEFAULT_OOB_MONGODB_NETWORK":
                networkProfileId = "a58043e2-3467-4aa7-b7e9-0ec0f54a8814";
                break;
        }

        switch(paramProfile) {
            case "DEFAULT_ORACLE_PARAMS":
                paramProfileId = "33f8a6fd-d7ad-49f3-899d-068f20fdf74e";
                break;
            case "DEFAULT_POSTGRES_PARAMS":
                paramProfileId = "c380bc5d-577b-4cbc-987f-908e5b3ea37a";
                break;
            case "DEFAULT_POSTGRES_HA_PARAMS":
                paramProfileId = "26ab28f3-13cf-4081-9d26-0fb2b29f3c92";
                break;
            case "DEFAULT_SQLSERVER_INSTANCE_PARAMS":
                paramProfileId = "e8d97a47-e7f1-4211-b2cd-ffaa23cf9a31";
                break;
            case "DEFAULT_SQLSERVER_DATABASE_PARAMS":
                paramProfileId = "48016975-25c5-48f0-8db7-d9c3a24bdb00";
                break;
            case "DEFAULT_MARIADB_PARAMS":
                paramProfileId = "88c4be4e-b90f-40c7-8cbc-33a8ff79f73a";
                break;
            case "DEFAULT_MYSQL_PARAMS":
                paramProfileId = "b9336b60-6b72-4e4b-87de-434580876060";
                break;
            case "DEFAULT_SAPHANA_PARAMS":
                paramProfileId = "96125530-072a-4743-a48e-0c9a838dcbbc";
                break;
            case "DEFAULT_MONGODB_PARAMS":
                paramProfileId = "e49215b5-ca42-4412-ad79-dae5d367afca";
                break;
        }

        System.out.println("Name : " + ndbProfileName);
        System.out.println("cpu : " + cpu);
        System.out.println("vCpu : " + vCpu);
        System.out.println("memory : " + memory);
        System.out.println("issue key : " + key);

        Map<String, Object> dataReqMap = new HashMap<>();
        dataReqMap.put("ndbProfileName", ndbProfileName);
        dataReqMap.put("cpu", cpu);
        dataReqMap.put("vCpu", vCpu);
        dataReqMap.put("memory", memory);

        dataReqMap.put("ndbInstanceName", ndbInstanceName);
        dataReqMap.put("databaseUsername", databaseUsername);
        dataReqMap.put("databasePassword", databasePassword);
        dataReqMap.put("vmPassword", vmPassword);
        dataReqMap.put("vmName", vmName);
        dataReqMap.put("dbName", dbName);

        dataReqMap.put("databasePort", databasePort);
        dataReqMap.put("databaseStorage", databaseStorage);
        dataReqMap.put("softwareProfileVersionId", softwareProfileVersionId);

        dataReqMap.put("networkProfileId", networkProfileId);
        dataReqMap.put("softwareProfileId", softwareProfileId);
        dataReqMap.put("paramProfileId", paramProfileId);

        dataReqMap.put("key", key);

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
        exchange.setProperty("computeName", ndbProfileName);

        exchange.setProperty("dataReqMap", dataReqMap);
    }
}