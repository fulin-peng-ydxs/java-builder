package build.builder.meta.codes.xml.bean.mybatis;

import build.builder.data.xmls.meta.XmlElement;
import build.source.data.meta.bean.mybatis.MybatisBuildBean;
import java.util.List;
/**
 * 简单的Mybatis实体构建器
 *
 * @author peng_fu_lin
 * 2022-10-09 17:36
 */
public class SimpleMybatisBuilder extends MybatisBuilder {

    @Override
    protected List<XmlElement> getSqlElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }

    /**
     * 生成列元素
     * 2022/10/10 0010-16:43
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlColumnElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }

    /**
     * 生成条件元素
     * 2022/10/11 0011-14:36
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlConditionElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }

    /**
     * 生成更新元素
     * 2022/10/11 0011-14:43
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlUpdateElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }

    /**
     * 生成结果集元素
     * 2022/10/11 0011-14:39
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlResultElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }

    @Override
    protected List<XmlElement> getBasicSqlElements(MybatisBuildBean mybatisBuildBean) {
        return null;
    }
}
