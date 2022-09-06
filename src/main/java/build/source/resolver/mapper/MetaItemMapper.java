package build.source.resolver.mapper;

import build.source.data.enums.NullType;
import build.source.data.enums.PrimaryKey;

/**
 * 元信息项模型映射
 *
 * @author peng_fu_lin
 * 2022-09-05 17:58
 */
public interface MetaItemMapper {

    String defaultTowMapperKey="YES";

    /**非空模型映射
     * 2022/9/5 0005-17:59
     * @author pengfulin
    */
    default NullType mapperNullType(String mapperKey){
        if(mapperKey.equalsIgnoreCase(defaultTowMapperKey))
            return NullType.YES_NULL;
        return NullType.NO_NULL;
    }

    /**主键模型映射
     * 2022/9/5 0005-18:10
     * @author pengfulin
    */
    default PrimaryKey mapperPrimaryKey(String mapperKey){
        if(mapperKey.equalsIgnoreCase("PRI"))
            return PrimaryKey.YES_PRI;
        return PrimaryKey.NO_PRI;
    }
}
