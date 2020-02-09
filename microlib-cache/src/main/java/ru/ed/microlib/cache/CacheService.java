package ru.ed.microlib.cache;

public interface CacheService {
    void putValue(Object key, Object value);
    Object getValue(Object key, int timeToLive);
}
