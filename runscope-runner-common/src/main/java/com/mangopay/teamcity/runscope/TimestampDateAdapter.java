package com.mangopay.teamcity.runscope;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class TimestampDateAdapter extends XmlAdapter<Double, Date> {
    @Override
    public Date unmarshal(Double v) throws Exception {
        if(v == null) return null;
        v = v * 1000;
        return new Date(v.longValue());
    }

    @Override
    public Double marshal(Date v) throws Exception {
        if(v == null) return null;
        return new Double(v.getTime() / 1000.0);
    }
}
