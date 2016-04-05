package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.Bucket;
import com.mangopay.teamcity.runscope.model.FakeClass;
import com.mangopay.teamcity.runscope.model.Test;

import java.util.List;

class Main {
    public static void main(String[] args) {
        String token = args[0];
        String bucket = args[1];

        callFakeApis();
        callRunscope(token, bucket);
    }

    private static void callFakeApis() {
        FakeClient fakeClient = new FakeClient();
        FakeClass fake = fakeClient.getFake();
        List<FakeClass> fakelist = fakeClient.getFakeList();
        FakeClass fakeResponse = fakeClient.getFakeResponse();

        System.out.println(String.format("%s : %s, %s", fake.getId(), fake.getName(), fake.getAuthToken()));
    }

    private static void callRunscope(String token, String bucket) {
        RunscopeClient client = new RunscopeClient(token);
        List<Test> tests = client.getBucketTests(bucket);

        Bucket b = client.getBucket(bucket);
        System.out.println(String.format("%s : %s", b.getKey(), b.getName()));
    }
}
