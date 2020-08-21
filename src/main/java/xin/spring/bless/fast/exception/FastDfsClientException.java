package xin.spring.bless.fast.exception;

/**
 * 描述，FastDfsClientException 客户端异常
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class FastDfsClientException extends FastFileException {

    public FastDfsClientException() {
        super();
    }

    public FastDfsClientException(String message) {
        super(message);
    }

    public FastDfsClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDfsClientException(Throwable cause) {
        super(cause);
    }

    protected FastDfsClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
