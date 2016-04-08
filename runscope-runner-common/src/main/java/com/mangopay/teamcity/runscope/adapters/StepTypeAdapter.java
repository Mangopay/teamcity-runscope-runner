package com.mangopay.teamcity.runscope.adapters;

import com.mangopay.teamcity.runscope.model.StepType;

public class StepTypeAdapter extends EnumAdapter<StepType>{

    public StepTypeAdapter() {
        super(StepType.UNKNOWN);
    }
}
