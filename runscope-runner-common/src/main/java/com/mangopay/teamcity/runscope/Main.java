package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.*;

import java.util.List;

class Main {
    public static void main(String[] args) {
        String token = args[0];
        String bucket = args[1];

        callFakeApis();
        System.exit(callRunscope(token, "u2bs2926ut38"));
    }

    private static void callFakeApis() {
        FakeClient fakeClient = new FakeClient();
        FakeClass fake = fakeClient.getFake();
        List<FakeClass> fakelist = fakeClient.getFakeList();
        FakeClass fakeResponse = fakeClient.getFakeResponse();

        System.out.println(String.format("%s : %s, %s", fake.getId(), fake.getName(), fake.getAuthToken()));
    }

    private static int callRunscope(String token, String bucket) {
        RunscopeClient client = new RunscopeClient(token);
        List<Test> tests = client.getBucketTests(bucket);
        Test test = tests.get(0);
        test = client.getTest(bucket, test.getId());

        Trigger trigger = client.trigger("https://api.runscope.com/radar/bucket/cd1c204f-1fa2-4092-8fdf-59f21025aeb2/trigger");
        //Trigger trigger = client.trigger(test);
        List<Run> runs = trigger.getRuns();

        if(trigger.getRunsStarted() != trigger.getRunsTotal()) {
            System.out.printf("%d tests queued out of %d => failed", trigger.getRunsStarted(), trigger.getRunsTotal());
            return 1;
        }

        System.out.printf("%d runs queued", trigger.getRunsStarted());

        Run run = runs.get(0);
        TestResult result = null;
        do {
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {}
             result = client.getRunResult(run);
        } while(result.getResult() == "working");

        if(result.getResult() == "fail") {
            System.out.printf("test failed", trigger.getRunsStarted(), trigger.getRunsTotal());
            return 2;
        }
        if(result.getResult() == "pass") {
            System.out.printf("test succeeded", trigger.getRunsStarted(), trigger.getRunsTotal());
            return 0;
        }

        return 3;
        /*Bucket b = client.getBucket(bucket);
        System.out.println(String.format("%s : %s", b.getKey(), b.getName()));*/
    }
}
