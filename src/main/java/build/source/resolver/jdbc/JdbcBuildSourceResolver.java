package build.source.resolver.jdbc;

import build.source.data.BuildMeta;
import build.source.data.BuildMetaItem;
import build.source.data.enums.NullType;
import build.source.data.enums.PrimaryKey;
import build.source.resolver.exception.BuildSourceResolverException;
import build.source.resolver.exception.JdbcBuildSourceResolverException;
import build.source.meta.BuildSource;
import build.source.resolver.BuildSourceResolver;
import build.source.meta.jdbc.JdbcBuildDataSource;
import build.source.meta.jdbc.JdbcBuildFileSource;
import build.source.meta.jdbc.JdbcBuildSource;
import com.mysql.jdbc.MySQLConnection;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Jdbc构建源解析器
 *
 * @author peng_fu_lin
 * 2022-09-05 14:47
 */
public class JdbcBuildSourceResolver extends BuildSourceResolver{

    @Override
    public boolean isSupported(BuildSource builderDataSource) {
        return builderDataSource instanceof JdbcBuildSource;
    }

    @Override
    public final Map<String,BuildMeta> resolver(BuildSource builderSource) throws BuildSourceResolverException {
        try {
            //获取连接
            Connection resolverConnection = getResolverConnection(builderSource);
            //获取SQL
            JdbcResolverSQL resolverSQL = getResolverSQL(resolverConnection);
            //获取元数据
            JdbcBuildSource jdbcBuildSource = (JdbcBuildSource) builderSource;
            return getBuildMetas(jdbcBuildSource.getTableNames(),jdbcBuildSource.getDataBaseName(), resolverSQL,resolverConnection);
        } catch (Exception e) {
            throw new JdbcBuildSourceResolverException("JDBC resolution builds source exceptions",e);
        }
    }

    protected Connection getResolverConnection(BuildSource builderDataSource) throws SQLException, ClassNotFoundException {
        if(builderDataSource instanceof JdbcBuildDataSource)
            return ((JdbcBuildDataSource)builderDataSource).getDataSource().getConnection();
        else if(builderDataSource instanceof JdbcBuildFileSource){
            JdbcBuildFileSource buildFileSource = (JdbcBuildFileSource) builderDataSource;
            Class.forName(buildFileSource.getDriverClass().getName());
            return DriverManager.getConnection(buildFileSource.getUrl(),buildFileSource.getUserName(), buildFileSource.getPassword());
        }
        return null;
    }

    protected JdbcResolverSQL getResolverSQL(Connection connection){
        if(connection instanceof MySQLConnection)
            return JdbcResolverSQL.MYSQL;
        return null;
    }

    protected  Map<String,BuildMeta> getBuildMetas
            (List<String> tableNames,String databaseName,JdbcResolverSQL resolverSQL,Connection resolverConnection) throws SQLException {
        Map<String, BuildMeta> buildMetas = new HashMap<>();
        if(tableNames==null||tableNames.isEmpty()){
            List<ResultSet> resultSets = getResultSets(null,databaseName ,resolverSQL, resolverConnection);
            for (ResultSet resultSet : resultSets) {
                BuildMeta buildMeta = getBuildMeta(resultSet);
                buildMetas.put(buildMeta.getName(),buildMeta);
            }
        }
        else{
            for (String tableName : tableNames) {
                List<ResultSet> resultSets = getResultSets(tableName,databaseName,resolverSQL, resolverConnection);
                BuildMeta buildMeta = getBuildMeta(resultSets.get(0));
                buildMetas.put(buildMeta.getName(),buildMeta);
            }
        }
        return buildMetas;
    }


    @AllArgsConstructor
    protected enum JdbcResolverSQL{
        MYSQL( "select column_name columnName,table_name tableName,data_type dataType,is_nullable isNull,column_key isPrimaryKey,column_comment fieldComment,character_maximum_length fieldLength " +
                "from information_schema.columns  " + "where table_name = ? and table_schema= ?",

                "select table_name tableName  from information_schema.`TABLES` where table_schema= ?");

        public String fieldSql;
        public String tableSQL;
    }


    private BuildMeta getBuildMeta(ResultSet resultSet) throws SQLException {
        List<BuildMetaItem> buildMetaItems = new LinkedList<>();
        boolean continues=true;
        BuildMeta buildMeta = new BuildMeta();
        while (resultSet.next()) {
            if(continues)
                buildMeta.setName(resultSet.getString("tableName"));
            //封装BuildMetaItem
            BuildMetaItem buildMetaItem = new BuildMetaItem();
            String fieldName = resultSet.getString("columnName");
            buildMetaItem.setFieldName(fieldName);
            buildMetaItem.setFieldType(resultSet.getString("dataType"));
            //封装ItemConfig
            String fieldLengthString = resultSet.getString("fieldLength");
            int  fieldLength= fieldLengthString == null ? 0 : Integer.parseInt(fieldLengthString);
            String fieldComment = resultSet.getString("fieldComment");
            NullType nullType = mapperNullType(resultSet.getString("isNull"));
            PrimaryKey primaryKey = mapperPrimaryKey(resultSet.getString("isPrimaryKey"));
            BuildMetaItem.ItemConfig itemConfig = new BuildMetaItem.ItemConfig(
                    fieldLength,fieldComment,nullType, primaryKey);
            buildMetaItem.setItemConfig(itemConfig);
            buildMetaItems.add(buildMetaItem);
            continues=false;
        }
        buildMeta.setDataItems(buildMetaItems);
        return buildMeta;
    }

    private  List<ResultSet> getResultSets(String tableName,String databaseName,JdbcResolverSQL resolverSQL,Connection resolverConnection)
            throws SQLException {
        String fieldSql=resolverSQL.fieldSql;
        String tableSQL=resolverSQL.tableSQL;
        PreparedStatement preStatementTables=null;
        PreparedStatement preStatementTable=null;
        try {
            List<ResultSet> resultSets = new LinkedList<>();
            if(tableName==null){ //全量查询
                preStatementTables= resolverConnection.prepareStatement(tableSQL);
                ResultSet resultTables= preStatementTables.executeQuery();
                preStatementTables.setString(1, databaseName);
                while (resultTables.next()) {
                    preStatementTable = resolverConnection.prepareStatement(fieldSql);
                    String table= resultTables.getString("tableName");
                    preStatementTable.setString(1, table);
                    preStatementTable.setString(2,databaseName);
                    resultSets.add(preStatementTable.executeQuery());
                }
            }else{ //单表查询
                preStatementTable = resolverConnection.prepareStatement(fieldSql);
                preStatementTable.setString(1,tableName);
                preStatementTable.setString(2,databaseName);
                resultSets.add(preStatementTable.executeQuery());
            }
            return resultSets;
        }catch (Exception e) {
            if(preStatementTables !=null&&
                    !preStatementTables.isClosed()){
                preStatementTables.close();
            }
            if(preStatementTable !=null&&
                    !preStatementTable.isClosed()){
                preStatementTable.close();
            }
            throw e;
        }
    }
}