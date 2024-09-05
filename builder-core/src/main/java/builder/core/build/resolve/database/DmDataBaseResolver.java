package builder.core.build.resolve.database;

import builder.model.resolve.database.jdbc.ConnectionInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 达梦数据库解析器
 *
 * @author fulin-peng
 * 2024-07-15  18:28
 */
public class DmDataBaseResolver extends DataBaseResolver{

    public DmDataBaseResolver(ConnectionInfo connectionInfo){
        super(connectionInfo);
    }
    @Override
    public ResultSet getColumnResultSet(String sql, Connection connection, String dataBaseName, String tableName) throws SQLException {
        PreparedStatement preStatementTable = connection.prepareStatement(sql);
        preStatementTable.setString(1, tableName);
        preStatementTable.setString(2, dataBaseName);
        preStatementTable.setString(3, tableName);
        preStatementTable.setString(4,dataBaseName);
        preStatementTable.setString(5, tableName);
        preStatementTable.setString(6,dataBaseName);
        preStatementTable.setString(7, tableName);
        preStatementTable.setString(8,dataBaseName);
        return preStatementTable.executeQuery();
    }

}
