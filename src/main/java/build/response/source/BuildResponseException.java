package build.response.source;

/**
 * 构建响应异常
 *
 * @author peng_fu_lin
 * 2022-09-05 10:07
 */
public class BuildResponseException extends Exception{

    public BuildResponseException(String message) {
        super(message);
    }

    public BuildResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}