package build.builder.meta;

import build.builder.data.BuildResult;
import build.builder.meta.exception.BuildCoderException;
import build.source.data.BuildMeta;
import java.util.Map;

/**
 * 构建器
 *
 * @author peng_fu_lin
 * 2022-09-02 15:59
 */
public interface BuildCoder {
    /**构建方法
     * 2022/9/6 0006-15:31
     * @author pengfulin
    */
    BuildResult buildCode(Map<String,BuildMeta> buildMetas) throws BuildCoderException;
}
