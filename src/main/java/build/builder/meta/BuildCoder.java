package build.builder.meta;

import build.source.meta.source.BuildSource;

/**
 * 构建器
 *
 * @author peng_fu_lin
 * 2022-09-02 15:59
 */
public abstract class BuildCoder {

    /**构建方法
     * 2022/9/2 0002-16:22
     * @author pengfulin
    */
    public abstract  BuildResult buildCode (BuildSource builderDataSource);

}
