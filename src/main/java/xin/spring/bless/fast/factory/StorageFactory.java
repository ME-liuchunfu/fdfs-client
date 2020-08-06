package xin.spring.bless.fast.factory;

import xin.spring.bless.fast.conf.StorageType;
import xin.spring.bless.fast.proper.StorageMap;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.impl.*;

/**
 * 描述，StorageFactory 存储服务工厂
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class StorageFactory {

    public StorageFactory(){}

    public static StorageFactory get() {
        return new StorageFactory();
    }

    /**
     * 建造Storage存储对象
     * @return 返回存储对象，如果配置为空，将返回空
     * @param properties
     */
    public Storage build(StorageMap properties) {
        Storage service = null;
        if (properties.get(StorageType.SERVER_KEY).equals(StorageType.LOCAL)) {
            service = BuilderBeanFactory.get().build(LocalStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.FASTDFS)) {
            service = BuilderBeanFactory.get().build(FastDfsStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.ALIYUN)) {
            service = BuilderBeanFactory.get().build(AliyunStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.QCLOUD)) {
            service = BuilderBeanFactory.get().build(QCloudStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.QINIU)) {
            service = BuilderBeanFactory.get().build(QiniuStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.BAIDUYUN)) {
            service = BuilderBeanFactory.get().build(BaiduStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.HUAWEIYUN)) {
            service = BuilderBeanFactory.get().build(HuaweiStorageServiceImpl.class);
        } else if (properties.get(StorageType.SERVER_KEY).equals(StorageType.HDFS)) {
            service = BuilderBeanFactory.get().build(HdfsStorageServiceImpl.class);
        }
        return service;
    }

}
