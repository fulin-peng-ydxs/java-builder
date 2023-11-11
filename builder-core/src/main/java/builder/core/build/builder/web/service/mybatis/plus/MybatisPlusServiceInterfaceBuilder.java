package builder.core.build.builder.web.service.mybatis.plus;


import builder.core.build.builder.web.service.baic.ServiceInterfaceBuilder;

/**
 * mybatis-plus服务接口构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class MybatisPlusServiceInterfaceBuilder extends ServiceInterfaceBuilder {

    public MybatisPlusServiceInterfaceBuilder(){
        this("/template/web/service/mybatis/plus/MybatisPlusServiceInterface.txt");
    }
    public MybatisPlusServiceInterfaceBuilder(String templatePath){
        super(templatePath);
    }
}
