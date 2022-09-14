package build.source.resolver.meta;

import build.source.resolver.exception.BuildSourceResolverException;
import build.source.meta.BuildSource;

/**构建源解析器
 *
 * @author peng_fu_lin
 * 2022-09-05 13:56
 */
public abstract class BuildSourceResolver<T> {

    /**是否支持此数据源的构建
     * 2022/9/2 0002-16:23
     * @author pengfulin
     */
    public abstract boolean isSupported(BuildSource builderSource,Class<?> resolverType);

    /**解析构建源的数据模型
     * 2022/9/5 0005-14:17
     * @author pengfulin
    */
    public abstract T resolve(BuildSource builderSource) throws BuildSourceResolverException;
}
