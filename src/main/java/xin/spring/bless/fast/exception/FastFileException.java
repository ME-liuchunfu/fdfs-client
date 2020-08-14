package xin.spring.bless.fast.exception;

/**
 * 描述，FastFileException 文件异常
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class FastFileException extends RuntimeException{

    public FastFileException() {
        super();
    }

    public FastFileException(String message) {
        super(message);
    }

    public FastFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastFileException(Throwable cause) {
        super(cause);
    }

    protected FastFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
