package builder.model.build.mvc;


import builder.model.build.mvc.service.Service;
import builder.model.build.orm.Entity;
import lombok.Data;
/**
 * controller信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class Controller {
    //控制器名
    private String name;
    //控制器描述
    private String description;
    //实体信息
    private Entity entity;
    //服务信息
    private Service serviceImpl;
    //包声明
    private String packages;
    //引用路径
    private String reference;
}
