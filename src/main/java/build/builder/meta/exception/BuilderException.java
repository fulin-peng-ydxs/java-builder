package build.builder.meta.exception;

/**
 * 构建器异常
 *
 * @author peng_fu_lin
 * 2022-09-06 15:35
 */
public class BuilderException extends Exception{

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}