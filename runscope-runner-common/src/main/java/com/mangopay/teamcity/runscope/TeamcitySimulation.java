package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class TeamcitySimulation {
    private static RunscopeClient client;
    private static Map<String, String> runsStatus;

    //simulate workflow that teamcity should implement on a test run
    public static void main(String[] args) {
        String token = args[0];
        String bucket = args[1];
        String test =  args.length > 2 ? args[2] : "";

        client = new RunscopeClient(token);
        runsStatus = new HashMap<String, String>();

        System.exit(runTests(bucket, test));
    }

    private static int runTests(String bucketKey, String testId) {
        //fetch bucket
        Bucket bucket = client.getBucket(bucketKey);
        if(bucket == null) return 1;

        Boolean success = true;
        //fetch all bucket tests
        List<Test> tests = null;

        if(testId != null && !testId.isEmpty()) {
            tests = new Vector<Test>();
            tests.add(client.getTest(bucketKey, testId));
        }
        else {
            tests = client.getBucketTests(bucket.getKey());
        }

        for(Test test : tests) {
            test.setSteps(client.getTestSteps(bucket.getKey(), test.getId()));
            Trigger trigger = trigger(test);
            String status = waitFor(test, trigger);
            success &= "pass".equals(status);
        }

        return success ? 0 : 2;
    }

    private static Trigger trigger(Test test) {
        Trigger trigger = client.trigger(test);
        System.out.printf("Test suite started : %s\n", test.getName());

        return trigger;
    }

    private static String waitFor(Test test, Trigger trigger) {
        List<Run> runs = trigger.getRuns();

        Boolean done;

        do {
            done = true;

            try {
                Thread.sleep(1000);
            } catch(Exception e) {}
            for (Run run : runs) {
                TestResult result = client.getRunResult(run);
                String status = result.getResult();

                setRunStatus(run, result);
                done = done && ("pass".equals(status) || "fail".equals(status) || "canceled".equals(status));
            }
        }while(!done);

        Boolean success = true;
        for(Map.Entry<String, String> entry: runsStatus.entrySet()){
            success = success && "pass".equals(entry.getValue());
        }

        if(success) System.out.printf("Test suite succeeded : %s\n", test.getName());
        else System.out.printf("Test suite failed : %s\n", test.getName());

        return success ? "pass" : "fail";
    }

    private static void setRunStatus(Run run, TestResult result) {
        String currentStatus = runsStatus.get(run.getTestRunId());
        String newStatus = result.getResult();

        runsStatus.put(run.getTestRunId(), newStatus);
        if(newStatus != null && !newStatus.equals(currentStatus)) {
            runStatusChanged(run, result);
        }
    }

    private static void runStatusChanged(Run run, TestResult result) {
        System.out.printf("Test %s : %s\n", run.getTestId(), result.getResult());
    }
}
