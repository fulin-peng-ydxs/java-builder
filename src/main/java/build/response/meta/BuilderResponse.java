package build.response.meta;

import build.builder.data.BuildResult;
import build.response.exception.BuildResponseException;
import java.io.OutputStream;
import java.util.List;

/**
 * 构建处理器
 *
 * @author peng_fu_lin
 * 2022-09-02 16:00
 */
public abstract class BuilderResponse {

    /**响应构建流数据
     * 2022/9/5 0005-09:56
     * @author pengfulin
    */
    public abstract void buildResponse(OutputStream outputStream,List<BuildResult> buildResults) throws BuildResponseException;

    /**是否支持处理此输出流的响应
     * 2022/9/5 0005-09:57
     * @author pengfulin
    */
    public abstract boolean  isSupported(OutputStream outputStream);
}
