package xin.spring.bless.fast.proper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述，StorageMap HashMap 数据结构的存储
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class StorageMap implements Map<Object, Object> {

    private HashMap<Object, Object> map;

    public StorageMap(){
        this.map = new HashMap<Object, Object>();
    }

    public static StorageMap get(){
        return new StorageMap();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<?, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Object> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

}
