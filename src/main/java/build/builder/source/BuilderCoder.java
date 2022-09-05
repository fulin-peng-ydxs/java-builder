package build.builder.source;

import build.datasource.source.BuildDataSource;

/**
 * 构建器
 *
 * @author peng_fu_lin
 * 2022-09-02 15:59
 */
public abstract class BuilderCoder {

    /**构建方法
     * 2022/9/2 0002-16:22
     * @author pengfulin
    */
    public abstract  BuildResult buildCode (BuildDataSource builderDataSource);

    /**是否支持此数据源的构建
     * 2022/9/2 0002-16:23
     * @author pengfulin
    */
    public abstract boolean isSupported(BuildDataSource builderDataSource);
}
