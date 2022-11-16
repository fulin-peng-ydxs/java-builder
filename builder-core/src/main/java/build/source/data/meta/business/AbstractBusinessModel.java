package build.source.data.meta.business;

import build.source.data.meta.BuildResolverModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 公共业务数据模型
 *
 * @author peng_fu_lin
 * 2022-11-04 15:13
 */
public class AbstractBusinessModel extends BuildResolverModel {

    @Setter
    @Getter
    public static class BuildBeanInfo{
        /**元名称*/
        private String name;
        /**主键信息*/
        private String primaryKey;
    }


    @Getter
    @Setter
    public static class BeanInfo{
        /**实体全限定名*/
        private String name;
        /**实体名*/
        private String simpleName;
        /**实体包声明*/
        private String packages;
        /**实体描述*/
        private String description;
    }
}