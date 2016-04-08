package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.fake.FakeClass;
import com.mangopay.teamcity.runscope.fake.FakeClassResponse;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class FakeClient {
    private final BuilderFactory builderFactory;

    public FakeClient() {
        this.builderFactory = new BuilderFactory("");
    }

    public FakeClass getFake() {
        WebTarget target = builderFactory.getTarget("https://ngroktest.leetchi.com", "api/fake");

        return builderFactory.getBuilder(target)
                .get(FakeClass.class);
    }

    public FakeClass getFakeResponse() {
        WebTarget target = builderFactory.getTarget("https://ngroktest.leetchi.com", "api/fake/response");

        Object o = builderFactory.getBuilder(target)
                .get(FakeClassResponse.class);

        return ((FakeClassResponse)o).getData();
    }

    public List<FakeClass> getFakeList() {
        WebTarget target = builderFactory.getTarget("https://ngroktest.leetchi.com", "api/fake/list");

        Object o = builderFactory.getBuilder(target)
                .get(new GenericType<List<FakeClass>>(){});

        return (List<FakeClass>)o;
    }
}
