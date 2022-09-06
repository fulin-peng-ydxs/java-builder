package build.source.meta.jdbc;

import lombok.Getter;
import lombok.Setter;
import javax.sql.DataSource;
/**
 * jdbc数据源构建
 *
 * @author peng_fu_lin
 * 2022-09-05 15:45
 */
@Setter
@Getter
public class JdbcBuildDataSource extends JdbcBuildSource {
    /** 数据库连接源*/
    private DataSource dataSource;
}