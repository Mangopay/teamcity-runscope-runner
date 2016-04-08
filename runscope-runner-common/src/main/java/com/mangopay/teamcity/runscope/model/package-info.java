@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=Date.class, value=TimestampDateAdapter.class),
        @XmlJavaTypeAdapter(type=TestStatus.class, value= TestStatusAdapter.class),
        @XmlJavaTypeAdapter(type=RequestStatus.class, value= RequestStatusAdapter.class)
})package com.mangopay.teamcity.runscope.model;

import com.mangopay.teamcity.runscope.adapters.RequestStatusAdapter;
import com.mangopay.teamcity.runscope.adapters.TestStatusAdapter;
import com.mangopay.teamcity.runscope.adapters.TimestampDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Date;