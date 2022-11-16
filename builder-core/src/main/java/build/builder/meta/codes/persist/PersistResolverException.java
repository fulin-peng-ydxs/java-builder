package build.builder.meta.codes.persist;

/**持续集成源解析异常
 * @author peng_fu_lin
 * 2022-09-29 11:24
 */
public class PersistResolverException extends Exception{

    public PersistResolverException(String message) {
        super(message);
    }

    public PersistResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}