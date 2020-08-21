package xin.spring.bless.fast.service.impl;

import xin.spring.bless.fast.commons.lang.utils.DateUtil;
import xin.spring.bless.fast.commons.lang.utils.FileUtil;
import xin.spring.bless.fast.commons.lang.utils.IOUtil;
import org.apache.commons.lang.StringUtils;
import xin.spring.bless.fast.conf.StorageType;
import xin.spring.bless.fast.exception.FastFileException;
import xin.spring.bless.fast.pojo.StorageInfo;
import xin.spring.bless.fast.proper.StorageMap;
import xin.spring.bless.fast.service.DownLoadProgress;
import xin.spring.bless.fast.service.UploadProgress;
import java.io.*;
import java.util.Objects;

/**
 * 描述，LocalStorageServiceImpl 本地存储实现
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class LocalStorageServiceImpl extends AbsStorageService{

    @Override
    public void init() {

    }

    @Override
    public String upload(byte[] datas) {
        // 获取配置信息
        Object[] obj = createConfig(null);
        String parent = ((StorageMap) obj[0]).get(StorageType.LOCAL_PATH_KEY).toString();
        // 文件存储地址
        String path = obj[4].toString();
        File file = new File(parent, path);
        try {
            long start = System.currentTimeMillis();
            logger.info("本地文件上传中：{}", file.getAbsolutePath());
            FileUtil.writeByteArrayToFile(file, datas);
            long end = System.currentTimeMillis();
            logger.info("本地文件已上传，耗时{}", DateUtil.format2TimeStr(start, end));
        } catch (IOException e) {
            logger.error("本地服务文件上传异常", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件写入异常", e);
        }
        return path;
    }

    @Override
    public String upload(InputStream inputStream, UploadProgress progress) {
        return this.upload(inputStream, null, progress);
    }

    @Override
    public String upload(File uploadFile, UploadProgress progress) {
        // 获取配置信息
        String subType = uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".")+1).toLowerCase();
        Object[] obj = createConfig(subType);
        String parent = ((StorageMap) obj[0]).get(StorageType.LOCAL_PATH_KEY).toString();
        String path = obj[4].toString();
        File file = new File(parent, path);
        try {
            if (Objects.nonNull(progress)) {
                progress.progress(0, uploadFile.length(), false, path);
            }
            long start = System.currentTimeMillis();
            logger.info("本地文件上传中：{}", file.getAbsolutePath());
            FileUtil.copyFile(uploadFile, file);
            long end = System.currentTimeMillis();
            logger.info("本地文件已上传，耗时{}", DateUtil.format2TimeStr(start, end));
            if (Objects.nonNull(progress)) {
                progress.progress(uploadFile.length(), uploadFile.length(), true, path);
            }
        } catch (IOException e) {
            logger.error("本地服务文件上传异常", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件写入异常", e);
        }
        return path;
    }

    @Override
    public String upload(byte[] datas, UploadProgress progress) {
        return this.upload(datas, null, progress);
    }

    @Override
    public String upload(File uploadFile) {
        return this.upload(uploadFile, null);
    }

    @Override
    public String upload(InputStream inputStream) {
        // 获取配置信息
        Object[] obj = createConfig(null);
        String parent = ((StorageMap) obj[0]).get(StorageType.LOCAL_PATH_KEY).toString();
        String path = obj[4].toString();
        File file = new File(parent, path);
        try {
            long start = System.currentTimeMillis();
            logger.info("本地文件上传中：{}", file.getAbsolutePath());
            FileUtil.copyInputStreamToFile(inputStream, file);
            long end = System.currentTimeMillis();
            logger.info("本地文件已上传，耗时{}", DateUtil.format2TimeStr(start, end));
        } catch (IOException e) {
            logger.error("本地服务文件上传异常", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件写入异常", e);
        }
        return path;
    }

    @Override
    public void download(String oid, DownLoadProgress downLoadProgress) {
        if (Objects.nonNull(downLoadProgress)) {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                if (Objects.nonNull(downLoadProgress)) {
                    // 获取配置信息
                    StorageMap config = getConfig();
                    // 存储根目录
                    String parent = config.get(StorageType.LOCAL_PATH_KEY).toString();
                    File file = new File(parent, oid);
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    byte[] data = new byte[SIZE_DATA];
                    int len = 0;
                    long current = 0;
                    long total = file.length();
                    long start = System.currentTimeMillis();
                    logger.info("本地文件下载中：{}", file.getAbsolutePath());
                    while ((len = bis.read(data)) != -1) {
                        current += len;
                        downLoadProgress.progress(data, len, current, total, current >= total);
                    }
                    long end = System.currentTimeMillis();
                    logger.info("本地文件已下载，耗时{}", DateUtil.format2TimeStr(start, end));
                }
            } catch (Exception e) {
                logger.error("本地服务文件下载异常", e.getMessage());
                e.printStackTrace();
                throw new FastFileException(this.getClass() + "文件读取异常", e);
            } finally {
                IOUtil.closeQuietly(bis);
                IOUtil.closeQuietly(fis);
            }
        }
    }

    @Override
    public InputStream downloadStream(String oid) {
        // 获取配置信息
        StorageMap config = getConfig();
        // 存储根目录
        String parent = config.get(StorageType.LOCAL_PATH_KEY).toString();
        File file = new File(parent, oid);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            logger.error("本地服务文件下载异常", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件读取异常", e);
        }
    }

    @Override
    public byte[] downloadBytes(String oid) {
        // 获取配置信息
        StorageMap config = getConfig();
        // 存储根目录
        String parent = config.get(StorageType.LOCAL_PATH_KEY).toString();
        File file = new File(parent, oid);
        try {
            long start = System.currentTimeMillis();
            logger.info("本地文件读取中：{}", file.getAbsolutePath());
            byte[] bytes = FileUtil.readFileToByteArray(file);
            long end = System.currentTimeMillis();
            logger.info("本地文件已读取，耗时{}", DateUtil.format2TimeStr(start, end));
            return bytes;
        } catch (IOException e) {
            logger.error("本地服务文件下载异常", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件读取异常", e);
        }
    }

    @Override
    public boolean delete(String oid) {
        StorageMap config = getConfig();
        String parent = config.get(StorageType.LOCAL_PATH_KEY).toString();
        File file = new File(parent, oid);
        long start = System.currentTimeMillis();
        logger.info("本地文件删除中：{}", file.getAbsolutePath());
        boolean b = FileUtil.deleteQuietly(file);
        long end = System.currentTimeMillis();
        logger.info("本地文件已删除，耗时{}", DateUtil.format2TimeStr(start, end));
        return b;
    }

    @Override
    public StorageInfo queryStorageInfo(String oid) {
        String pref = getConfig().get(StorageType.LOCAL_PATH_KEY).toString();
        File file = new File(pref, oid);
        if (Objects.nonNull(file) && file.exists()) {
            StorageInfo storageInfo = new StorageInfo();
            storageInfo.setName(file.getName());
            storageInfo.setUri(oid);
            storageInfo.setStorageType(StorageType.LOCAL);
            storageInfo.setSize(file.getTotalSpace());
            storageInfo.setAbsPath(file.getAbsolutePath());
            return storageInfo;
        } else {
            return null;
        }
    }

    @Override
    public String upload(byte[] datas, String subType, UploadProgress progress) {
        if (Objects.isNull(datas)) {
            return null;
        }
        Object[] obj = createConfig(subType);
        String parent = ((StorageMap) obj[0]).get(StorageType.LOCAL_PATH_KEY).toString();
        // 文件存储地址
        String path = obj[4].toString();
        if (StringUtils.isNotBlank(subType)) {
            path += "." + subType.toLowerCase();
        }
        File file = new File(parent, path);
        try {
            if (Objects.nonNull(progress)) {
                progress.progress(0, datas.length, false, path);
            }
            long start = System.currentTimeMillis();
            logger.info("本地文件上传中：{}", file.getAbsolutePath());
            FileUtil.writeByteArrayToFile(file, datas);
            long end = System.currentTimeMillis();
            logger.info("本地文件上传完成，耗时{}", DateUtil.format2TimeStr(start, end));
            if (Objects.nonNull(progress)) {
                progress.progress(datas.length, datas.length, true, path);
            }
        } catch (Exception e) {
            logger.error("本地文件上传异常{}", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件写入异常", e);
        }
        return path;
    }

    @Override
    public String upload(InputStream inputStream, String subType, UploadProgress progress) {
        if (Objects.isNull(inputStream)) {
            return null;
        }
        Object[] obj = createConfig(subType);
        String parent = ((StorageMap) obj[0]).get(StorageType.LOCAL_PATH_KEY).toString();
        // 文件存储地址
        String path = obj[4].toString();
        if (StringUtils.isNotBlank(subType)) {
            path += "." + subType.toLowerCase();
        }
        File file = new File(parent, path);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] datas = new byte[SIZE_DATA];
            int len = 0;
            long current = 0;
            long start = System.currentTimeMillis();
            logger.info("本地文件上传中：{}", file.getAbsolutePath());
            while (( len = bis.read(datas)) != -1 ) {
                bos.write(datas, 0, len);
                current += len;
                if (Objects.nonNull(progress)) {
                    progress.progress(current, 0, false, path);
                }
            }
            long end = System.currentTimeMillis();
            logger.info("本地文件上传完成，耗时{}", DateUtil.format2TimeStr(start, end));
            if (Objects.nonNull(progress)) {
                progress.progress(current, 0, true, path);
            }
        } catch (Exception e) {
            logger.error("本地文件上传异常{}", e.getMessage());
            e.printStackTrace();
            throw new FastFileException(this.getClass() + "文件写入异常", e);
        } finally {
            IOUtil.closeQuietly(bos);
            IOUtil.closeQuietly(fos);
            IOUtil.closeQuietly(bis);
            IOUtil.closeQuietly(inputStream);
        }
        return path;
    }

    @Override
    public String upload(byte[] datas, String subType) {
        return this.upload(datas, subType, null);
    }

    /**
     * 方法描述： 上传生成配置信息
     * @param subType 文件类型，如 txt，word， ppt， mp4
     * @return 返回String[] index ==》 0: StorageMap config,
     *              1: parent 存储根目录， 2: date当前日期存储文件夹，
     *              3:name目标文件名，4: path文件存储相对路径
     */
    public Object[] createConfig(String subType){
        // 获取配置信息
        StorageMap config = getConfig();
        // 存储根目录
        String parent = config.get(StorageType.LOCAL_PATH_KEY).toString();
        // 当前时间日期目录
        String date = dateTimeString();
        // 文件存储名称
        String name = uuid();
        if (StringUtils.isNotBlank(subType)) {
            name += "." + subType.toLowerCase();
        }
        // 文件存储地址
        String path = date + File.separatorChar + name;
        File filePath = new File(parent, date);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        Object[] arr = {config, parent, date, name, path};
        return arr;
    }

}
