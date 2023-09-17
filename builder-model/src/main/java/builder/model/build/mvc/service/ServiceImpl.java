package builder.model.build.mvc.service;


import builder.model.build.orm.mybatis.Mapper;
import lombok.Data;

/**
 * services实现
 * author: pengshuaifeng
 * 2023/9/12
 */
@Data
public class ServiceImpl extends Service{
    //mapper信息
    private Mapper mapper;
}
