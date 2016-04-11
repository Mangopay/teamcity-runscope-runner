package com.mangopay.teamcity.runscope.client;

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
    private static final String BASE_URL ="https://api.runscope.com";
    private final String token;

    public BuilderFactory(String token) {
        this.token = token;
    }

    public WebTarget getAbsoluteTarget(String path) {

        ClientConfig config = new ClientConfig();
        config.connectorProvider(new ApacheConnectorProvider());
        config.register(ObjectMapperProvider.class);
        config.register(JacksonFeature.class);
        config.property(ClientProperties.PROXY_URI, "http://127.0.0.1:8888");

        Client client = ClientBuilder.newClient(config);
        return client.target(path);
    }

    public WebTarget getTarget(String path) {
        return getTarget(BASE_URL, path);
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
