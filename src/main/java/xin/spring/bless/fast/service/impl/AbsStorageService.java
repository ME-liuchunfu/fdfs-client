package xin.spring.bless.fast.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.spring.bless.fast.commons.lang.utils.StringUtils;
import xin.spring.bless.fast.data.cache.DataCacheMemory;
import xin.spring.bless.fast.exception.DfsNotSurportException;
import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.proper.StorageMap;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.UploadProgress;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 描述，AbsStorageService 存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public abstract class AbsStorageService implements Storage {

    protected static final String CLASS_NAME = AbsStorageService.class.getName();

    protected static Logger logger = LoggerFactory.getLogger(AbsStorageService.class);

    /**
     * 默认上传字节大小
     */
    public int SIZE_DATA = 1024 * 10;

    public AbsStorageService(){
        logger.info("初始化配置{}", "init()");
        init();
        logger.info("已完成初始化配置{}", "init()");
    }

    /**
     * 方法描述: 初始化服务配置
     */
    public abstract void init();

    /**
     * 方法描述： 销毁实例配置
     */
    public void destroy() {}

    /**
     * 方法描述： 获取缓存配置信息
     * @return
     */
    public StorageMap getConfig(){
        StorageMap storageMap = DataCacheMemory.get().getCache(StorageMap.class);
        return storageMap;
    }

    /**
     * 方法描述： 获取uuid字符串
     * @return
     */
    public String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 方法描述： 获取当前时间日期字符串
     * @return 如 2020-08-06
     */
    public String dateString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 方法描述： 获取当前时间日期字符串
     * @return 如 2020-08-06-20-20-20
     */
    public String dateTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 方法描述：获取文件名后缀，不包含“.”
     * @param filename 文件名称
     * @return 获取成功返回后缀，失败返回null
     */
    public String subfix(String filename) {
        if (StringUtils.isBlank(filename)) {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    @Override
    public String upload(File uploadFile, UploadProgress progress) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public String upload(byte[] datas, UploadProgress progress) {
        throw new DfsNotSurportException("方法未实现");
    }

    /**
     * 方法描述： 文件上传
     * @param datas 目标文件
     * @param subType 文件类型， 如 txt， pdf，mp4等
     * @param progress 上传进度监听
     */
    public String upload(byte[] datas, String subType, UploadProgress progress) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public String upload(InputStream inputStream, UploadProgress progress) {
        throw new DfsNotSurportException("方法未实现");
    }

    /**
     * 方法描述： 文件上传
     * @param inputStream 目标文件,输入流
     * @param subType 文件类型， 如 txt， pdf，mp4等
     * @param progress 上传进度监听
     */
    public String upload(InputStream inputStream, String subType, UploadProgress progress) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public String upload(File uploadFile) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public String upload(byte[] datas) {
        throw new DfsNotSurportException("方法未实现");
    }

    /**
     * 方法描述： 文件上传
     * @param datas 目标文件,字节数组
     * @param subType 文件类型， 如 txt， pdf，mp4等
     */
    public String upload(byte[] datas, String subType) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public String upload(InputStream inputStream) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public void download(String oid, DownLoadProgress downLoadProgress) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public InputStream downloadStream(String oid) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public byte[] downloadBytes(String oid) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public boolean delete(String oid) {
        throw new DfsNotSurportException("方法未实现");
    }

    @Override
    public StorageInfo queryStorageInfo(String oid) {
        throw new DfsNotSurportException("方法未实现");
    }

}
