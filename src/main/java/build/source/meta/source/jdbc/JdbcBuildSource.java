package build.source.meta.source.jdbc;

import build.source.meta.source.BuildSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * jdbc构建源
 *
 * @author peng_fu_lin
 * 2022-09-05 15:47
 */
@Getter
@Setter
public abstract class JdbcBuildSource implements BuildSource {
    /** 数据库名*/
    private String dataBaseName;
    /** 数据表名*/
    private List<String> tableNames;
}