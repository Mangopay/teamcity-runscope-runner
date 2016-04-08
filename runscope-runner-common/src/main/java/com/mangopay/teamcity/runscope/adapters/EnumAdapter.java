package com.mangopay.teamcity.runscope.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.*;

public abstract class EnumAdapter<T extends Enum<T>> extends XmlAdapter<String, Enum<T>> {

    private final Map<String, Enum<T>> values;
    private final Enum<T> defaultValue;

    public EnumAdapter(final Enum<T> defaultValue) {
        this.defaultValue = defaultValue;

        Enum<T>[] values = defaultValue.getClass().getEnumConstants();
        this.values = new HashMap<String, Enum<T>>(values.length);
        for(Enum<T> val : defaultValue.getClass().getEnumConstants()) {
            this.values.put(val.toString(), val);
        }

    }
    @Override
    public Enum<T> unmarshal(final String v) throws Exception {
        if(values.containsKey(v)) return values.get(v);

        return defaultValue;
    }

    @Override
    public String marshal(final Enum<T> v) throws Exception {
        return v.toString();
    }
}
