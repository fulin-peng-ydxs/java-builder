package builder.core.build.resolve.database.mapper;


/**
 * BuildBean信息映射
 *
 * @author peng_fu_lin
 * 2022-09-05 17:58
 */
public class ColumnMapper {

    private static final String  defaultTowMapperKey="YES";

    /**非空映射
     * 2022/9/5 0005-17:59
     * @author pengfulin
    */
    public static boolean mapperNullType(String mapperKey){
        return mapperKey.equalsIgnoreCase(defaultTowMapperKey);
    }

    /**主键映射
     * 2022/9/5 0005-18:10
     * @author pengfulin
    */
    public static boolean mapperPrimaryKey(String mapperKey){
        return mapperKey.equalsIgnoreCase("PRI");
    }
}
