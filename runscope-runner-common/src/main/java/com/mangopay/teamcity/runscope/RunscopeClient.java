package com.mangopay.teamcity.runscope;
import com.mangopay.teamcity.runscope.model.*;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;


public class RunscopeClient {

    private final BuilderFactory builderFactory;

    public RunscopeClient(String token) {
        this.builderFactory = new BuilderFactory(token);
    }

    public Bucket getBucket(String bucketKey) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}")
                .resolveTemplate("bucketKey", bucketKey);

        return builderFactory.getBuilder(target)
                .get(BucketResponse.class)
                .getData();
    }

    public List<Test> getBucketTests(String bucketKey) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}/tests")
                .resolveTemplate("bucketKey", bucketKey);

        return builderFactory.getBuilder(target)
                .get(BucketTestsResponse.class)
                .getData();
    }
}


