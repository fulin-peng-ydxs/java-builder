package build.response.meta;

import build.builder.meta.BuildResult;

import java.io.OutputStream;

/**
 * 构建处理器
 *
 * @author peng_fu_lin
 * 2022-09-02 16:00
 */
public abstract class BuilderResponse {

    /**响应方法：用于处理为输出流的构建结果
     * 2022/9/5 0005-09:56
     * @author pengfulin
    */
    public  void buildResponse(BuildResult buildResult) throws BuildResponseException{
        throw new BuildResponseException("Override this method");
    };

    /**响应方法：用于处理为字节数组的构建结果
     * 2022/9/5 0005-09:57
     * @author pengfulin
    */
    public void buildResponse(BuildResult buildResult,OutputStream outputStream) throws BuildResponseException {
        buildResponse(buildResult);
    };

    /**是否支持处理此输出流的响应
     * 2022/9/5 0005-09:57
     * @author pengfulin
    */
    public abstract boolean  isSupported(OutputStream outputStream,BuildResult buildResult);
}
