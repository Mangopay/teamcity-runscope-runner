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
        return new ClientConfig()
                .connectorProvider(new ApacheConnectorProvider())
                .register(ObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .property(ClientProperties.PROXY_URI, getProxy());
    }

    public WebTarget getAbsoluteTarget(String path) {
        final Client client = ClientBuilder.newClient(config);
        return client.target(path);
    }

    private String getProxy() {
        final String host = System.getProperty("http.proxyHost");
        final String port = System.getProperty("http.proxyPort");
        final StringBuilder sb = new StringBuilder("http://");

        if(host == null || host.isEmpty()) return null;
        sb.append(host);
        if(port != null && !port.isEmpty()) {
            sb.append(':');
            sb.append(port);
        }

        return sb.toString();
    }

    public WebTarget getTarget(final String path) {
        return getTarget(RunscopeConstants.BASE_URL, path);
    }

    public WebTarget getTarget(final String baseUrl, final String path) {
        return getAbsoluteTarget(baseUrl)
                .path(path);
    }

    public Invocation.Builder getBuilder(final WebTarget target) {
        return target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
    }
}
