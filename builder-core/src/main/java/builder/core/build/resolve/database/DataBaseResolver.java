package builder.core.build.resolve.database;

import builder.core.build.resolve.database.mapper.ColumnMapper;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.TableInfo;
import builder.model.resolve.database.jdbc.BaseInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.model.resolve.database.jdbc.SQL;
import builder.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据库解析器
 * author: pengshuaifeng
 * 2023/9/2
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataBaseResolver {

    protected ConnectionInfo connectionInfo;

    public List<TableInfo> getTableInfos() {
        return getTableInfos(this.connectionInfo);
    }

    /**
     * 获取表集合
     * 2023/9/3 13:30
     * @author pengshuaifeng
     */
    public List<TableInfo> getTableInfos(ConnectionInfo connectionInfo){
        try {
            BaseInfo baseInfo = connectionInfo.getBaseInfo();
            List<TableInfo> tableInfos = resultSetsConvertTables(baseInfo.getTableNames(), baseInfo.getDataBaseName(),
                    SQL.getSQL(connectionInfo.getUrl()), getConnection(connectionInfo));
            if(tableInfos.isEmpty())
                throw new RuntimeException("未获取到数据表："+connectionInfo);
            return tableInfos;
        } catch (Exception e) {
            throw new RuntimeException("获取数据表集合异常",e);
        }
    }

    /**
     * 数据库结果集转换数据表
     * 2023/9/3 12:55
     * @author pengshuaifeng
     */
    public List<TableInfo>  resultSetsConvertTables(List<String> tableNames, String databaseName, SQL sql, Connection connection)
            throws SQLException {
        String fieldSql=sql.fieldSql;
        String tableSQL=sql.tableSQL;
        PreparedStatement preStatementTables=null;
        PreparedStatement preStatementTable=null;
        LinkedList<TableInfo> tableInfos = new LinkedList<>();
        try{
            List<ResultSet> resultSets = new LinkedList<>();
            if(tableNames==null){ //全量查询
                preStatementTables= connection.prepareStatement(tableSQL);
                ResultSet resultTables= preStatementTables.executeQuery();
                preStatementTables.setString(1, databaseName);
                while (resultTables.next()) {
                    preStatementTable = connection.prepareStatement(fieldSql);
                    String table= resultTables.getString("tableName");
                    preStatementTable.setString(1, table);
                    preStatementTable.setString(2,databaseName);
                    resultSets.add(preStatementTable.executeQuery());
                }
            }else{ //按需查询
                for (String tableName : tableNames) {
                    preStatementTable = connection.prepareStatement(fieldSql);
                    preStatementTable.setString(1,tableName);
                    preStatementTable.setString(2,databaseName);
                    resultSets.add(preStatementTable.executeQuery());
                }
            }
            for (ResultSet resultSet : resultSets) {
                tableInfos.add(getTable(resultSet));
            }
            return tableInfos;
        }catch (Exception e) {
            throw  new RuntimeException("获取数据库结果集异常",e);
        }
        finally {
            if(preStatementTables !=null&&
                    !preStatementTables.isClosed()){
                preStatementTables.close();
            }
            if(preStatementTable !=null&&
                    !preStatementTable.isClosed()){
                preStatementTable.close();
            }
        }
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
            LinkedList<ColumnInfo> columnInfos = new LinkedList<>();
            while (resultSet.next()) {
                if(tableName==null){
                    tableName=resultSet.getString("tableName");
                }
                ColumnInfo columnInfo = getColumnInfo(resultSet);
                if(columnInfo.isPrimaryKey()){
                    tableInfo.setPrimaryColumnInfo(columnInfo);
                }
                columnInfos.add(columnInfo);
            }
            tableInfo.setColumnInfos(columnInfos);
            tableInfo.setName(tableName);
            //TODO 动态取值
            tableInfo.setDescription(tableName);
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
            int length= fieldLengthString == null ? 0 : Integer.parseInt(fieldLengthString);
            String comment = resultSet.getString("fieldComment");
            boolean isNull= ColumnMapper.mapperNullType(resultSet.getString("isNull"));
            boolean isPrimaryKey = ColumnMapper.mapperPrimaryKey(resultSet.getString("isPrimaryKey"));
            columnInfo.setLength(length);
            columnInfo.setDescription(StringUtil.isNotEmpty(comment)?comment:fieldName);
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
            Class.forName(connectionInfo.getClass().getName());
            return DriverManager.getConnection(connectionInfo.getUrl(),
                    connectionInfo.getUserName(), connectionInfo.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("获取数据库连接异常",e);
        }
    }

    private  Connection getConnection(DataSource dataSource){
        return null;
    }
}
