package com.mangopay.teamcity.runscope;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.spi.ConnectorProvider;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import java.util.HashMap;
import java.util.Map;

class BuilderFactory {
    private static final String BASE_URL ="https://api.runscope.com";
    private final String token;

    public BuilderFactory(String token) {
        this.token = token;
    }

    public WebTarget getTarget(String path) {
        return getTarget(BASE_URL, path);
    }

    public WebTarget getTarget(String baseUrl, String path) {

        ClientConfig config = new ClientConfig();
        config.connectorProvider(new ApacheConnectorProvider());
        config.register(createMoxyJsonResolver());
        config.property(ClientProperties.PROXY_URI, "http://127.0.0.1:8888");

        Client client = ClientBuilder.newClient(config);
        WebTarget baseTarget = client.target(baseUrl);

        return baseTarget.path(path);
    }

    public Invocation.Builder getBuilder(WebTarget target) {
        return target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
    }

    private static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }
}
