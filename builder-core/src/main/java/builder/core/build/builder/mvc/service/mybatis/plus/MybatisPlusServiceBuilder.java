package builder.core.build.builder.mvc.service.mybatis.plus;

import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.core.build.builder.mvc.service.mybatis.MybatisServiceBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.basic.MybatisPlusServiceImplBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.basic.MybatisPlusServiceInterfaceBuilder;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilder;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.orm.mybatis.Mapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * mybatis-plus服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
@Setter
@Getter
@Builder
public class MybatisPlusServiceBuilder{

    protected ServiceBuilder serviceBuilder;

    protected MybatisPlusBuilder mybatisPlusBuilder;

    /**
     * 构建器创建
     * 2023/9/23 11:17
     * @author pengshuaifeng
     */
     MybatisPlusServiceBuilder(ServiceBuilder serviceBuilder, MybatisPlusBuilder mybatisPlusBuilder) {
        this.serviceBuilder = serviceBuilder;
        this.mybatisPlusBuilder = mybatisPlusBuilder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/23 11:00
     * @author pengshuaifeng
     */
    protected void init(){
        initBuilder();
    }

    public void initBuilder(){
        serviceBuilder.setServiceImplBuilder(new MybatisPlusServiceImplBuilder());
        serviceBuilder.setServiceInterfaceBuilder(new MybatisPlusServiceInterfaceBuilder());
    }

    /**
     * 构建
     * 2023/9/23 10:49
     * @author pengshuaifeng
     */
    public void build() {
        mybatisPlusBuilder.build(MybatisContent.ALL);
        List<Mapper> mappers = mybatisPlusBuilder.getMybatisBuilder().getMappers();
        serviceBuilder.setServiceInterfaces(MybatisServiceBuilder.generateServiceInterface(serviceBuilder,mappers));
        serviceBuilder.setServiceImpls(MybatisServiceBuilder.generateServiceImpls(serviceBuilder,serviceBuilder.getServiceInterfaces()));
        serviceBuilder.build();
    }
}
