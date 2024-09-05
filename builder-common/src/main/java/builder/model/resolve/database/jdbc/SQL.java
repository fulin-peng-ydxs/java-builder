package builder.model.resolve.database.jdbc;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum SQL {

    MYSQL(
            "select t.table_name tableName,t.table_comment tableComment,column_name columnName,data_type dataType,is_nullable isNull,column_key isPrimaryKey,column_comment fieldComment,character_maximum_length fieldLength \n" +
                    "from information_schema.tables t\n" +
                    "left join information_schema.columns c on c.TABLE_NAME=t.TABLE_NAME \n" +
                    "where t.table_name = ? and t.table_schema= ? \n" +
                    "order by ordinal_position",

            "select table_name tableName  from information_schema.`TABLES` where table_schema= ?"
    ),

    DM("SELECT \n" +
            "     col.column_name columnName,col.table_name tableName ,col.data_type dataType, col.data_length fieldLength,\n" +
            "      col.nullable isNull,\n" +
            "    (CASE WHEN pk.column_name IS NOT NULL THEN 'YES' ELSE 'NO' END) AS isPrimaryKey,\n" +
            "    comm.comments AS fieldComment,\n" +
            "    tab_comm.comments AS tableComment\n" +
            "FROM \n" +
            "    dba_tab_columns col\n" +
            "LEFT JOIN \n" +
            "    (SELECT cols.column_name\n" +
            "     FROM dba_constraints cons, dba_cons_columns cols\n" +
            "     WHERE cons.constraint_type = 'P'\n" +
            "       AND cons.constraint_name = cols.constraint_name\n" +
            "       AND cons.table_name = cols.table_name\n" +
            "       AND cons.table_name = ? AND cols.owner=?) pk\n" +
            "ON \n" +
            "    col.column_name = pk.column_name\n" +
            "LEFT JOIN \n" +
            "    (SELECT comm.column_name, comm.comments\n" +
            "     FROM dba_col_comments comm\n" +
            "     WHERE comm.table_name = ? AND comm.owner=?) comm\n" +
            "ON \n" +
            "    col.column_name = comm.column_name\n" +
            "LEFT JOIN \n" +
            "    (SELECT table_name, comments\n" +
            "     FROM dba_tab_comments\n" +
            "     WHERE table_name = ? AND owner = ?) tab_comm\n" +
            "ON \n" +
            "    col.table_name = tab_comm.table_name\n"+
            "WHERE \n" +
            "    col.table_name = ? and  col.owner=?\n" +
            "ORDER BY \n" +
            "    col.column_id",

    "SELECT owner, tableName\n" +
            "FROM dba_tables\n" +
            "where owner=?"
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
        }else if(url.startsWith("jdbc:dm")){
            return DM;
        }
        throw new RuntimeException("没有可支持的SQL对象："+url);
    }
}
