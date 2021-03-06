package com.mangopay.teamcity.runscope.agent.client;

import com.mangopay.teamcity.runscope.agent.model.*;

import java.util.List;

class BucketResponse extends Response<Bucket> { }
class BucketTestsResponse extends Response<List<Test>> { }
class EnvironmentResponse extends  Response<Environment> { }
class ResultResponse extends Response<TestResult> { }
class TestResponse extends Response<Test> { }
class TestStepsResponse extends Response<List<Step>> { }
class TriggerResponse extends Response<Trigger> { }

