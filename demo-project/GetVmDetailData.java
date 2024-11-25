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
@BindToRegistry("GetVmDetailData")
public class GetVmDetailData implements Processor {

    public void process(Exchange exchange) throws Exception {
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);

        Map<String, Object> spec = (Map<String, Object>) respBody.get("spec");
        Map<String, Object> resources = (Map<String, Object>) spec.get("resources");
        List<Map<String, Object>> nic_list = (List<Map<String, Object>>) resources.get("nic_list");

        Map<String, Object> nic1 = (Map<String, Object>) nic_list.get(0);
        Map<String, Object> nic2 = (Map<String, Object>) nic_list.get(1);

        List<Map<String, Object>> ipEndpointList1 = (List<Map<String, Object>>) nic1.get("ip_endpoint_list");
        List<Map<String, Object>> ipEndpointList2 = (List<Map<String, Object>>) nic2.get("ip_endpoint_list");

        Map<String, Object> dataIpMap = (Map<String, Object>) ipEndpointList1.get(0);
        Map<String, Object> manIpMap = (Map<String, Object>) ipEndpointList2.get(0);

        String dataIp = (String) dataIpMap.get("ip");
        String manIp = (String) manIpMap.get("ip");

        System.out.println("============================ Get Vm Detail");
        System.out.println("Data IP : " + dataIp + " Man IP : " + manIp);
        System.out.println("============================ Get Vm Detail");

        exchange.setProperty("dataIp", dataIp);
        exchange.setProperty("manIp", manIp);

    }
}