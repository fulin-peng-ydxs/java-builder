package builder.model.build.mvc;


import builder.model.build.orm.Entity;
import builder.model.build.orm.mybatis.Mapper;
import lombok.Data;

/**
 * service信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class ServiceInfo {
    //实体信息
    private Entity entity;
    //mapper信息
    private Mapper mapper;
    //引用路径
    private String path;
}
