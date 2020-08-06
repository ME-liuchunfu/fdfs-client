package xin.spring.bless.fast.service;

/**
 * 描述：文件上传进度
 * 作者： liuchunfu
 * 时间：2020-07-31 16:11
 */
public interface UploadProgress {

    /**
     * 上传进度
     * @param current  当前进度
     * @param totle  总数目
     * @param finish  true完成，false未完成
     * @param path 文件存储路径
     */
    void progress(long current, long totle, boolean finish, String path);

}
