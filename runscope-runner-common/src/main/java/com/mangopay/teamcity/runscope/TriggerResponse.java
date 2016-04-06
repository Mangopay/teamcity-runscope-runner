package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.Trigger;

class TriggerResponse extends Response {
    private Trigger data;

    public Trigger getData() {
        return data;
    }

    public void setData(Trigger data) {
        this.data = data;
    }
}
