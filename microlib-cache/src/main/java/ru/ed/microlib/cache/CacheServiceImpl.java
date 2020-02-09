package ru.ed.microlib.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheServiceImpl implements CacheService {
    private static final int CACHE_SIZE = 50;

    private Map<Object, Object> cache = new ConcurrentHashMap<>();
    private Map<Object, Integer> addTimeMap = new ConcurrentHashMap<>();

    @Override
    public void putValue(Object key, Object value) {
        if(cache.size() < CACHE_SIZE){
            putValueIntoCache(key, value);
        } else {
            freeCache();
            putValueIntoCache(key, value);
        }
    }

    private void putValueIntoCache(Object key, Object value){
        cache.put(key, value);
        addTimeMap.put(key, currentTime());
    }

    private void freeCache() {
        int addTimeMin = Integer.MAX_VALUE;
        Object key = null;

        for (Map.Entry<Object, Integer> entry : addTimeMap.entrySet()){
            int currentAddTime = entry.getValue();

            if(currentAddTime < addTimeMin){
                addTimeMin = currentAddTime;
                key = entry.getKey();
            }
        }

        if(key != null){
            cache.remove(key);
            addTimeMap.remove(key);
        }
    }

    @Override
    public Object getValue(Object key, int timeToLive) {
        Object value = cache.get(key);

        if(value == null){
            return null;
        }

        int addTime = addTimeMap.get(key);
        int currentTime = currentTime();

        if(currentTime - addTime > timeToLive){
            cache.remove(key);
            addTimeMap.remove(key);
            return null;
        }

        return value;
    }

    private int currentTime(){
        return (int) (System.currentTimeMillis() / 1000);
    }
}
