package builder.model.resolve.database;


import lombok.Data;

import java.util.List;

/**
 * 表信息
 * author: pengshuaifeng
 * 2023/9/1
 */
@Data
public class TableInfo {
    //表名称
    private String name;
    //字段集合
    private List<ColumnInfo> columnInfos;
    //主键字段
    private ColumnInfo primaryColumnInfo;
    //表描述
    private String description;
}
