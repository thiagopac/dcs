package br.gov.mg.uberlandia.decserver.utils;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonParseable {

    default String toJson() {
        return JacksonUtils.toJson(this);
    }

    default JsonNode toJsonNode() {
        return JacksonUtils.toJsonNode(this);
    }

}
