package com.mangopay.teamcity.runscope.adapters;

import com.mangopay.teamcity.runscope.model.RequestStatus;

public class RequestStatusAdapter extends EnumAdapter<RequestStatus>{

    public RequestStatusAdapter() {
        super(RequestStatus.UNKNOWN);
    }
}
