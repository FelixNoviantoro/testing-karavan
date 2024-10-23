import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@Configuration
@BindToRegistry("ByteToStringProcessor")
public class ByteToStringProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        Gson gson = new Gson();

        // Log the headers for debugging purposes
        System.out.println("Headers: " + exchange.getIn().getHeaders());

        Object body = exchange.getIn().getBody();
        
        // Check if the body is a byte array
        if (body instanceof byte[]) {
            byte[] byteBody = (byte[]) body;

            // Check for gzip encoding
            String contentEncoding = exchange.getIn().getHeader("Content-Encoding", String.class);
            if ("gzip".equalsIgnoreCase(contentEncoding)) {
                // Handle gzip-encoded body
                try (InputStream gzipStream = new GZIPInputStream(new ByteArrayInputStream(byteBody))) {
                    String respBody = new String(gzipStream.readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("==========================");
                    System.out.println(respBody);
                    Map<String, Object> jsonMap = gson.fromJson(respBody, Map.class);
                    System.out.println("==========================");
                    System.out.println(gson.toJson(jsonMap));
                    exchange.getIn().setBody(gson.toJson(jsonMap));
                }
            } else {
                // Assume UTF-8 for uncompressed body
                String respBody = new String(byteBody, StandardCharsets.UTF_8);
                exchange.getIn().setBody(respBody);
            }
        } else if (body instanceof String) {
            // Handle case when body is already a String
            String respBody = (String) body;
            exchange.getIn().setBody(respBody);
        } else {
            // Unknown body type
            exchange.getIn().setBody("Body with unknown encoding or format");
        }
    }
}
