package ru.otus.cachehw;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
    //Надо реализовать эти методы

    static String PUT = "PUT";
    static String REMOVE = "REMOVE";
    static String GET = "GET";

    Map<K,V> weakHashMap = new WeakHashMap<>();

    List<HwListener<K, V>> hwListeners = new ArrayList<>();


    @Override

    public void put(K key, V value) {
        this.weakHashMap.put(key, value);
        hwListeners.forEach(kvHwListener -> kvHwListener.notify(key, value, PUT));
    }

    @Override
    public void remove(K key) {
        V value = this.weakHashMap.remove(key);
        hwListeners.forEach(kvHwListener -> kvHwListener.notify(key, value, REMOVE));
    }

    @Override
    public V get(K key) {
        V value = this.weakHashMap.get(key);
        hwListeners.forEach(kvHwListener -> kvHwListener.notify(key, value, GET));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.hwListeners.add(listener);

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.hwListeners.remove(listener);
    }
}