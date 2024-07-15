package builder.core.build.resolve.database;

import builder.model.resolve.database.jdbc.ConnectionInfo;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class MysqlDataBaseResolver extends DataBaseResolver{

    public MysqlDataBaseResolver(ConnectionInfo connectionInfo){
        super(connectionInfo);
    }
    @Override
    public ResultSet getColumnResultSet(String sql, Connection connection, String dataBaseName, String tableName) throws SQLException {
        PreparedStatement preStatementTable = connection.prepareStatement(sql);
        preStatementTable.setString(1, tableName);
        preStatementTable.setString(2,dataBaseName);
        return preStatementTable.executeQuery();
    }

}
