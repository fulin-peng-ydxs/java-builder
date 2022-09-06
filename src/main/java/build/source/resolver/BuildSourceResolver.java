package build.source.resolver;

import build.source.data.BuildMeta;
import build.source.resolver.exception.BuildSourceResolverException;
import build.source.resolver.mapper.MetaItemMapper;
import build.source.meta.BuildSource;
import java.util.Map;
/**构建源解析器
 *
 * @author peng_fu_lin
 * 2022-09-05 13:56
 */
public abstract class BuildSourceResolver implements MetaItemMapper {

    /**是否支持此数据源的构建
     * 2022/9/2 0002-16:23
     * @author pengfulin
     */
    public abstract boolean isSupported(BuildSource builderSource);

    /**获取构建源的元数据
     * 2022/9/5 0005-14:17
     * @author pengfulin
    */
    public abstract Map<String,BuildMeta> resolver(BuildSource builderSource) throws BuildSourceResolverException;
}
