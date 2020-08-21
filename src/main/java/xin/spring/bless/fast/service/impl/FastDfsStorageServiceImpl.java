package xin.spring.bless.fast.service.impl;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import xin.spring.bless.fast.conf.StorageType;
import xin.spring.bless.fast.exception.FastDfsClientException;
import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.UploadProgress;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 描述，FastDfsStorageServiceImpl fastdfs存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class FastDfsStorageServiceImpl extends AbsStorageService{

    @Override
    public void init() {
        String path = "";
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
            StorageClient1 sc1 = new StorageClient1(ts, ss);
            NameValuePair[] meta_list = null; //new NameValuePair[0];
            int i = path.lastIndexOf(".");
            String subFix = path.substring(i + 1);
            String fileId = sc1.upload_file1(path, subFix, meta_list);
            logger.info("上传成功，远程节点地址：{}", fileId);
        }catch (IOException e) {
            logger.error("IOException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("创建FastDfsClient IOException失败", e);
        } catch (MyException e) {
            logger.error("IOException FastDfsClient异常, {}", e.getMessage());
            throw new FastDfsClientException("创建FastDfsClient MyException失败", e);
        }
    }

    public void query() throws IOException, MyException {
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
        StorageClient1 sc1 = new StorageClient1(ts, ss);
        String fileId = "";
        FileInfo fileInfo = sc1.get_file_info1(fileId);
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
        return super.upload(uploadFile, progress);
    }

    @Override
    public String upload(byte[] datas, UploadProgress progress) {
        return super.upload(datas, progress);
    }

    @Override
    public String upload(byte[] datas, String subType, UploadProgress progress) {
        return super.upload(datas, subType, progress);
    }

    @Override
    public String upload(InputStream inputStream, UploadProgress progress) {
        return super.upload(inputStream, progress);
    }

    @Override
    public String upload(InputStream inputStream, String subType, UploadProgress progress) {
        return super.upload(inputStream, subType, progress);
    }

    @Override
    public String upload(File uploadFile) {
        return super.upload(uploadFile);
    }

    @Override
    public String upload(byte[] datas) {
        return super.upload(datas);
    }

    @Override
    public String upload(byte[] datas, String subType) {
        return super.upload(datas, subType);
    }

    @Override
    public String upload(InputStream inputStream) {
        return super.upload(inputStream);
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
