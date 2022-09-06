package build.builder.meta.exception;

/**
 * 构建器异常
 *
 * @author peng_fu_lin
 * 2022-09-06 15:35
 */
public class BuildCoderException extends Exception{
    public BuildCoderException(String message) {
        super(message);
    }

    public BuildCoderException(String message, Throwable cause) {
        super(message, cause);
    }
}