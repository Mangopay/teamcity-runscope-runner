package com.mangopay.teamcity.runscope.agent.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.util.ArrayList;
import java.util.Collection;

public class EmptyCollectionDeserializer extends CollectionDeserializer {
    private static final long serialVersionUID = -1L;

    public EmptyCollectionDeserializer(final JavaType collectionType, final JsonDeserializer<Object> valueDeser, final TypeDeserializer valueTypeDeser, final ValueInstantiator valueInstantiator) {
        super(collectionType, valueDeser, valueTypeDeser, valueInstantiator);
    }

    protected EmptyCollectionDeserializer(final JavaType collectionType, final JsonDeserializer<Object> valueDeser, final TypeDeserializer valueTypeDeser, final ValueInstantiator valueInstantiator, final JsonDeserializer<Object> delegateDeser) {
        super(collectionType, valueDeser, valueTypeDeser, valueInstantiator, delegateDeser);
    }

    protected EmptyCollectionDeserializer(final CollectionDeserializer src) {
        super(src);
    }

    @Override
    public Collection<Object> getNullValue() {
        return new ArrayList<>();
    }

    @Override
    protected CollectionDeserializer withResolved(final JsonDeserializer<?> dd, final JsonDeserializer<?> vd, final TypeDeserializer vtd) {
        return new EmptyCollectionDeserializer(super.withResolved(dd, vd, vtd));
    }
}
