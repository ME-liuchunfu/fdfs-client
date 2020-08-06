package xin.spring.bless.fast.service.impl;

import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.UploadProgress;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 描述，AbsStorageService 存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public abstract class AbsStorageService implements Storage {

    /**
     * 方法描述: 初始化服务配置
     */
    public abstract void init();

    /**
     * 方法描述： 销毁实例配置
     */
    public void destroy() {}

    @Override
    public void upload(File uploadFile, UploadProgress progress) {

    }

    @Override
    public void upload(byte[] datas, UploadProgress progress) {

    }

    @Override
    public void upload(InputStream inputStream, UploadProgress progress) {

    }

    @Override
    public String upload(File uploadFile) {
        return null;
    }

    @Override
    public String upload(byte[] datas) {
        return null;
    }

    @Override
    public String upload(InputStream inputStream) {
        return null;
    }

    @Override
    public void download(String oid, DownLoadProgress downLoadProgress) {

    }

    @Override
    public OutputStream downloadStream(String oid) {
        return null;
    }

    @Override
    public byte[] downloadBytes(String oid) {
        return new byte[0];
    }

    @Override
    public boolean delete(String oid) {
        return false;
    }

    @Override
    public StorageInfo queryStorageInfo(String oid) {
        return null;
    }

}
