package com.mangopay.teamcity.runscope.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class TimestampDateAdapter extends XmlAdapter<Double, Date> {
    @Override
    public Date unmarshal(final Double v) throws Exception {
        if(v == null) return null;
        return new Date(v.longValue() * 1000);
    }

    @Override
    public Double marshal(final Date v) throws Exception {
        if(v == null) return null;
        return new Double(v.getTime() / 1000.0);
    }
}
