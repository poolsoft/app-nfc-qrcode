package com.br.syncrename.Utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class ServerHandler {

    private static ObjectMapper mapper;

    public static ObjectMapper getJsonConverter() {
        if (mapper == null) {
            mapper = new ObjectMapper()
                    .registerModule(new JodaModule())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        }
        return mapper;
    }
}
