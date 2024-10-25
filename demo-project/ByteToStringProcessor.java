import com.google.gson.Gson;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Configuration
@BindToRegistry("ByteToStringProcessor")
public class ByteToStringProcessor implements Processor {

    private final Gson gson = new Gson();

    @Override
    public void process(Exchange exchange) throws Exception {
        // Log headers for debugging
        System.out.println("Headers ==============================================================: " + exchange.getIn().getHeaders());

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
                    System.out.println("GZIP Decoded Body: " + respBody);

                    // Convert the JSON string to a Map
                    Map<String, Object> jsonMap = gson.fromJson(respBody, Map.class);
                    // Set the JSON string representation of the Map as the body
                    exchange.getIn().setBody(jsonMap);
                }
            } else {
                // Assume UTF-8 for uncompressed body
                String respBody = new String(byteBody, StandardCharsets.UTF_8);
                exchange.getIn().setBody(respBody);
            }
        } else if (body instanceof Map) {
            // If the body is already a Map, convert it to JSON string format
            String jsonString = gson.toJson(body);
            exchange.getIn().setBody(jsonString);
        } else if (body instanceof String) {
            // If the body is already a String, set it as is
            exchange.getIn().setBody(body);
        } else {
            // Unknown body type
            exchange.getIn().setBody("Unsupported body format");
        }
    }
}
