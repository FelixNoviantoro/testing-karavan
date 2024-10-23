import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import org.apache.camel.component.netty.http.NettyHttpComponent;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import org.apache.camel.component.http.HttpComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("NutanixBpListProcessor")
public class NutanixBpListProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        System.out.println("==========================");
        System.out.println("NUTANIX Processor");

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Trust all client certificates
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Trust all server certificates
                    }
                }
        };

        TrustManagersParameters trustManagersParameters = new TrustManagersParameters();
        trustManagersParameters.setTrustManager(trustAllCerts[0]);

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setSecureSocketProtocol("TLS");
        sslContextParameters.setTrustManagers(trustManagersParameters);

        HttpComponent httpComponent = exchange.getContext().getComponent("https", HttpComponent.class);
        httpComponent.setSslContextParameters(sslContextParameters);
        NettyHttpComponent nettyHttpComponent = exchange.getContext().getComponent("netty-http", NettyHttpComponent.class);
        nettyHttpComponent.getConfiguration().setSslContextParameters(sslContextParameters);

        Gson gson = new Gson();
        Map<String, Object> reqBody = new HashMap<>();

        reqBody.put("length", 2);
        reqBody.put("offset", 0);
        reqBody.put("sort_attribute", "ASCENDING");
        reqBody.put("sort_order", "ASCENDING");

        System.out.println("==========================");
        System.out.println("END NUTANIX Processor");

        exchange.getIn().setBody(gson.toJson(reqBody));

    }
}
