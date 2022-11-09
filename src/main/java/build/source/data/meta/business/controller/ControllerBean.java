package build.source.data.meta.business.controller;

import build.source.data.meta.business.AbstractBusinessModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Controller实体数据模型
 *
 * @author peng_fu_lin
 * 2022-11-04 14:38
 */
@Getter
@Setter
@AllArgsConstructor
public class ControllerBean  extends AbstractBusinessModel {

    /**实体信息*/
    private BeanInfo beanInfo;
    /**元实体信息*/
    private BuildBeanInfo buildBeanInfo;
    /**服务信息*/
    private ServiceInfo serviceInfo;

    @Getter
    @Setter
    public static class ServiceInfo{
        /**服务名*/
        private String name;
        /**服务包声明*/
        private String packages;
    }
}