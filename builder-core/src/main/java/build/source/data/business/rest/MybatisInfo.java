package build.source.data.business.rest;

import build.source.data.business.meta.AbstractBusinessModel;
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
public  class MybatisInfo extends AbstractBusinessModel {
    /**实体信息*/
    private BeanInfo beanInfo;
    /**元实体信息*/
    private BuildBeanInfo buildBeanInfo;
    /**实体字段映射*/
    private Map<String,String> entityFields;
}