import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Configuration
@BindToRegistry("NdbRequest")
public class NdbRequest implements Processor {

    public void process(Exchange exchange) throws Exception {

        String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDcarFKBmh2Zj5a/t8OwmAuPSPkAEABm6E8pUKn+QME+zTBNSiwgKDwKrHEcgnOU7As9jogHShLro1JLUUK4QcwwH6uLUZPBsjo8XORvO7rrQDalXK5V68FcKyDjtgt5xrtbrxTS18iqixMAjwLA2hUuM4t8a0lAKcaZOFRZuxSYSs2ya02qNLbO9S0e5L68eXZGldrIp7oMroWgK96NeBbhRBnWZlPdlZup3umKvVADQHZgznMjshQ9AV4ChZrTlk00MAJkXbBwhWC699k/X7b7qSppBhYEsHNo4QCc//RmlCE8/CRS4lS7CiLEsunESxsSZ222qUcdhiN61L5nOEDKXiLa7mwxoI6VuX5ZMmKZmOMkSm7F62NWOPUSobYB8QQH5qb5N1rjGFGWWfgII314WcNMK93vqCQiBczPsNffN2ROr5RO/4K4Drpo1HYZd47KYushy2EKRLkkeRWaeJzMSPB4klNmM9LL+X8bBQucLh5+EWTgabNc6k5VOIneaMqNxqgOIFKxd15n5O9sE8JZw7L9i1rFGq5wkk0u/1reVVIsh86vzneJxRCr3QwmFjV9G9849quADFH/NwdkRIP1aHPEs0u/PTiwJsjGsZbDqljALUCUeylhE9Y2BjBytZEzKKiNBDJn3dChjMJIlvUv7YxJWVHVXxPSqH8rZxaWQ== felixnoviantoro16@gmail.com,";

        // Remove only trailing comma and trim leading/trailing whitespace
        sshKey = sshKey.trim().replaceAll("(^,+|,+$)", "");

        String execArgs = String.format(
            "main.yml -e {'ndb_ip':'%s','ndb_port':'%s','basic_auth':'%s','database_type':'%s','instance_name':'%s','database_description':'%s','softwareProfileId':'%s','softwareProfileVersionId':'%s','computeProfileId':'%s','networkProfileId':'%s','dbParameterProfileId':'%s','time_machine_description':'%s','sla_id':'%s','listener_port':'%s','database_size':'%s','dbserver_description':'%s','database_names':'%s','db_password':'%s','nx_cluster_id':'%s','vm_name':'%s','network_profile_id':'%s','vm_password':'%s'} -vvvv",
            "10.8.135.235",
            "443",
            "YWRtaW46SGVsaW9zMTIzIQ==",
            "postgres_database",
            "testing-instance-ansible-5",
            "testing-dari-ansible",
            "246bc618-5afd-4811-83fc-95889849aafd",
            "a70c3202-b7a4-40cb-99a9-ec14e057fe4f",
            "54e557b6-2186-4c4a-aac0-09b7cd9ca879",
            "edb42bdb-a38f-4a6f-a5b5-fb8d90acd311",
            "c380bc5d-577b-4cbc-987f-908e5b3ea37a",
            "testing-instance-ansible_TM",
            "4d9dcd6d-b6f8-47f0-8015-9e691c1d3cf4",
            "5432",
            "30",
            "testing-desc",
            "postgres",
            "nutanix/4u",
            "9a82460f-fb0b-4fa4-8359-a4f75788eaca",
            // sshKey,  // Sanitized SSH key with spaces preserved
            "test-ndb-ansible-vm-5",
            "edb42bdb-a38f-4a6f-a5b5-fb8d90acd311",
            "nutanix/4u"
        );

        // Set the dynamically built args in the header
        exchange.getIn().setHeader("execArgs", execArgs);
        
    }
}