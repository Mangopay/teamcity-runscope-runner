package com.mangopay.teamcity.runscope.adapters;

import com.mangopay.teamcity.runscope.model.TestStatus;

public class TestStatusAdapter extends EnumAdapter<TestStatus>{

    public TestStatusAdapter() {
        super(TestStatus.UNKNOWN);
    }
}
