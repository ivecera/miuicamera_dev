package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        boolean isLenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            JsonElement parse = Streams.parse(jsonReader);
            jsonReader.setLenient(isLenient);
            return parse;
        } catch (StackOverflowError e2) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e2);
        } catch (OutOfMemoryError e3) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e3);
        } catch (Throwable th) {
            jsonReader.setLenient(isLenient);
            throw th;
        }
    }

    public JsonElement parse(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement parse = parse(jsonReader);
            if (!parse.isJsonNull()) {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonSyntaxException("Did not consume the entire document.");
                }
            }
            return parse;
        } catch (MalformedJsonException e2) {
            throw new JsonSyntaxException(e2);
        } catch (IOException e3) {
            throw new JsonIOException(e3);
        } catch (NumberFormatException e4) {
            throw new JsonSyntaxException(e4);
        }
    }

    public JsonElement parse(String str) throws JsonSyntaxException {
        return parse(new StringReader(str));
    }
}
