package xin.spring.bless.fast.conf;

import org.apache.commons.io.FileUtils;
import org.csource.fastdfs.ClientGlobal;
import xin.spring.bless.fast.data.cache.DataCacheMemory;
import xin.spring.bless.fast.factory.StorageFactory;
import xin.spring.bless.fast.proper.StorageMap;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.impl.AbsStorageService;
import xin.spring.bless.fast.service.impl.LocalStorageServiceImpl;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * 描述，StorageGlobalConfig 存储服务全局配置
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class StorageGlobalConfig {

    public StorageGlobalConfig(){}

    /**
     * 方法描述： 获取存储服务全局配置对象
     * @return
     */
    public static StorageGlobalConfig get() {
        return new StorageGlobalConfig();
    }

    /**
     * 方法描述： 建造存储服务配置信息
     * @param properties 配置信息
     * @return
     */
    public Storage build(StorageMap properties) {
        Storage storage = null;
        if (Objects.isNull(properties)) {
            properties = defaultLocalProperties();
        }
        DataCacheMemory.get().put(StorageMap.class, properties);
        try {
            switch (properties.get(StorageType.SERVER_KEY).toString()) {
                case StorageType.FASTDFS: {
                    Properties pt = new Properties();
                    pt.putAll(properties);
                    ClientGlobal.initByProperties(pt);
                    break;
                }
                case StorageType.ALIYUN: {
                    break;
                }
                case StorageType.QCLOUD: {
                    break;
                }
                case StorageType.QINIU: {
                    break;
                }
                case StorageType.BAIDUYUN: {
                    break;
                }
                case StorageType.HUAWEIYUN: {
                    break;
                }
                case StorageType.HDFS: {
                    break;
                }
                default: {
                    FileUtils.forceMkdir(new File(properties.get(StorageType.LOCAL_PATH_KEY).toString()));
                    break;
                }
            }
            storage = StorageFactory.get().build(properties);
            DataCacheMemory.get().putService(Storage.class, storage);
        } catch (Exception e) {
             e.printStackTrace();
        }
        return storage;
    }

    /**
     * 方法描述： 建造本地存储服务配置信息
     * @return Storage 存储服务
     */
    public Storage build() {
        StorageMap storageMap = defaultLocalProperties();
        return get().build(storageMap);
    }

    /**
     * 方法描述： 建造本地文件存储配置信息
     * @return properties 配置信息
     */
    public StorageMap defaultLocalProperties(){
        StorageMap properties = StorageMap.get();
        int i = osServer();
        if (i == 1) {
            properties.put("path", "C:"+File.separatorChar+"fast-storage"+File.separatorChar);
        } else if (i == 2) {
            properties.put("path", File.separatorChar+"fast-storage"+File.separatorChar);
        } else if (i == 3) {
            properties.put("path", File.separatorChar+"fast-storage"+File.separatorChar);
        }
        properties.put(StorageType.SERVER_KEY, StorageType.LOCAL);
        return properties;
    }

    /**
     * 方法描述： 建造默认的FastDFS存储服务配置信息
     * @return Storage 存储服务
     */
    public StorageMap defaultFastDFSProperties(){
        StorageMap properties = StorageMap.get();
        properties.put(StorageType.SERVER_KEY, StorageType.FASTDFS);
        properties.put(StorageType.fastdfs_connect_timeout_in_seconds, 5);
        properties.put(StorageType.fastdfs_network_timeout_in_seconds, 30);
        properties.put(StorageType.fastdfs_charset, "UTF-8");
        properties.put(StorageType.fastdfs_http_anti_steal_token, false);
        properties.put(StorageType.fastdfs_http_secret_key, "FastDFS1234567890");
        properties.put(StorageType.fastdfs_http_tracker_http_port, "80");
        properties.put(StorageType.fastdfs_tracker_servers, "127.0.0.1:22122");
        return properties;
    }

    /**
     * 方法描述： 获取当前服务是在那种环境
     * @return i ==> 0 unknow， 1 linux, 2 windows, 3 mac
     */
    public static int osServer() {
        int i = 0;
        //当前系统名称
        String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
        if (OS_NAME.contains("windows")) {
            i = 2;
        } else if (OS_NAME.contains("linux")) {
            i = 1;
        } else if (OS_NAME.contains("mac")) {
            i = 3;
        }
        return i;
    }

    public static void main(String[] args) {
        //当前系统名称
        String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
        System.out.println(OS_NAME);
    }

}

