package com.mangopay.teamcity.agent.client;

import com.mangopay.teamcity.agent.model.*;

import java.util.List;

class BucketResponse extends Response<Bucket> { }
class BucketTestsResponse extends Response<List<Test>> { }
class ResultResponse extends Response<TestResult> { }
class TestResponse extends Response<Test> { }
class TestStepsResponse extends Response<List<Step>> { }
class TriggerResponse extends Response<Trigger> { }

