package builder.model.resolve.database.jdbc;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * jdbc解析对象
 * author: pengshuaifeng
 * 2023/9/3
 */
@Data
@AllArgsConstructor
public class DataBaseInfo {
    /** 数据库名*/
    private String dataBaseName;
    /** 数据表名*/
    private List<String> tableNames;
}
