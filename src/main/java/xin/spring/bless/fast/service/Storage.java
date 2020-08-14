package xin.spring.bless.fast.service;

import xin.spring.bless.fast.pojo.StorageInfo;
import java.io.File;
import java.io.InputStream;

/**
 * 描述，节点服务接口
 * 作者： liuchunfu
 * 时间：2020-07-31 16:11
 */
public interface Storage {

    /***************************** upload ****************************/

    /**
     * 方法描述： 文件上传
     * @param uploadFile 目标文件
     * @param progress 上传进度监听
     */
    String upload(File uploadFile, UploadProgress progress);

    /**
     * 方法描述： 文件上传
     * @param datas 目标文件
     * @param progress 上传进度监听
     */
    String upload(byte[] datas, UploadProgress progress);

    /**
     * 方法描述： 文件上传
     * @param inputStream 目标文件
     * @param progress 上传进度监听
     */
    String upload(InputStream inputStream, UploadProgress progress);

    /**
     * 方法描述： 文件上传
     * @param uploadFile 目标文件
     * @return 返回目标路劲
     */
    String upload(File uploadFile);

    /**
     * 方法描述： 文件上传
     * @param datas 目标文件
     * @return 返回目标路劲
     */
    String upload(byte[] datas);

    /**
     * 方法描述： 文件上传
     * @param inputStream 目标文件
     * @return 返回目标路劲
     */
    String upload(InputStream inputStream);

    /***************************** download ****************************/

    /**
     * 方法描述： 文件下载
     * @param oid 文件标识
     * @param downLoadProgress 下载进度
     */
    void download(String oid, DownLoadProgress downLoadProgress);

    /**
     * 方法描述： 文件下载
     * @param oid 文件标识
     * @return InputStream
     */
    InputStream downloadStream(String oid);

    /**
     * 方法描述： 文件下载
     * @param oid 文件标识
     * @return byte[]
     */
    byte[] downloadBytes(String oid);

    /***************************** delete ****************************/

    /**
     * 方法描述： 删除文件
     * @param oid 文件标识
     * @return true删除成功，false删除失败
     */
    boolean delete(String oid);

    /***************************** delete ****************************/

    /**
     * 方法描述： 查询节点信息
     * @param oid 节点标识
     * @return 节点信息类
     */
    StorageInfo queryStorageInfo(String oid);

}
