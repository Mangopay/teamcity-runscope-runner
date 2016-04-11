package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.RunscopeConstants;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

class BuilderFactory {
    private final String token;
    private final ClientConfig config;

    public BuilderFactory(String token) {
        this.token = token;
        this.config = CreateConfig();
    }

    private ClientConfig CreateConfig() {
        ClientConfig config = new ClientConfig();
        config.connectorProvider(new ApacheConnectorProvider());
        config.register(ObjectMapperProvider.class);
        config.register(JacksonFeature.class);
        SetProxy(config);

        return config;
    }

    public WebTarget getAbsoluteTarget(String path) {



        Client client = ClientBuilder.newClient(config);
        return client.target(path);
    }

    private void SetProxy(ClientConfig config) {
        String host = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        StringBuilder sb = new StringBuilder("http://");

        if(host == null || host.isEmpty()) return;
        sb.append(host);
        if(port != null && !port.isEmpty()) {
            sb.append(':');
            sb.append(port);
        }

        config.property(ClientProperties.PROXY_URI, sb.toString());
    }

    public WebTarget getTarget(String path) {
        return getTarget(RunscopeConstants.BASE_URL, path);
    }

    public WebTarget getTarget(String baseUrl, String path) {
        return getAbsoluteTarget(baseUrl)
                .path(path);
    }

    public Invocation.Builder getBuilder(WebTarget target) {
        return target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
    }
}
