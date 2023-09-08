package builder.model.resolve.database.jdbc;


import lombok.Data;

import java.sql.Driver;

/**
 * jdbc连接对象
 * author: pengshuaifeng
 * 2023/9/2
 */
@Data
public class ConnectionInfo {
    /** 数据库连接地址*/
    private String url;
    /** 数据库连接驱动*/
    private Class<? extends Driver> DriverClass;
    /** 数据库用户*/
    private String userName;
    /** 数据库用户密码*/
    private String password;
    //操作目标信息
    private BaseInfo baseInfo;
}