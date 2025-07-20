package com.swisscom.api.utils;

import com.swisscom.api.model.SwisscomService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;

@Component
public class SwisscomServiceCache<T> {

    private final Map<String, T> cache = new ConcurrentHashMap<>();

    public void putIfAbsent(String id, T t) {
        cache.putIfAbsent(id, t);
    }

    public void put(String id, T t) {
        cache.put(id, t);
    }

    public Optional<T> get(String id) {
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

    public List<T> getAll() {
        return new ArrayList<>(cache.values());
    }
}
