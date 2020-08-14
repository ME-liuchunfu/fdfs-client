package xin.spring.bless.fast.service;

/**
 * 描述：文件下载进度
 * 作者： liuchunfu
 * 时间：2020-07-31 16:11
 */
public interface DownLoadProgress {

    /**
     * 方法描述： 上传进度
     *       数据从0开始，读取到readLen长度，存储在datas数组
     * @param datas  读取字节数组
     * @param readLen  读取长度
     * @param current  当前进度
     * @param totle  总数目
     * @param finish  true完成，false未完成
     */
    void progress(byte[] datas,long readLen,long current, long totle, boolean finish);

}
