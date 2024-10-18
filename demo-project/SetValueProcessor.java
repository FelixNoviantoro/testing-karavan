import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("SetValueProcessor")
public class SetValueProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("========================= Set Value");
        Map<String,Map<String,Object>> reqBody = exchange.getIn().getBody(Map.class);

        String summary = jsonObject.getAsJsonObject("issue").getAsJsonPrimitive("summary").getAsString();

        Gson gson = new Gson();

        System.out.println(gson.toJson(reqBody));
        Map<String, Object> changelogMap = gson.fromJson(gson.toJson(reqBody.get("changelog")), Map.class);
        System.out.println(gson.toJson(changelogMap.get("items")));

        List<Map<String, Object>> itemList = (List<Map<String, Object>>) changelogMap.get("items");
        String fromString = "";
        String toString = "";

        for (Map<String, Object> item : itemList) {
            if ("status".equals(item.get("field"))) {
                // Retrieve fromString and toString values
                fromString = (String) item.get("fromString");
                toString = (String) item.get("toString");

                // Print or apply any logic with the values
                System.out.println("From Status: " + fromString);
                System.out.println("To Status: " + toString);
            }
        }

        boolean statusMatch = "On Process Create VM".equals(fromString) && "Done".equals(toString);
        boolean isIncrease = summary.startsWith("Increase");
        System.out.println("BOOLEAN status : " + statusMatch + " increase : " + isIncrease);
        exchange.getIn().setHeader("isIncrease", isIncrease);
        exchange.getIn().setHeader("isComplete", statusMatch);
    }
    
}
