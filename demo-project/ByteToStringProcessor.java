import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Configuration
@BindToRegistry("ByteToStringProcessor")
public class ByteToStringProcessor implements Processor {

    private final Gson gson = new Gson();

    @Override
    public void process(Exchange exchange) throws Exception {
        // Log headers for debugging
        System.out.println("Headers: " + exchange.getIn().getHeaders());
        Integer responseCode = (Integer) exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
        System.out.println("response code inquiry ====================================================================================== " + gson.toJson(exchange.getIn().getHeaders()));

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
                    System.out.println("GZIP Decoded Body ============================================= " + respBody);
                    System.out.println("====================================================== processJsonBody");
                    processJsonBody(respBody, exchange);
                }
            } else {
                // Assume UTF-8 for uncompressed body
                String respBody = new String(byteBody, StandardCharsets.UTF_8);
                processJsonBody(respBody, exchange);
            }
        } else if (body instanceof Map) {
            // If the body is already a Map, convert it to JSON string format
            String jsonString = gson.toJson(body);
            exchange.getIn().setBody(jsonString);
        } else if (body instanceof String) {
            // If the body is already a String, set it as is
            processJsonBody((String) body, exchange);
        } else {
            // Unknown body type
            exchange.getIn().setBody("Unsupported body format");
        }
    }

    private void processJsonBody(String jsonBody, Exchange exchange) {
        try {
            // Parse JSON as a generic JsonElement
            JsonElement jsonElement = gson.fromJson(jsonBody, JsonElement.class);

            if (jsonElement.isJsonArray()) {
                // If it's a JSON array, convert to a List of Maps
                exchange.getIn().setBody(gson.fromJson(jsonBody, List.class));
            } else if (jsonElement.isJsonObject()) {
                // If it's a JSON object, convert to a Map
                exchange.getIn().setBody(gson.fromJson(jsonBody, Map.class));
            } else {
                exchange.getIn().setBody("Unsupported JSON format");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
            exchange.getIn().setBody("Invalid JSON format");
        }
    }
}
