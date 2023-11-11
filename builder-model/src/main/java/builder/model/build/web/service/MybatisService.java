package builder.model.build.web.service;


import builder.model.build.orm.mybatis.Mapper;
import lombok.Getter;
import lombok.Setter;

/**
 * mybatis-plus_service信息
 * author: pengshuaifeng
 * 2023/9/23
 */
@Setter
@Getter
public class MybatisService extends Service{
    //mapper
    private Mapper mapper;
}
