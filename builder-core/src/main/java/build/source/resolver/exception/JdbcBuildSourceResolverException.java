package build.source.resolver.exception;

/**
 * jdbc构建源解析异常
 *
 * @author peng_fu_lin
 * 2022-09-05 16:22
 */
public class JdbcBuildSourceResolverException extends BuildSourceResolverException{

    public JdbcBuildSourceResolverException(String message) {
        super(message);
    }

    public JdbcBuildSourceResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}