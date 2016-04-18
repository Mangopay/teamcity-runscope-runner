package com.mangopay.teamcity.runscope.client;
import com.mangopay.teamcity.runscope.RunscopeConstants;
import com.mangopay.teamcity.runscope.model.*;

import javax.ws.rs.client.WebTarget;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return getBucketTests(bucketKey, 0);
    }

    public List<Test> getBucketTests(String bucketKey, int count) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}/tests")
                .resolveTemplate("bucketKey", bucketKey);

        if(count > 0) {
            target = target.queryParam(RunscopeConstants.CLIENT_COUNT, count);
        }

        return builderFactory.getBuilder(target)
                .get(BucketTestsResponse.class)
                .getData();
    }

    public Test getTest(String bucketKey, String testId) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}/tests/{testId}")
                .resolveTemplate("bucketKey", bucketKey)
                .resolveTemplate("testId", testId);

        return builderFactory.getBuilder(target)
                .get(TestResponse.class)
                .getData();
    }

    public List<Step> getTestSteps(String bucketKey, String testId) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}/tests/{testId}/steps")
                .resolveTemplate("bucketKey", bucketKey)
                .resolveTemplate("testId", testId);

        return builderFactory.getBuilder(target)
                .get(TestStepsResponse.class)
                .getData();
    }

    public TestResult getRunResult(Run run) {
        WebTarget target = builderFactory.getTarget("buckets/{bucketKey}/tests/{testId}/results/{runId}")
                .resolveTemplate("bucketKey", run.getBucketKey())
                .resolveTemplate("testId", run.getTestId())
                .resolveTemplate("runId", run.getTestRunId());

        return builderFactory.getBuilder(target)
                .get(ResultResponse.class)
                .getData();
    }

    public Trigger trigger(Test test) {
        return trigger(test.getTriggerUrl());
    }

    public Trigger trigger(Test test, String environment) {
        return trigger(test.getTriggerUrl(), environment);
    }

    public Trigger trigger(Test test, String environment, Map<String, String> initialVariables) {
        return trigger(test.getTriggerUrl(), environment, initialVariables);
    }

    public Trigger trigger(String triggerUrl) {
        return trigger(triggerUrl, "");
    }

    public Trigger trigger(String triggerUrl, String environment) {
        Map<String, String> initialVariables = new HashMap<String, String>();

        return trigger(triggerUrl, environment, initialVariables);
    }

    public Trigger trigger(String triggerUrl, String environment, Map<String, String> initialVariables) {
        WebTarget target = builderFactory.getAbsoluteTarget(triggerUrl);
        initialVariables.put(RunscopeConstants.CLIENT_ENVIRONMENT, environment);

        for(Map.Entry<String, String> variable : initialVariables.entrySet()) {
            target = target.queryParam(variable.getKey(), variable.getValue());
        }

        return builderFactory.getBuilder(target)
                .get(TriggerResponse.class)
                .getData();
    }
}


