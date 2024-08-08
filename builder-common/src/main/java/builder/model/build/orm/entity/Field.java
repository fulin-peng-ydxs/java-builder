package builder.model.build.orm.entity;


import builder.model.resolve.database.ColumnInfo;
import lombok.Data;

/**
 * 字段信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class Field {
    //字段名
    private String name;
    //字段名（类名）
    private String classStyleName;
    //引用路径
    private String reference;
    //字段类型
    private Class<?> type;
    //列信息
    private ColumnInfo columnInfo;
}
