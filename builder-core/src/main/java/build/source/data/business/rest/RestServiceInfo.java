package build.source.data.business.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Rest代码实体数据模型
 *
 * @author peng_fu_lin
 * 2023-01-12 17:22
 */
@Getter
@Setter
@AllArgsConstructor
public class RestServiceInfo {

    /**服务名*/
    private String serviceName;

    /**服务包*/
    private String servicePackage;

    /**服务描述*/
    private String description;

    /**依赖服务*/
    private RestServiceInfo dependentService;

}