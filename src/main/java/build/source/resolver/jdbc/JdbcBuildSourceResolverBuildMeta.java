package build.source.resolver.jdbc;

import build.source.data.enums.NullType;
import build.source.data.enums.PrimaryKey;
import build.source.data.meta.bean.BuildBean;
import build.source.data.meta.bean.BuildBeanItem;
import build.source.meta.BuildSource;
import build.source.meta.jdbc.JdbcBuildSource;
import build.source.resolver.exception.BuildSourceResolverException;
import build.source.resolver.exception.JdbcBuildSourceResolverException;
import build.source.resolver.mapper.BuildMetaMapper;
import com.mysql.jdbc.MySQLConnection;
import lombok.AllArgsConstructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
/**
 * Jdbc解析器-BuildMeta
 *
 * @author peng_fu_lin
 * 2022-09-07 14:52
 */
public class JdbcBuildSourceResolverBuildMeta extends JdbcBuildSourceResolver<List<BuildBean>>
      implements BuildMetaMapper {

    @Override
    public boolean isSupported(BuildSource builderDataSource, Class<?> resolverType) {
        return super.isSupported(builderDataSource, resolverType) && resolverType== BuildBean.class;
    }

    @Override
    public final List<BuildBean> resolver(BuildSource builderSource) throws BuildSourceResolverException {
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

    protected JdbcResolverSQL getResolverSQL(Connection connection){
        if(connection instanceof MySQLConnection)
            return JdbcResolverSQL.MYSQL;
        return null;
    }


    protected  List<BuildBean> getBuildMetas
            (List<String> tableNames,String databaseName,JdbcResolverSQL resolverSQL,Connection resolverConnection) throws SQLException {
        List<BuildBean> buildBeans =new LinkedList<>();
        if(tableNames==null||tableNames.isEmpty()){
            List<ResultSet> resultSets = getResultSets(null,databaseName ,resolverSQL, resolverConnection);
            for (ResultSet resultSet : resultSets) {
                BuildBean buildBean = getBuildMeta(resultSet);
                buildBeans.add(buildBean);
            }
        }
        else{
            for (String tableName : tableNames) {
                List<ResultSet> resultSets = getResultSets(tableName,databaseName,resolverSQL, resolverConnection);
                BuildBean buildBean = getBuildMeta(resultSets.get(0));
                buildBeans.add(buildBean);
            }
        }
        return buildBeans;
    }


    @AllArgsConstructor
    protected enum JdbcResolverSQL{
        MYSQL( "select column_name columnName,table_name tableName,data_type dataType,is_nullable isNull,column_key isPrimaryKey,column_comment fieldComment,character_maximum_length fieldLength " +
                "from information_schema.columns  " + "where table_name = ? and table_schema= ?",

                "select table_name tableName  from information_schema.`TABLES` where table_schema= ?");

        public String fieldSql;
        public String tableSQL;
    }


    private BuildBean getBuildMeta(ResultSet resultSet) throws SQLException {
        List<BuildBeanItem> buildBeanItems = new LinkedList<>();
        boolean continues=true;
        BuildBean buildBean = new BuildBean();
        while (resultSet.next()) {
            if(continues)
                buildBean.setName(resultSet.getString("tableName"));
            //封装BuildMetaItem
            BuildBeanItem buildBeanItem = new BuildBeanItem();
            String fieldName = resultSet.getString("columnName");
            buildBeanItem.setFieldName(fieldName);
            buildBeanItem.setFieldType(resultSet.getString("dataType"));
            //封装ItemConfig
            String fieldLengthString = resultSet.getString("fieldLength");
            int  fieldLength= fieldLengthString == null ? 0 : Integer.parseInt(fieldLengthString);
            String fieldComment = resultSet.getString("fieldComment");
            NullType nullType = mapperNullType(resultSet.getString("isNull"));
            PrimaryKey primaryKey = mapperPrimaryKey(resultSet.getString("isPrimaryKey"));
            BuildBeanItem.ItemConfig itemConfig = new BuildBeanItem.ItemConfig(
                    fieldLength,fieldComment,nullType, primaryKey);
            buildBeanItem.setItemConfig(itemConfig);
            buildBeanItems.add(buildBeanItem);
            continues=false;
        }
        buildBean.setDataItems(buildBeanItems);
        return buildBean;
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