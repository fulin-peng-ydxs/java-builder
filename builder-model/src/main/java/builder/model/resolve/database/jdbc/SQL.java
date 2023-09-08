package builder.model.resolve.database.jdbc;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum SQL {

    MYSQL(
            "select column_name columnName,table_name tableName,data_type dataType,is_nullable isNull,column_key isPrimaryKey,column_comment fieldComment,character_maximum_length fieldLength " +
            "from information_schema.columns  " + "where table_name = ? and table_schema= ? order by ordinal_position",

            "select table_name tableName  from information_schema.`TABLES` where table_schema= ?"
    );

    public final String fieldSql;

    public final String tableSQL;


    /**
     * 获取可支持的SQL对象
     * 2023/9/3 13:43
     * @author pengshuaifeng
     * @param url jdbc连接url
     */
    public static SQL getSQL(String url){
        if(url.startsWith("jdbc:mysql")){
            return MYSQL;
        }
        return null;
    }
}
