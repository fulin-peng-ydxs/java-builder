package build.source.resolver.exception;

/**
 * 构建源解析异常
 *
 * @author peng_fu_lin
 * 2022-09-05 16:03
 */
public class BuildSourceResolverException extends Exception{

    public BuildSourceResolverException(String message) {
        super(message);
    }

    public BuildSourceResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}