package build.source.data.meta.bean.mybatis;

import build.builder.data.classes.model.ClassModel;
import build.source.data.meta.BuildResolverModel;
import build.source.data.meta.bean.BuildBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    private ClassModel beanModel;

    private BuildBean beanSource;
}