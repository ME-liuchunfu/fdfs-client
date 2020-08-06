package xin.spring.bless.fast.pojo;

import java.io.Serializable;

/**
 * 描述，Storage文件信息
 * 作者： liuchunfu
 * 时间：2020-07-31 16:11
 */
public class StorageInfo implements Serializable {

    private static final long serialVersionUID = 362498820763181265L;

    /**
     * 相对路径
     */
    private String absPath;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 协议地址
     */
    private String uri;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 节点存储类型
     */
    private String storageType;

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    @Override
    public String toString() {
        return "StorageInfo{" +
                "absPath='" + absPath + '\'' +
                ", size=" + size +
                ", uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", storageType='" + storageType + '\'' +
                '}';
    }

}
