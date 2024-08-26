package builder.core.build.resolve.database;

import builder.core.build.resolve.database.mapper.ColumnMapper;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.model.resolve.database.jdbc.DataBaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.model.resolve.database.jdbc.SQL;
import builder.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据库解析器
 * author: pengshuaifeng
 * 2023/9/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DataBaseResolver {

    //数据库连接信息
    protected ConnectionInfo connectionInfo;

    /**
     * 获取表集合
     * 2023/9/3 13:30
     * @author pengshuaifeng
     */
    public List<TableInfo> getTableInfos(ConnectionInfo connectionInfo){
        try {
            DataBaseInfo dataBaseInfo = connectionInfo.getDataBaseInfo();
            List<TableInfo> tableInfos = resultSetsConvertTables(dataBaseInfo.getTableNames(), dataBaseInfo.getDataBaseName(),
                    SQL.getSQL(connectionInfo.getUrl()), getConnection(connectionInfo));
            if(tableInfos.isEmpty())
                throw new RuntimeException("未获取到数据表："+connectionInfo);
            return tableInfos;
        } catch (Exception e) {
            throw new RuntimeException("获取数据表集合异常",e);
        }
    }

    public List<TableInfo> getTableInfos() {
        return getTableInfos(this.connectionInfo);
    }

    /**
     * 数据库结果集转换数据表
     * 2023/9/3 12:55
     * @author pengshuaifeng
     */
    public List<TableInfo>  resultSetsConvertTables(Collection<String> tableNames, String databaseName, SQL sql, Connection connection) {
        String fieldSql=sql.fieldSql;
        String tableSQL=sql.tableSQL;
        LinkedList<TableInfo> tableInfos = new LinkedList<>();
        try{
            List<ResultSet> resultSets = new LinkedList<>();
            if(tableNames==null){ //全量查询
                ResultSet resultTables= getTableResultSet(tableSQL,connection,databaseName);
                while (resultTables.next()) {
                    String tableName= resultTables.getString("tableName");
                    resultSets.add(getColumnResultSet(fieldSql,connection,databaseName,tableName));
                }
            }else{ //按需查询
                for (String tableName : tableNames) {
                    resultSets.add(getColumnResultSet(fieldSql,connection,databaseName,tableName));
                }
            }
            for (ResultSet resultSet : resultSets) {
                tableInfos.add(getTable(resultSet));
            }
            return tableInfos;
        }catch (Exception e) {
            throw  new RuntimeException("获取数据库结果集异常",e);
        }
    }

    /**
     * 解释列的sql
     * 2024/7/15 0015 18:10
     * @author fulin-peng
     */
    public abstract ResultSet getColumnResultSet(String sql,Connection connection,String dataBaseName,String tableName) throws SQLException;

    /**
     * 解释表的sql
     * 2024/7/15 0015 18:10
     * @author fulin-peng
     */
    public  ResultSet getTableResultSet(String sql,Connection connection,String dataBaseName) throws SQLException{
        PreparedStatement preStatementTables= connection.prepareStatement(sql);
        preStatementTables.setString(1, dataBaseName);
        return preStatementTables.executeQuery();
    }


    /**
     * 解析table
     * 2023/9/3 12:07
     * @author pengshuaifeng
     */
    protected TableInfo getTable(ResultSet resultSet){
        try {
            TableInfo tableInfo = new TableInfo();
            String tableName=null;
            String tableDescription=null;
            LinkedList<ColumnInfo> columnInfos = new LinkedList<>();
            while (resultSet.next()) {
                if(tableName==null){
                    tableName=resultSet.getString("tableName");
                }
                if(tableDescription==null){
                    tableDescription=resultSet.getString("tableComment");
                }
                ColumnInfo columnInfo = getColumnInfo(resultSet);
                if(columnInfo.isPrimaryKey()){
                    tableInfo.setPrimaryColumnInfo(columnInfo);
                }
                columnInfos.add(columnInfo);
            }
            tableInfo.setColumnInfos(columnInfos);
            tableInfo.setName(tableName);
            tableInfo.setDescription(tableDescription==null?tableName:tableDescription);
            return tableInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取数据表异常",e);
        }
    }


    /**
     * 解析column
     * 2023/9/3 12:07
     * @author pengshuaifeng
     */
    protected ColumnInfo getColumnInfo(ResultSet resultSet){
        try {
            ColumnInfo columnInfo = new ColumnInfo();
            String fieldName = resultSet.getString("columnName");
            columnInfo.setName(fieldName);
            columnInfo.setType(resultSet.getString("dataType"));
            String fieldLengthString = resultSet.getString("fieldLength");
            int length= fieldLengthString == null ? 0 :
                    fieldLengthString.length()>=4?0:Integer.parseInt(fieldLengthString);
            String comment = resultSet.getString("fieldComment");
            boolean isNull= ColumnMapper.mapperNullType(resultSet.getString("isNull"));
            boolean isPrimaryKey = ColumnMapper.mapperPrimaryKey(resultSet.getString("isPrimaryKey")) ||
            columnInfo.getName().equalsIgnoreCase("id") || columnInfo.getName().equalsIgnoreCase("sid");
            columnInfo.setLength(length);
            columnInfo.setDescription(StringUtils.isNotEmpty(comment)?comment:fieldName);
            columnInfo.setNull(isNull);
            columnInfo.setPrimaryKey(isPrimaryKey);
            return columnInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取数据表列信息异常",e);
        }
    }

    /**
     * 获取数据库连接
     * 2023/9/2 13:32
     * @author pengshuaifeng
     * @param connectionInfo 连接信息
     */
    private  Connection getConnection(ConnectionInfo connectionInfo){
        try {
            Class.forName(connectionInfo.getDriverClass().getName());
            return DriverManager.getConnection(connectionInfo.getUrl(),
                    connectionInfo.getUserName(), connectionInfo.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("获取数据库连接异常",e);
        }
    }
    
    
    /**
     * 获取适配的数据库解析器
     * 2024/7/15 0015 18:39 
     * @author fulin-peng
     */
    //TODO 待优化成缓存
    public static DataBaseResolver getDataBaseResolver(ConnectionInfo connectionInfo){
        SQL sql = SQL.getSQL(connectionInfo.getUrl());
        if (sql== SQL.MYSQL) {
            return new MysqlDataBaseResolver(connectionInfo);
        }else if(sql==SQL.DM){
            return new DmDataBaseResolver(connectionInfo);
        }
        throw new RuntimeException("没有获取适配的数据库解析器："+sql);
    }
}
