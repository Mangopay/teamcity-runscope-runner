@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=Date.class, value=TimestampDateAdapter.class)
})package com.mangopay.teamcity.runscope.model;

import com.mangopay.teamcity.runscope.TimestampDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Date;