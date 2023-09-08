package builder.model.build.orm.mybatis;


import builder.model.build.orm.Entity;
import lombok.Data;

/**
 * mapper信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class Mapper {
    //映射器名
    private String name;
    //实体信息
    private Entity entity;
    //引用路径
    private String reference;
    //mapper描述
    private String description;
}
