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
@BindToRegistry("FetchIssueNDBProcessor")
public class FetchIssueNDBProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        String respBody = exchange.getIn().getBody(String.class);
        System.out.println("========================= FETCHED");
        // System.out.println(respBody);
        System.out.println("========================= fromjson");

        String regex = "name=VM Name.*?value=([^\\s,}]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(respBody);

        String regexPass = "name=Database Password.*?value=([^\\s,}]+)";
        Pattern patternPass = Pattern.compile(regexPass);
        Matcher matcherPass = patternPass.matcher(respBody);

        String regexPassVm = "name=VM Password.*?value=([^\\s,}]+)";
        Pattern patternPassVm = Pattern.compile(regexPassVm);
        Matcher matcherPassVm = patternPassVm.matcher(respBody);
        
        if (matcher.find()) {
            String vmName = matcher.group(1); // Extracted value
            System.out.println("NDB Instance Name: " + vmName);

            exchange.setProperty("vmName", vmName);

        } else {
            System.out.println("NDB Instance Name not found.");
        }

        if (matcherPass.find()) {
            String dbPass = matcherPass.group(1); // Extracted value
            System.out.println("NDB db pass: " + dbPass);

            exchange.setProperty("dbPass", dbPass);

        } else {
            System.out.println("NDB db pass not found.");
        }

        if (matcherPassVm.find()) {
            String vmPass = matcherPassVm.group(1); // Extracted value
            System.out.println("NDB VM Pass: " + vmPass);

            exchange.setProperty("vmPass", vmPass);

        } else {
            System.out.println("NDB Vm Pass not found.");
        }

    }
}