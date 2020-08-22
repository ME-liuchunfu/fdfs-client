package xin.spring.bless.fast.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.*;
import xin.spring.bless.fast.commons.lang.utils.FileUtil;
import xin.spring.bless.fast.commons.lang.utils.IOUtil;
import xin.spring.bless.fast.commons.lang.utils.StringUtils;
import xin.spring.bless.fast.conf.StorageType;
import xin.spring.bless.fast.exception.OssDfsException;
import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.proper.StorageMap;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.UploadProgress;

import java.io.*;
import java.util.Objects;

/**
 * 描述，AliyunStorageServiceImpl aliyun oss 存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class AliyunStorageServiceImpl extends AbsStorageService{

    private OSSClient client;

    @Override
    public void init() {
        initClient();
    }

    /**
     * 方法描述：初始化OSSClient
     */
    private void initClient() {
        if (Objects.isNull(client)) {
            logger.info("初始化构造 aliyun oss 服务");
            StorageMap config = getConfig();
            client = (OSSClient) new OSSClientBuilder().build(config.get(StorageType.aliyun_endpoint).toString(),
                    config.get(StorageType.aliyun_access_key_id).toString(),
                    config.get(StorageType.aliyun_access_key_secret).toString());
            logger.info("已初始化构造 aliyun oss 服务");
        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(client)) {
            client.shutdown();
            client = null;
        }
    }

    @Override
    public String upload(File uploadFile, UploadProgress progress) {
        try {
            init();
            String bucketName = getBucketName();
            String objectName = dateString() + "/" + uuid();
            String uri = getEndpoint() + "/" + objectName + "." + subfix(uploadFile.getName());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, uploadFile);
            putObjectRequest.withProgressListener(new PutObjectProgressListener(progress, uri));
            client.putObject(putObjectRequest);
            return uri;
        } catch (RuntimeException e) {
            logger.error("上传aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("上传aliyun oss文件失败", e);
        } finally {
            destroy();
        }
    }

    @Override
    public String upload(byte[] datas, UploadProgress progress) {
        return this.upload(datas, null, progress);
    }

    @Override
    public String upload(byte[] datas, String subType, UploadProgress progress) {
        try {
            init();
            String bucketName = getBucketName();
            InputStream inputStream = new ByteArrayInputStream(datas);
            String objectName = dateString() + "/" + uuid();
            String uri = getEndpoint() + "/" + objectName;
            if (StringUtils.isNotBlank(subType)) {
                uri += "." + subType;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            putObjectRequest.withProgressListener(new PutObjectProgressListener(progress, uri));
            client.putObject(putObjectRequest);
            return uri;
        } catch (RuntimeException e) {
            logger.error("下载aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件失败", e);
        } finally {
            destroy();
        }
    }

    @Override
    public String upload(InputStream inputStream, UploadProgress progress) {
        return this.upload(inputStream, null, progress);
    }

    @Override
    public String upload(InputStream inputStream, String subType, UploadProgress progress) {
        try {
            init();
            String bucketName = getBucketName();
            String objectName = dateString() + "/" + uuid();
            String uri = getEndpoint() + "/" + objectName;
            if (StringUtils.isNotBlank(subType)) {
                uri += "." + subType;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            putObjectRequest.withProgressListener(new PutObjectProgressListener(progress, uri));
            client.putObject(putObjectRequest);
            return uri;
        } catch (RuntimeException e) {
            logger.error("上传aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("上传aliyun oss文件失败", e);
        } finally {
            destroy();
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
        if (StringUtils.isBlank(oid)) {
            logger.info("下载aliyun oss文件是失败，oid为空，{}", oid);
            return;
        }
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            init();
            String bucketName = getBucketName();
            logger.info("正在下载aliyun oss文件，{}", oid);
            OSSObject object = client.getObject(bucketName, oid);
            is = object.getObjectContent();
            bis = new BufferedInputStream(is);
            if (Objects.nonNull(downLoadProgress)) {
                byte[] bytes = new byte[SIZE_DATA];
                long current = 0;
                ObjectMetadata objectMetadata = object.getObjectMetadata();
                long totle = objectMetadata.getContentLength();
                long len = 0;
                while ((len = bis.read(bytes)) != -1) {
                    logger.info("正在下载aliyun oss文件，进度{}", (current / (double) totle * 100) + "%");
                    current += len;
                    downLoadProgress.progress(bytes, len, current, totle, false);
                }
                logger.info("下载aliyun oss文件完成，{}", oid);
                downLoadProgress.progress(null, 0, current, totle, true);
            }
        } catch (RuntimeException e) {
            logger.error("下载aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件失败", e);
        } catch (IOException e) {
            logger.error("下载aliyun oss文件转换失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件转换失败", e);
        } finally {
            IOUtil.closeQuietly(bis);
            IOUtil.closeQuietly(is);
            destroy();
        }
    }

    @Override
    public InputStream downloadStream(String oid) {
        if (StringUtils.isBlank(oid)) {
            logger.info("下载aliyun oss文件失败，oid为空，{}", oid);
            return null;
        }
        try {
            init();
            String bucketName = getBucketName();
            OSSObject object = client.getObject(bucketName, oid);
            InputStream stream = object.getObjectContent();
            return stream;
        } catch (RuntimeException e) {
            logger.error("下载aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件失败", e);
        } finally {
            destroy();
        }
    }

    @Override
    public byte[] downloadBytes(String oid) {
        if (StringUtils.isBlank(oid)) {
            logger.info("下载aliyun oss文件是失败，oid为空，{}", oid);
            return null;
        }
        InputStream stream = null;
        try {
            init();
            String bucketName = getBucketName();
            logger.info("正在下载aliyun oss文件，{} ，{}", bucketName, oid);
            OSSObject object = client.getObject(bucketName, oid);
            stream = object.getObjectContent();
            byte[] bytes = FileUtil.toByteArray(stream);
            return bytes;
        } catch (RuntimeException e) {
            logger.error("下载aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件失败", e);
        } catch (IOException e) {
            logger.error("下载aliyun oss文件转换失败，{}", e.getMessage());
            throw new OssDfsException("下载aliyun oss文件转换失败", e);
        } finally {
            IOUtil.closeQuietly(stream);
            destroy();
        }
    }

    @Override
    public boolean delete(String oid) {
        if (StringUtils.isBlank(oid)) {
            logger.info("删除aliyun oss文件失败，oid为空，{}", oid);
            return false;
        }
        try {
            init();
            String endpoint = getEndpoint();
            logger.info("正常删除aliyun oss文件，{}", oid);
            client.deleteObject(endpoint, oid);
            logger.info("正常删除aliyun oss文件，{}", oid);
        } catch (RuntimeException e) {
            logger.error("删除aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("删除aliyun oss文件失败", e);
        } finally {
            destroy();
        }
        return true;
    }

    @Override
    public StorageInfo queryStorageInfo(String oid) {
        if (StringUtils.isBlank(oid)) {
            logger.info("未找到aliyun oss文件，oid为空，{}", oid);
            return null;
        }
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setAbsPath(oid);
        storageInfo.setStorageType(StorageType.ALIYUN);
        storageInfo.setUri(getEndpoint() + oid);
        storageInfo.setName(oid);
        try {
            init();
            String bucketName = getBucketName();
            logger.info("正在查询aliyun oss文件，{}", oid);
            OSSObject object = client.getObject(bucketName, oid);
            ObjectMetadata metadata = object.getObjectMetadata();
            storageInfo.setSize(metadata.getContentLength());
        } catch (RuntimeException e) {
            logger.error("查询aliyun oss文件失败，{}", e.getMessage());
            throw new OssDfsException("查询aliyun oss文件失败", e);
        } finally {
            destroy();
        }
        return storageInfo;
    }

    /**
     * 方法描述： 获取endpoint
     * @return
     */
    protected String getEndpoint(){
        StorageMap config = getConfig();
        return config.get(StorageType.aliyun_endpoint).toString();
    }

    /**
     * 方法描述： 获取BucketName
     * @return
     */
    protected String getBucketName(){
        StorageMap config = getConfig();
        return config.get(StorageType.aliyun_bucket_name).toString();
    }

    /**
     * 上传监听
     */
    static class PutObjectProgressListener implements ProgressListener {

        private long bytesWritten = 0;
        private long totalBytes = -1;
        private boolean succeed = false;

        private UploadProgress progress;

        private String uri;

        public PutObjectProgressListener(UploadProgress progress, String uri) {
            this.progress = progress;
            this.uri = uri;
        }

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            if (Objects.nonNull(progress)) {
                long bytes = progressEvent.getBytes();
                ProgressEventType eventType = progressEvent.getEventType();
                switch (eventType) {
                    case TRANSFER_STARTED_EVENT:
                        logger.info("aliyun oss服务开始上传");
                        progress.progress(0, 0, succeed, uri);
                        break;
                    case REQUEST_CONTENT_LENGTH_EVENT:
                        this.totalBytes = bytes;
                        logger.info(this.totalBytes + " 字节将会上传至aliyun oss");
                        break;
                    case REQUEST_BYTE_TRANSFER_EVENT:
                        this.bytesWritten += bytes;
                        if (this.totalBytes != -1) {
                            int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                            logger.info(bytes + " 字节已上传,当前进度: " +  percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                            progress.progress(this.bytesWritten, this.totalBytes, succeed, uri);
                        } else {
                            logger.info(bytes + " 字节已上传,当前进度:" + "(" + this.bytesWritten + "/...)");
                        }
                        break;
                    case TRANSFER_COMPLETED_EVENT:
                        this.succeed = true;
                        logger.info("上传完成, " + this.bytesWritten + " 字节");
                        progress.progress(this.bytesWritten, this.totalBytes, succeed, uri);
                        break;
                    case TRANSFER_FAILED_EVENT:
                        logger.error("上传失败, " + this.bytesWritten + " 字节");
                        progress.progress(this.bytesWritten, this.totalBytes, succeed, uri);
                        break;
                    default:
                        break;
                }
            }
        }

        public boolean isSucceed() {
            return succeed;
        }
    }

    /**
     * 下载监听
     */
    static class GetObjectProgressListener implements ProgressListener {

        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to download......");
                    break;
                case RESPONSE_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
                    break;
                case RESPONSE_BYTE_TRANSFER_EVENT:
                    this.bytesRead += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                        System.out.println(bytes + " bytes have been read at this time, download progress: " +
                                percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" +
                                "(" + this.bytesRead + "/...)");
                    }
                    break;

                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
                    break;

                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
                    break;

                default:
                    break;
            }
        }

        public boolean isSucceed() {
            return succeed;
        }
    }

}
