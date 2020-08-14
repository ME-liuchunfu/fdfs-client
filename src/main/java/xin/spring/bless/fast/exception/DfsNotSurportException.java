package xin.spring.bless.fast.exception;

/**
 * 描述，DfsNotSurportException 为实现方法异常
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class DfsNotSurportException extends RuntimeException {
    public DfsNotSurportException() {
        super();
    }

    public DfsNotSurportException(String message) {
        super(message);
    }

    public DfsNotSurportException(String message, Throwable cause) {
        super(message, cause);
    }

    public DfsNotSurportException(Throwable cause) {
        super(cause);
    }

    protected DfsNotSurportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
