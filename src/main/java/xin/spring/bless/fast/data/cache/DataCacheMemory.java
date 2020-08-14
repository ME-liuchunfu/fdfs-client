package xin.spring.bless.fast.data.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述，DataCacheMemory 内存缓存
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class DataCacheMemory{

    protected static final String CLASS_NAME = DataCacheMemory.class.getName();

    protected static Logger logger = LoggerFactory.getLogger(DataCacheMemory.class);

    private static Map<Object, Object> caches = new ConcurrentHashMap<Object, Object>();

    private DataCacheMemory(){}

    private static DataCacheMemory instance = null;

    public synchronized static Map<Object, Object> getCaches() {
        logger.info("获取缓存数据:{}", CLASS_NAME);
        if (instance == null) {
            instance = new DataCacheMemory();
            logger.info("缓存数据:{},{}", CLASS_NAME, "为空，即将初始化");
        }
        return instance.caches;
    }

    public synchronized static DataCacheMemory get() {
        if (instance == null) {
            instance = new DataCacheMemory();
        }
        return instance;
    }

    public void clear(){
        logger.info("清除缓存数据:{}", CLASS_NAME);
        caches.clear();
    }

    /**
     * 方法描述： 获取缓存
     * @param key 缓存key
     * @param <T> 缓存对象
     * @return
     */
    public <T> T getCache(Object key) {
        T o = (T)caches.get(key);
        return o;
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
