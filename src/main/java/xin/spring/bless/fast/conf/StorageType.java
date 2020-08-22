package xin.spring.bless.fast.conf;

/**
 * 描述，StorageType 存储类型服务全局配置
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public interface StorageType {

    /**
     * fastdfs存储
     */
    String FASTDFS = "FASTDFS";

    /**
     * 本地存储
     */
    String LOCAL = "LOCAL";

    /**
     * 阿里云
     */
    String ALIYUN = "ALIYUN";

    /**
     * 腾讯云
     */
    String QCLOUD = "QCLOUD";

    /**
     * 七牛云
     */
    String QINIU = "QINIU";

    /**
     * 百度云
     */
    String BAIDUYUN = "BAIDUYUN";

    /**
     * 华为云
     */
    String HUAWEIYUN = "HUAWEIYUN";

    /**
     * hadoop hdfs
     */
    String HDFS = "hadoop_hdfs";

    /**
     * 服务器版本信息识别 key
     */
    String SERVER_KEY = "service";

    /**
     * 本地存储服务路径 key
     */
    String LOCAL_PATH_KEY = "path";

    /**=============== fastdfs key =================**/

    /**
     * 连接超时时间/秒 key
     */
    String fastdfs_connect_timeout_in_seconds = "fastdfs.connect_timeout_in_seconds";

    /**
     * 网络超时时间/秒 key
     */
    String fastdfs_network_timeout_in_seconds = "fastdfs.network_timeout_in_seconds";

    /**
     * 字符编码，默认UTF-8 key
     */
    String fastdfs_charset = "fastdfs.charset";

    /**
     * 客户端连接token key
     */
    String fastdfs_http_anti_steal_token = "fastdfs.http_anti_steal_token";

    /**
     * 客户端连接秘钥 key
     */
    String fastdfs_http_secret_key = "fastdfs.http_secret_key";

    /**
     * 服务器http端口 key
     */
    String fastdfs_http_tracker_http_port = "fastdfs.http_tracker_http_port";

    /**
     * 服务地址 key
     */
    String fastdfs_tracker_servers = "fastdfs.tracker_servers";

    /**=============== aliyun key =================**/

    /**
     * Endpoint以杭州为例，其它Region请按实际情况填写。
     */
    String aliyun_endpoint = "endpoint";
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
     * 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
     */
    String aliyun_access_key_id = "accessKeyId";

    /**
     * 授权码
     */
    String aliyun_access_key_secret = "accessKeySecret";

    /**
     * 服务包名称
     */
    String aliyun_bucket_name = "bucketName";

    /**=============== Qcloud key =================**/
    /**=============== Qiniu key =================**/
    /**=============== Baiduyun key =================**/
    /**=============== huaweiyun key =================**/
    /**=============== hdfs key =================**/


    /**=====================================================================================================================
     *
     * 配置文件key-value解析
     *      service：  服务类型    StorageType 下的服务类型，包括可拓展类型
     *          LOCAL：                                               本地存储
     *              path：                                            文件存储路径
     *          FASTDFS：                                             fastdfs存储
     *              connect_timeout_in_seconds：                      连接超时时间/秒
     *              network_timeout_in_seconds：                      网络超时时间/秒
     *              charset：                                         字符编码，默认UTF-8
     *              http_anti_steal_token：                           客户端连接token
     *              http_secret_key：                                 客户端连接秘钥
     *              http_tracker_http_port：                          服务器http端口
     *              tracker_servers：
     *                      服务地址：
     *                              多服务：     ip:port   ==> 10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
     *                              单服务地址： ip:port   ==> 10.0.11.201:22122
     *          ALIYUN：                                              aliyun oss存储服务
     *              endpoint：                                        Endpoint以深圳为例，http://oss-cn-shenzhen.aliyuncs.com
     *              accessKeyId:                                      accessKeyId阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
     *              accessKeySecret:                                  授权码
     *              bucketName：                                      服务资源包名称
     *
     *
     *======================================================================================================================*/

}
