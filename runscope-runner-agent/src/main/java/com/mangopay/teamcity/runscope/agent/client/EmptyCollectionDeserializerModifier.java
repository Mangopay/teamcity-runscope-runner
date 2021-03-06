package com.mangopay.teamcity.runscope.agent.client;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

public class EmptyCollectionDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyCollectionDeserializer(final DeserializationConfig config, final CollectionType type, final BeanDescription beanDesc, final JsonDeserializer<?> deserializer) {
        if(deserializer.getClass().isAssignableFrom(CollectionDeserializer.class)) {
            return new EmptyCollectionDeserializer((CollectionDeserializer)deserializer);
        }

        return super.modifyCollectionDeserializer(config, type, beanDesc, deserializer);
    }

}
