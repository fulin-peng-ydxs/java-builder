package builder.model.build.mvc.service;


import builder.model.build.orm.Entity;
import lombok.Data;

/**
 * service信息
 * author: pengshuaifeng
 * 2023/9/12
 */
@Data
public class Service {
    //服务名
    private String name;
    //实体信息
    private Entity entity;
    //service描述
    private String description;
    //引用路径
    private String reference;
}
