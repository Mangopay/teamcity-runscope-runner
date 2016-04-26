package com.mangopay.teamcity.runscope.agent.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.ws.rs.ext.ContextResolver;
import java.util.Date;

class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public ObjectMapperProvider() {
        objectMapper = createDefaultMapper();
    }

    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateDeserializer());
        module.setDeserializerModifier(new EmptyCollectionDeserializerModifier());
        result.registerModule(module);

        return result;
    }

    @Override
    public ObjectMapper getContext(final Class<?> aClass) {
        return objectMapper;
    }
}
