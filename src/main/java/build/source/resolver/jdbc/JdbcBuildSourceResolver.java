package build.source.resolver.jdbc;

import build.source.meta.BuildSource;
import build.source.meta.jdbc.JdbcBuildDataSource;
import build.source.meta.jdbc.JdbcBuildFileSource;
import build.source.meta.jdbc.JdbcBuildSource;
import build.source.resolver.meta.BuildSourceResolver;
import java.sql.*;
/**
 * Jdbc构建源解析器
 *
 * @author peng_fu_lin
 * 2022-09-05 14:47
 */
public abstract class JdbcBuildSourceResolver<T> extends BuildSourceResolver<T> {

    @Override
    public boolean isSupported(BuildSource builderDataSource,Class<?> resolverType) {
        return builderDataSource instanceof JdbcBuildSource;
    }

    protected Connection getResolverConnection(BuildSource builderDataSource)
            throws SQLException, ClassNotFoundException {
        if(builderDataSource instanceof JdbcBuildDataSource)
            return ((JdbcBuildDataSource)builderDataSource).getDataSource().getConnection();
        else if(builderDataSource instanceof JdbcBuildFileSource){
            JdbcBuildFileSource buildFileSource = (JdbcBuildFileSource) builderDataSource;
            Class.forName(buildFileSource.getDriverClass().getName());
            return DriverManager.getConnection(buildFileSource.getUrl(),buildFileSource.getUserName(), buildFileSource.getPassword());
        }
        return null;
    }
}