import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Configuration
@BindToRegistry("NdbReq")
public class NdbReq implements Processor {

    public void process(Exchange exchange) throws Exception {
        // Gson gson = new Gson();

        // Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        // Map<String, Object> issue = (Map<String, Object>) respBody.get("issue");
        // Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
        // Map<String, Object> issueType = (Map<String, Object>) fields.get("issuetype");
        // String key = (String) issue.get("key");
        // String issueTypeName = (String) issueType.get("name");

        // String databaseType = "";

        // String ndbInstanceName = "";
        // String databaseUsername = "";
        // String databasePassword = "";
        // String vmPassword = "";
        // String vmName = "";
        // String dbName = "";

        // Integer databasePort = 5432;
        // Integer databaseStorage = 50;

        // String softwareProfile = ""; 
        // String computeProfile = "";
        // String networkProfile = ""; 
        // String paramProfile = "";

        // String softwareProfileId = "";
        // String computeProfileId = "";
        // String networkProfileId = "";
        // String paramProfileId = "";

        // String softwareProfileVersionId = "";

        // System.out.println("========================= " + issueTypeName);

        // if (issueTypeName.equals("NDB Provision")){

        //     System.out.println("masuk NDB Povision");

        //     databaseType = "postgres_database";
        //     dbName = "postgres";

        //     ndbInstanceName = (String) fields.get("customfield_10109");

        //     databaseUsername = "postgres";
        //     databasePassword = (String) fields.get("customfield_10104");
        //     vmPassword = (String) fields.get("customfield_10105");
        //     vmName = (String) fields.get("customfield_10108");

        //     Double databasePortDouble = (Double) fields.get("customfield_10106");
        //     databasePort = databasePortDouble != null ? databasePortDouble.intValue() : null;

        //     Double databaseStorageDouble = (Double) fields.get("customfield_10107");
        //     databaseStorage = databaseStorageDouble != null ? databaseStorageDouble.intValue() : null;

        //     Map<String, Object> softwareProfileMap = (Map<String, Object>) fields.get("customfield_10099");
        //     softwareProfile = (String) softwareProfileMap.get("value");
        //     softwareProfileVersionId = "a70c3202-b7a4-40cb-99a9-ec14e057fe4f";

        //     Map<String, Object> computeProfileMap = (Map<String, Object>) fields.get("customfield_10100");
        //     computeProfile = (String) computeProfileMap.get("value");

        //     Map<String, Object> networkProfileMap = (Map<String, Object>) fields.get("customfield_10101");
        //     networkProfile = (String) networkProfileMap.get("value");

        //     Map<String, Object> paramProfileMap = (Map<String, Object>) fields.get("customfield_10102");
        //     paramProfile = (String) paramProfileMap.get("value");


        // } else if (issueTypeName.equals("Postgres Provision")){

        //     System.out.println("masuk Postgres Povision");

        //     databaseType = "postgres_database";
        //     dbName = "postgres";

        //     ndbInstanceName = (String) fields.get("customfield_10109");

        //     databaseUsername = "postgres";
        //     databasePassword = (String) fields.get("customfield_10104");
        //     vmPassword = (String) fields.get("customfield_10105");
        //     vmName = (String) fields.get("customfield_10108");

        //     Double databasePortDouble = (Double) fields.get("customfield_10106");
        //     databasePort = databasePortDouble != null ? databasePortDouble.intValue() : null;

        //     Double databaseStorageDouble = (Double) fields.get("customfield_10107");
        //     databaseStorage = databaseStorageDouble != null ? databaseStorageDouble.intValue() : null;

        //     Map<String, Object> softwareProfileMap = (Map<String, Object>) fields.get("customfield_10111");
        //     softwareProfile = (String) softwareProfileMap.get("value");
        //     softwareProfileVersionId = "a70c3202-b7a4-40cb-99a9-ec14e057fe4f";

        //     Map<String, Object> computeProfileMap = (Map<String, Object>) fields.get("customfield_10100");
        //     computeProfile = (String) computeProfileMap.get("value");

        //     Map<String, Object> networkProfileMap = (Map<String, Object>) fields.get("customfield_10114");
        //     networkProfile = (String) networkProfileMap.get("value");

        //     Map<String, Object> paramProfileMap = (Map<String, Object>) fields.get("customfield_10116");
        //     paramProfile = (String) paramProfileMap.get("value");


        // } else {

        //     System.out.println("masuk Mysql Povision");

        //     databaseType = "mysql_database";
        //     dbName = "mysql";

        //     ndbInstanceName = (String) fields.get("customfield_10109");

        //     databaseUsername = "root";
        //     databasePassword = (String) fields.get("customfield_10117");
        //     vmPassword = (String) fields.get("customfield_10084");
        //     vmName = (String) fields.get("customfield_10086");

        //     Double databasePortDouble = (Double) fields.get("customfield_10106");
        //     databasePort = databasePortDouble != null ? databasePortDouble.intValue() : null;

        //     Double databaseStorageDouble = (Double) fields.get("customfield_10096");
        //     databaseStorage = databaseStorageDouble != null ? databaseStorageDouble.intValue() : null;

        //     Map<String, Object> softwareProfileMap = (Map<String, Object>) fields.get("customfield_10110");
        //     softwareProfile = (String) softwareProfileMap.get("value");
        //     softwareProfileVersionId = "3388b8d5-9746-40c4-a442-919b9a719997";

        //     Map<String, Object> computeProfileMap = (Map<String, Object>) fields.get("customfield_10100");
        //     computeProfile = (String) computeProfileMap.get("value");

        //     Map<String, Object> networkProfileMap = (Map<String, Object>) fields.get("customfield_10112");
        //     networkProfile = (String) networkProfileMap.get("value");

        //     Map<String, Object> paramProfileMap = (Map<String, Object>) fields.get("customfield_10115");
        //     paramProfile = (String) paramProfileMap.get("value");

        // }

        // String regex = "\\(([^)]+)\\)";
        
        // Pattern pattern = Pattern.compile(regex);
        // Matcher matcherCompute = pattern.matcher(computeProfile);

        // if (matcherCompute.find()) {
        //     computeProfileId = matcherCompute.group(1); 
        //     System.out.println("Extracted Compute Profile ID: " + computeProfileId);
        // } else {
        //     System.out.println("No match found");
        // }

        // switch(softwareProfile) {
        //     case "POSTGRES_15.6_ROCKY_LINUX_8_OOB":
        //         softwareProfileId = "246bc618-5afd-4811-83fc-95889849aafd";
        //         break;
        //     case "POSTGRES_15.6_HA_ENABLED_ROCKY_LINUX_8_OOB":
        //         softwareProfileId = "14936a4f-c36c-440c-b2e7-701d62c67af7";
        //         break;
        //     case "POSTGRES_10.23_HA_ENABLED_ROCKY_LINUX_8_OOB":
        //         softwareProfileId = "1595b90d-edbc-4c4d-b0c9-89d012ade8d3";
        //         break;
        //     case "MARIADB_10.11_ROCKY_LINUX_8_OOB":
        //         softwareProfileId = "e60812dc-0713-4d41-88aa-d5b7503366ec";
        //         break;
        //     case "MYSQL_8.0_ROCKY_LINUX_8_OOB":
        //         softwareProfileId = "c3ba81a7-d442-4dbc-bb1a-ed2328fd69ad";
        //         break;
        // }

        // switch(networkProfile) {
        //     case "DEFAULT_OOB_ORACLE_NETWORK":
        //         networkProfileId = "85f308b6-5026-4096-999c-66dcf0928c94";
        //         break;
        //     case "DEFAULT_OOB_POSTGRESQL_NETWORK":
        //         networkProfileId = "edb42bdb-a38f-4a6f-a5b5-fb8d90acd311";
        //         break;
        //     case "DEFAULT_OOB_SQLSERVER_NETWORK":
        //         networkProfileId = "0615ccd7-0c6b-4dce-9b19-afb648dd9517";
        //         break;
        //     case "DEFAULT_OOB_MARIADB_NETWORK":
        //         networkProfileId = "59a5e4a4-d670-4712-a8fa-8c5f0f6dc91f";
        //         break;
        //     case "DEFAULT_OOB_MYSQL_NETWORK":
        //         networkProfileId = "bfc63fad-1952-417a-abcf-8f03a0c5eba5";
        //         break;
        //     case "DEFAULT_OOB_MONGODB_NETWORK":
        //         networkProfileId = "a58043e2-3467-4aa7-b7e9-0ec0f54a8814";
        //         break;
        // }

        // switch(paramProfile) {
        //     case "DEFAULT_ORACLE_PARAMS":
        //         paramProfileId = "33f8a6fd-d7ad-49f3-899d-068f20fdf74e";
        //         break;
        //     case "DEFAULT_POSTGRES_PARAMS":
        //         paramProfileId = "c380bc5d-577b-4cbc-987f-908e5b3ea37a";
        //         break;
        //     case "DEFAULT_POSTGRES_HA_PARAMS":
        //         paramProfileId = "26ab28f3-13cf-4081-9d26-0fb2b29f3c92";
        //         break;
        //     case "DEFAULT_SQLSERVER_INSTANCE_PARAMS":
        //         paramProfileId = "e8d97a47-e7f1-4211-b2cd-ffaa23cf9a31";
        //         break;
        //     case "DEFAULT_SQLSERVER_DATABASE_PARAMS":
        //         paramProfileId = "48016975-25c5-48f0-8db7-d9c3a24bdb00";
        //         break;
        //     case "DEFAULT_MARIADB_PARAMS":
        //         paramProfileId = "88c4be4e-b90f-40c7-8cbc-33a8ff79f73a";
        //         break;
        //     case "DEFAULT_MYSQL_PARAMS":
        //         paramProfileId = "b9336b60-6b72-4e4b-87de-434580876060";
        //         break;
        //     case "DEFAULT_SAPHANA_PARAMS":
        //         paramProfileId = "96125530-072a-4743-a48e-0c9a838dcbbc";
        //         break;
        //     case "DEFAULT_MONGODB_PARAMS":
        //         paramProfileId = "e49215b5-ca42-4412-ad79-dae5d367afca";
        //         break;
        // }

        // System.out.println("NDB Instance Name: " + ndbInstanceName);
        // System.out.println("Database Username: " + databaseUsername);
        // System.out.println("Database Password: " + databasePassword);
        // System.out.println("VM Password: " + vmPassword);
        // System.out.println("VM Name: " + vmName);
        // System.out.println("Database Port: " + databasePort);
        // System.out.println("Database Storage: " + databaseStorage);
        // System.out.println("Software Profile: " + softwareProfile);
        // System.out.println("Compute Profile: " + computeProfile);
        // System.out.println("Network Profile: " + networkProfile);
        // System.out.println("Parameter Profile: " + paramProfile);
        // System.out.println("Software Profile ID: " + softwareProfileId);
        // System.out.println("Compute Profile ID: " + computeProfileId);
        // System.out.println("Network Profile ID: " + networkProfileId);
        // System.out.println("Parameter Profile ID: " + paramProfileId);
        // System.out.println("Software Profile Version ID: " + softwareProfileVersionId);

        // String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDcarFKBmh2Zj5a/t8OwmAuPSPkAEABm6E8pUKn+QME+zTBNSiwgKDwKrHEcgnOU7As9jogHShLro1JLUUK4QcwwH6uLUZPBsjo8XORvO7rrQDalXK5V68FcKyDjtgt5xrtbrxTS18iqixMAjwLA2hUuM4t8a0lAKcaZOFRZuxSYSs2ya02qNLbO9S0e5L68eXZGldrIp7oMroWgK96NeBbhRBnWZlPdlZup3umKvVADQHZgznMjshQ9AV4ChZrTlk00MAJkXbBwhWC699k/X7b7qSppBhYEsHNo4QCc//RmlCE8/CRS4lS7CiLEsunESxsSZ222qUcdhiN61L5nOEDKXiLa7mwxoI6VuX5ZMmKZmOMkSm7F62NWOPUSobYB8QQH5qb5N1rjGFGWWfgII314WcNMK93vqCQiBczPsNffN2ROr5RO/4K4Drpo1HYZd47KYushy2EKRLkkeRWaeJzMSPB4klNmM9LL+X8bBQucLh5+EWTgabNc6k5VOIneaMqNxqgOIFKxd15n5O9sE8JZw7L9i1rFGq5wkk0u/1reVVIsh86vzneJxRCr3QwmFjV9G9849quADFH/NwdkRIP1aHPEs0u/PTiwJsjGsZbDqljALUCUeylhE9Y2BjBytZEzKKiNBDJn3dChjMJIlvUv7YxJWVHVXxPSqH8rZxaWQ== felixnoviantoro16@gmail.com,";

        // // Remove only trailing comma and trim leading/trailing whitespace
        // sshKey = sshKey.trim().replaceAll("(^,+|,+$)", "");

        // String execArgs = String.format(
        //     "main.yml -e {'ndb_ip':'%s','ndb_port':'%s','basic_auth':'%s','database_type':'%s','instance_name':'%s','database_description':'%s','softwareProfileId':'%s','softwareProfileVersionId':'%s','computeProfileId':'%s','networkProfileId':'%s','dbParameterProfileId':'%s','time_machine_description':'%s','sla_id':'%s','listener_port':'%s','database_size':'%s','dbserver_description':'%s','database_names':'%s','db_password':'%s','nx_cluster_id':'%s','vm_name':'%s','network_profile_id':'%s','vm_password':'%s','card_id':'%s'} -vvvv",
        //     "10.8.135.235",
        //     "443",
        //     "YWRtaW46SGVsaW9zMTIzIQ==",
        //     databaseType,
        //     ndbInstanceName,
        //     "create-database-instance-from-camel",
        //     softwareProfileId,
        //     softwareProfileVersionId,
        //     computeProfileId,
        //     networkProfileId,
        //     paramProfileId,
        //     "testing-instance-ansible_TM",
        //     "4d9dcd6d-b6f8-47f0-8015-9e691c1d3cf4",
        //     databasePort,
        //     databaseStorage,
        //     "testing-desc",
        //     dbName,
        //     databasePassword,
        //     "9a82460f-fb0b-4fa4-8359-a4f75788eaca",
        //     // sshKey,  // Sanitized SSH key with spaces preserved
        //     vmName,
        //     networkProfileId,
        //     vmPassword,
        //     key
        // );

        // // Set the dynamically built args in the header
        // exchange.getIn().setHeader("execArgs", execArgs);
        // exchange.getIn().setHeader("dbType", databaseType);
    }
}