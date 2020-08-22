package xin.spring.bless.fast.service.impl;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import xin.spring.bless.fast.commons.lang.utils.IOUtil;
import xin.spring.bless.fast.conf.StorageType;
import xin.spring.bless.fast.exception.FastDfsClientException;
import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.UploadProgress;
import java.io.*;
import java.util.Objects;

/**
 * 描述，FastDfsStorageServiceImpl fastdfs存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class FastDfsStorageServiceImpl extends AbsStorageService{

    @Override
    public void init() {

    }

    /**
     * 方法描述：创建客户端程序
     * @return StorageClient1
     */
    protected StorageClient1 createStorageClient1(){
        StorageClient1 sc1 = null;
        try {
            logger.info("创建TrackerClient创建客户端");
            //创建客户端
            TrackerClient tc = new TrackerClient();
            //连接tracker Server
            logger.info("创建TrackerServer创建客户端");
            TrackerServer ts = tc.getConnection();
            if (ts == null) {
                logger.info("创建TrackerServer创建客户端 失败");
                throw new FastDfsClientException("创建TrackerServer失败");
            }
            //获取一个storage server
            logger.info("创建StorageServer创建客户端");
            StorageServer ss = tc.getStoreStorage(ts);
            if (ss == null) {
                logger.info("创建StorageServer创建客户端 失败");
                throw new FastDfsClientException("创建StorageServer失败");
            }
            //创建一个storage存储客户端
            logger.info("创建一个storage存储客户端");
            sc1 = new StorageClient1(ts, ss);
        } catch (IOException e) {
            logger.error("创建StorageServer失败" , e.getMessage());
            throw new FastDfsClientException("创建StorageServer失败", e);
        }
        return sc1;
    }

    @Override
    public String upload(File uploadFile, UploadProgress progress) {
        return this.upload(uploadFile, progress, null);
    }

    /**
     * 方法描述： 带媒体方式上传文件
     * @param uploadFile 待上传的文件
     * @param progress 上传进度
     * @param pairs 媒体类型
     * @return 上传成功的oid
     */
    public String upload(File uploadFile, UploadProgress progress, NameValuePair ...pairs) {
        try {
            StorageClient1 sc1 = createStorageClient1();
            String uuid = uuid() + uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".")+1).toLowerCase();
            NameValuePair[] valuePairs = null;
            if (pairs != null && pairs.length > 0) {
                valuePairs = pairs;
            }
            if (Objects.nonNull(progress)) {
                progress.progress(0, uploadFile.getTotalSpace(), false, null);
            }
            String oid = sc1.upload_file1(uploadFile.getAbsolutePath(), uuid, valuePairs);
            if (Objects.nonNull(progress)) {
                progress.progress(uploadFile.getTotalSpace(), uploadFile.getTotalSpace(), true, oid);
            }
            logger.info("文件上传成功，路径{}", oid);
            return oid;
        } catch (IOException e) {
            logger.error("IOException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("IOException FastDfsClient异常, {}", e);
        } catch (MyException e) {
            logger.error("MyException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("MyException FastDfsClient异常, {}", e);
        }
    }

    @Override
    public String upload(byte[] datas, UploadProgress progress) {
        return this.upload(datas, null, progress);
    }

    @Override
    public String upload(byte[] datas, String subType, UploadProgress progress) {
        return this.upload(datas, subType, progress, null);
    }

    /**
     * 方法描述: 带媒体方式上传文件
     * @param datas 文件byte数据
     * @param subType 文件后缀
     * @param progress 上传进度
     * @param pairs 媒体类型
     * @return 上传成功的oid
     */
    public String upload(byte[] datas, String subType, UploadProgress progress, NameValuePair ...pairs) {
        try {
            StorageClient1 sc1 = createStorageClient1();
            String uuid = uuid();
            NameValuePair[] valuePairs = null;
            if (pairs != null && pairs.length > 0) {
                valuePairs = pairs;
            }
            if (Objects.nonNull(progress)) {
                progress.progress(0, datas.length, false, null);
            }
            String oid = sc1.upload_file1(datas, uuid, valuePairs);
            if (Objects.nonNull(progress)) {
                progress.progress(datas.length, datas.length, true, oid);
            }
            logger.info("文件上传成功，路径{}", oid);
            return oid;
        } catch (IOException e) {
            logger.error("IOException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("IOException FastDfsClient异常, {}", e);
        } catch (MyException e) {
            logger.error("MyException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("MyException FastDfsClient异常, {}", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, UploadProgress progress) {
        return this.upload(inputStream, null, progress);
    }

    @Override
    public String upload(InputStream inputStream, String fileSize, UploadProgress progress) {
        return this.upload(inputStream, fileSize, progress, null);
    }

    /**
     * 方法描述： 带媒体方式上传文件
     * @param inputStream 文件流、
     * @param fileSize 文件大小
     * @param progress 上传进度
     * @param pairs 文件媒体类型
     * @return 上传成功的oid
     */
    public String upload(InputStream inputStream, String fileSize, UploadProgress progress, NameValuePair ...pairs) {
        try {
            StorageClient1 sc1 = createStorageClient1();
            String uuid = uuid();
            NameValuePair[] valuePairs = null;
            if (pairs != null && pairs.length > 0) {
                valuePairs = pairs;
            }
            long size = 0L;
            if (Objects.isNull(fileSize)) {
                size = inputStream.available();
            } else {
                size = Long.valueOf(fileSize);
            }
            final long finalSize = size;
            String oid = sc1.upload_file1(null, finalSize, new UploadCallback() {
                @Override
                public int send(OutputStream out) throws IOException {
                    BufferedInputStream bos = new BufferedInputStream(inputStream);
                    int len = 0;
                    long current = 0;
                    byte[] bytes = new byte[SIZE_DATA];
                    while ((len = bos.read(bytes)) != -1) {
                        current += len;
                        out.write(bytes, 0, len);
                        logger.info("文件上传中，进度{}", current / finalSize);
                        if (Objects.nonNull(progress)) {
                            progress.progress(current, finalSize, false, null);
                        }
                    }
                    if (Objects.nonNull(progress)) {
                        progress.progress(current, finalSize, true, null);
                    }
                    return 0;
                }
            }, uuid, valuePairs);
            logger.info("文件上传成功，路径{}", oid);
            return oid;
        } catch (IOException e) {
            logger.error("IOException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("IOException FastDfsClient异常, {}", e);
        } catch (MyException e) {
            logger.error("MyException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("MyException FastDfsClient异常, {}", e);
        } finally {
            IOUtil.closeQuietly(inputStream);
        }
    }

    @Override
    public String upload(File uploadFile) {
        return this.upload(uploadFile, null);
    }

    @Override
    public String upload(byte[] datas) {
        return this.upload(datas, (String) null);
    }

    @Override
    public String upload(byte[] datas, String subType) {
        return this.upload(datas, subType, null);
    }

    @Override
    public String upload(InputStream inputStream) {
        return this.upload(inputStream, null);
    }

    @Override
    public void download(String oid, DownLoadProgress downLoadProgress) {
        try {
            StorageClient1 sc1 = createStorageClient1();
            sc1.download_file1(oid, new DownloadCallback() {
                @Override
                public int recv(long file_size, byte[] data, int bytes) {
                    if (Objects.nonNull(downLoadProgress)) {
                        downLoadProgress.progress(data, bytes, 0, file_size, false);
                    }
                    return 0;
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        }
    }

    @Override
    public InputStream downloadStream(String oid) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(this.downloadBytes(oid));
        return inputStream;
    }

    @Override
    public byte[] downloadBytes(String oid) {
        try {
            StorageClient1 sc1 = createStorageClient1();
            byte[] bytes = sc1.download_file1(oid);
            return bytes;
        } catch (IOException e) {
            logger.error("fastdfs文件下载异常，{}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件下载异常，{}", e);
        } catch (MyException e) {
            logger.error("fastdfs文件下载异常，{}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件下载异常，{}", e);
        }
    }

    @Override
    public boolean delete(String oid) {
        try {
            StorageClient1 sc1 = this.createStorageClient1();
            sc1.delete_file1(oid);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        }
        return true;
    }

    /**
     * 方法描述： 删除fastdfs文件
     * @param group 分组名称
     * @param name 文件名称
     * @return 删除成功返回true，失败返回false
     */
    public boolean delete(String group, String name) {
        try {
            StorageClient1 sc1 = this.createStorageClient1();
            sc1.delete_file(group, name);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("fastdfs文件删除失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件删除失败, {}", e);
        }
        return true;
    }

    @Override
    public StorageInfo queryStorageInfo(String oid) {
        StorageInfo storageInfo = null;
        try {
            StorageClient1 sc1 = this.createStorageClient1();
            FileInfo fileInfo = sc1.query_file_info1(oid);
            storageInfo = new StorageInfo();
            if (Objects.nonNull(fileInfo)) {
                storageInfo.setAbsPath(oid);
                storageInfo.setSize(fileInfo.getFileSize());
                storageInfo.setStorageType(StorageType.FASTDFS);
                storageInfo.setUri(oid);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("fastdfs文件查询失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件查询失败, {}", e);
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("fastdfs文件查询失败, {}", e.getMessage());
            throw new FastDfsClientException("fastdfs文件查询失败, {}", e);
        }
        return storageInfo;
    }
}
