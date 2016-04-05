package com.mangopay.teamcity.runscope.model;

public class Bucket {
    private Boolean def;
    private String key;
    private String name;
    private Team team;
    private Boolean ssl;

    public Boolean getDefault() {
        return def;
    }

    public void setDefault(Boolean def) {
        this.def = def;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }
}
