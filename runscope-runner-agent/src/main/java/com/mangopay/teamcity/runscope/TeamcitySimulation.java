package com.mangopay.teamcity.runscope;


public class TeamcitySimulation {

    //simulate workflow that teamcity should implement on a test run
    public static void main(String[] args) throws Exception {
        String token = args[0];
        String bucket = args[1];
        String test =  args.length > 2 ? args[2] : "";

        RunscopeClient client = new RunscopeClient(token);
        RunscopeTestSet runscopeTestSet = new RunscopeTestSet(token, bucket, test, "", new SystemOutLogger());
        runscopeTestSet.run();
    }
}
