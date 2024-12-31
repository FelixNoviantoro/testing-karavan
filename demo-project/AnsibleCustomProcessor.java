import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Configuration
@BindToRegistry("AnsibleCustomProcessor")
public class AnsibleCustomProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        String execArgs = String.format(
            "main.yml -e {'pc_ip':'%s','bp_id':'%s','basic_auth':'%s','app_description':'%s','app_name':'%s','app_profile_reference_name':'%s','app_profile_reference_uuid':'%s','card_id':'%s','username':'%s','password':'%s','vm_name':'%s','substrate_list_uuid':'%s','username_uuid_variable':'%s','password_uuid_variable':'%s','custom_cpu_num':%d,'custom_memory':%d,'custom_storage':%d,'card_id_uuid_variable':'%s'} -vvvv",
            "10.8.130.168",
            "2397fd48-ac2d-47ee-a710-e4132cce72d2",
            "YWRtaW46bnV0NG5peFBAc3N3MHJk",
            "testing_descripttion_ansible",
            "project-testing-dulu-20", // should match "project-testing-dulu-15"
            "custom",
            "d81a91f4-89df-456c-8693-c2fb8cca4865",
            "PEG-93",
            "felix",
            "password",
            "testing-vm",
            "866028f7-7b14-ae2d-fbfd-bd0ac8510443",
            "19ce41a2-e7ac-ae85-bfb3-96a334766569",
            "d1faee84-7de4-0b8f-b9b6-6a0cd676d682",
            2,               // custom_cpu_num
            8196,            // custom_memory
            100000,          // custom_storage
            "243063ba-d401-4993-9121-a59fc7a5256a"
        );
        exchange.getIn().setHeader("execArgs", execArgs);
    }
}