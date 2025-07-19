package com.swisscom.api.service;

import com.swisscom.api.model.SwisscomService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;

@Component
public class SwisscomServiceCache {

    private final Map<String, SwisscomService> cache = new ConcurrentHashMap<>();

    public void putIfAbsent(String id, SwisscomService SwisscomService) {
        cache.putIfAbsent(id, SwisscomService);
    }

    public void put(String id, SwisscomService SwisscomService) {
        cache.put(id, SwisscomService);
    }

    public Optional<SwisscomService> get(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    public void remove(String id) {
        cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }

    public boolean contains(String id) {
        return cache.containsKey(id);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public List<SwisscomService> getAll() {
        return new ArrayList<>(cache.values());
    }
}
