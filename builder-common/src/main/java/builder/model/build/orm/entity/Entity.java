package builder.model.build.orm.entity;


import builder.model.resolve.database.TableInfo;
import lombok.Data;
import java.util.List;

/**
 * 实体信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class Entity {
    //实体名
    private String name;
    //字段集
    private List<Field> fields;
    //主键字段
    private Field primaryField;
    //引用路径
    private String reference;
    //包声明
    private String packages;
    //实体描述
    private String description;
    //实体表对象
    private TableInfo tableInfo;
}
