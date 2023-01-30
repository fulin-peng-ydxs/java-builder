package build.source.data.business.rest.meta;

import build.source.data.business.meta.AbstractBusinessModel;
import build.source.data.business.rest.RestServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**Rest业务实体数据模型
 * @author peng_fu_lin
 * 2023-01-12 16:26
 */
@Getter
@Setter
@AllArgsConstructor
public class RestBusiness extends AbstractBusinessModel {

    /**元实体信息*/
    private BuildBeanInfo buildBeanInfo;

    /**实体信息*/
    private BeanInfo beanInfo;

    /**服务信息*/
    private RestServiceInfo restServiceInfo;
}