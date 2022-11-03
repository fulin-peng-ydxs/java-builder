package build.source.data.meta.mybatis;

import build.source.data.meta.BuildResolverModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
/**
 * Mybatis实体数据模型
 *
 * @author peng_fu_lin
 * 2022-10-09 15:19
 */
@Getter
@Setter
@AllArgsConstructor
public class MybatisBuildBean  extends BuildResolverModel {

    /**实体信息*/
    private BeanInfo beanInfo;
    /**表信息*/
    private TableInfo tableInfo;
    /**实体字段映射*/
    private Map<String,String> entityFields;

    @Setter
    @Getter
    public static class TableInfo{
        /**表名*/
        private String tableName;
        /**主键信息*/
        private String primaryKey;
    }

    @Getter
    @Setter
    public static class BeanInfo{
        /**实体全限定名*/
        private String beanName;
        /**实体名*/
        private String beanSimpleName;
        /**实体包声明*/
        private String beanPackage;
    }
}