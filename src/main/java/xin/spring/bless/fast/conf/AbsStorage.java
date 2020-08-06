package xin.spring.bless.fast.conf;

import xin.spring.bless.fast.proper.StorageMap;

import java.io.Serializable;

/**
 * 描述，AbsStorage 存储对象
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public abstract class AbsStorage implements Serializable {

    private static final long serialVersionUID = 362498820763181265L;

    /**
     * 服务配置信息
     */
    public static StorageMap storageConfig = null;

}
