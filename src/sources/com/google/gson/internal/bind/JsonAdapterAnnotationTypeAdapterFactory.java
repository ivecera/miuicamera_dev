package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor2) {
        this.constructorConstructor = constructorConstructor2;
    }

    static TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor2, Gson gson, TypeToken<?> typeToken, JsonAdapter jsonAdapter) {
        Class<?> value = jsonAdapter.value();
        if (TypeAdapter.class.isAssignableFrom(value)) {
            return (TypeAdapter) constructorConstructor2.get(TypeToken.get((Class) value)).construct();
        }
        if (TypeAdapterFactory.class.isAssignableFrom(value)) {
            return ((TypeAdapterFactory) constructorConstructor2.get(TypeToken.get((Class) value)).construct()).create(gson, typeToken);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }

    /* JADX DEBUG: Type inference failed for r2v2. Raw type applied. Possible types: com.google.gson.TypeAdapter<?>, com.google.gson.TypeAdapter<T> */
    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        JsonAdapter jsonAdapter = (JsonAdapter) typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }
}
