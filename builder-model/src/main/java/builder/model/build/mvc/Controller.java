package builder.model.build.mvc;


import builder.model.build.mvc.service.ServiceInterface;
import builder.model.build.orm.Entity;
import lombok.Data;

/**
 * controller信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class Controller {
    //实体信息
    private Entity entity;
    //服务信息
    private ServiceInterface serviceInterface;
    //引用路径
    private String path;
}
