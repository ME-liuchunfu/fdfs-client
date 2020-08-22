package xin.spring.bless.fast.exception;

/**
 * 描述，OssDfsException oss 存储实现异常
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class OssDfsException extends FastFileException {

    public OssDfsException() {
        super();
    }

    public OssDfsException(String message) {
        super(message);
    }

    public OssDfsException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssDfsException(Throwable cause) {
        super(cause);
    }

    protected OssDfsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
