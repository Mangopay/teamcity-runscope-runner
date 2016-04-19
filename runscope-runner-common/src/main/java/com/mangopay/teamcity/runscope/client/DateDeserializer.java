package com.mangopay.teamcity.runscope.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final double v = jsonParser.getDoubleValue();
        return new Date(Math.round(v * 1000.0));
    }
}
