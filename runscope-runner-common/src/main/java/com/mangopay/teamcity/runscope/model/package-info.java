@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=Date.class, value=TimestampDateAdapter.class),
        @XmlJavaTypeAdapter(type=TestStatus.class, value= TestStatusAdapter.class),
        @XmlJavaTypeAdapter(type=RequestStatus.class, value= RequestStatusAdapter.class),
        @XmlJavaTypeAdapter(type=StepType.class, value= StepTypeAdapter.class),
        @XmlJavaTypeAdapter(type=AssertionStatus.class, value= AssertionStatusAdapter.class)
})package com.mangopay.teamcity.runscope.model;

import com.mangopay.teamcity.runscope.adapters.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Date;