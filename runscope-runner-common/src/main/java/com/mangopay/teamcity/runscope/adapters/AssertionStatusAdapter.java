package com.mangopay.teamcity.runscope.adapters;

import com.mangopay.teamcity.runscope.model.AssertionStatus;

public class AssertionStatusAdapter extends EnumAdapter<AssertionStatus>{

    public AssertionStatusAdapter() {
        super(AssertionStatus.UNKNOWN);
    }
}
