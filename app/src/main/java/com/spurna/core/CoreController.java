package com.spurna.core;

import com.spurna.core.business.GenericBO;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oriako on 05/03/2018.
 */

public class CoreController {

    private static volatile CoreController instance = new CoreController();

    public static CoreController getInstance() {
        return instance;
    }

    private static volatile HashMap<Class, GenericBO> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends GenericBO> T getBO(Class<T> objClass) {

        T result = (T) instances.get(objClass);
        if (result == null)
            try {
                result = (T) objClass.newInstance();
                instances.put(objClass, result);
            } catch (Exception e) {

            }
        return (T) result;
    }

    private volatile ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

    public ConcurrentHashMap<String, Object> getCache() {
        return cache;
    }

    public void updateCache(String cacheKey, Object object)
    {
        cache.put(cacheKey,object);
    }
}
