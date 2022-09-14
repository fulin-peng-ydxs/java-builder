package build.builder.meta;

import build.builder.data.BuildResult;
import build.builder.meta.exception.BuilderException;
/**
 * 构建器
 *
 * @author peng_fu_lin
 * 2022-09-02 15:59
 */
public abstract class BuildCoder<T> {

    /**模型构建
     * 2022/9/6 0006-15:31
     * @author pengfulin
    */
    public abstract BuildResult buildCode(Object buildDataModel) throws BuilderException;

    /**可支持的构建模型
     * 2022/9/7 0007-15:30
     * @author pengfulin
    */
    public abstract Class<?> supportedBuildSource();

    /**根据数据模型获取构建模型
     * 2022/9/8 0008-14:21
     * @author pengfulin
     */
    protected abstract T getBuildModel(Object buildDataModel);
}
