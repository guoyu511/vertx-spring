package io.vertx.ext.spring;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InMemoryCache {

    // shared by verticles
    private static HashMap<String, String> testMap = new HashMap<>();

    String get(String key) {
        return testMap.getOrDefault(key, "");
    }

    String set(String key, String value) {
        testMap.put(key, value);
        return value;
    }

}
