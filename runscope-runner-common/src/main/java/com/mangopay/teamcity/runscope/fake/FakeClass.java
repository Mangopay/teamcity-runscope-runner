package com.mangopay.teamcity.runscope.fake;

import javax.xml.bind.annotation.XmlElement;

public class FakeClass {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    @XmlElement(name="auth_token")
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
