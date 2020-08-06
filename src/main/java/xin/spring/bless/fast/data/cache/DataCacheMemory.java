package xin.spring.bless.fast.data.cache;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述，DataCacheMemory 内存缓存
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class DataCacheMemory{

    private static Map<Object, Object> caches = new ConcurrentHashMap<Object, Object>();

    private DataCacheMemory(){}

    private static DataCacheMemory instance = null;

    public synchronized static Map<Object, Object> getCaches() {
        if (instance == null) {
            instance = new DataCacheMemory();
        }
        return instance.caches;
    }

    public synchronized static DataCacheMemory get() {
        if (instance == null) {
            instance = new DataCacheMemory();
        }
        return instance;
    }


    public void put(Object obj, Object value) {
        caches.put(obj, value);
    }

    public <T> T getService(Class<T> cla) {
        T o = (T)caches.get(cla);
        return o;
    }

    public void putService(Class<?> cla, Object value) {
        caches.put(cla, value);
    }

}
