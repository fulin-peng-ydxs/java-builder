package builder.model.build.web.service;


import builder.model.build.orm.entity.Entity;
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
    //服务描述
    private String description;
    //引用路径
    private String reference;
    //包声明
    private String packages;
    //服务接口引用
    private Service serviceInterface;
}
