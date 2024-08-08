package builder.model.resolve.database;


import lombok.Data;

/**
 * 列信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class ColumnInfo {
    //列名称
    private String name;
    //列类型
    private String type;
    //是否为空
    private boolean isNull;
    //是否主键
    private boolean isPrimaryKey;
    //列说明
    private String description;
    //列长度
    private Integer length;
}
