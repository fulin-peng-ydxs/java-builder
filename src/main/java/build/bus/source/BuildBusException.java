package build.bus.source;

/**
 * 构建总线异常
 *
 * @author peng_fu_lin
 * 2022-09-02 16:38
 */
public class BuildBusException extends Exception{

    public BuildBusException(String message) {
        super(message);
    }

    public BuildBusException(String message, Throwable cause) {
        super(message, cause);
    }
}