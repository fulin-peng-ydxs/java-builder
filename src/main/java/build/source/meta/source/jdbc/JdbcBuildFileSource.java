package build.source.meta.source.jdbc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Driver;
/**
 * jdbc文件构建源
 *
 * @author peng_fu_lin
 * 2022-09-05 15:19
 */
@Setter
@Getter
public class JdbcBuildFileSource extends JdbcBuildSource{
    /** 数据库连接地址*/
    private String url;
    /** 数据库连接驱动*/
    private Class<? extends Driver> DriverClass;
    /** 数据库用户*/
    private String userName;
    /** 数据库用户密码*/
    private String password;
}