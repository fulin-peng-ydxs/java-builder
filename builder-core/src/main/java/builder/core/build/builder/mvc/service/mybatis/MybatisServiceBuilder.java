package builder.core.build.builder.mvc.service.mybatis;

import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.core.build.builder.mvc.service.mybatis.basic.MybatisServiceImplBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.MybatisPlusServiceImplBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.MybatisPlusServiceInterfaceBuilder;
import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilder;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.mvc.service.MybatisService;
import builder.model.build.mvc.service.Service;
import builder.model.build.orm.Entity;
import builder.model.build.orm.mybatis.Mapper;
import builder.util.JsonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**mybatis服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
@Setter
@Getter
@Builder
public class MybatisServiceBuilder{

    private ServiceBuilder serviceBuilder;

    private MybatisBuilder mybatisBuilder;
    private MybatisPlusBuilder mybatisPlusBuilder;

    /**
     * 构建器创建
     * 2023/9/23 17:54
     * @author pengshuaifeng
     */
    MybatisServiceBuilder(ServiceBuilder serviceBuilder,MybatisBuilder mybatisBuilder, MybatisPlusBuilder mybatisPlusBuilder){
        this.serviceBuilder=serviceBuilder;
        this.mybatisBuilder=mybatisBuilder;
        this.mybatisPlusBuilder = mybatisPlusBuilder;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/23 11:00
     * @author pengshuaifeng
     */
    private void init(){
        initBuilder();
    }

    public void initBuilder(){
        if(mybatisPlusBuilder!=null){
            initPlusBuilder();
        }else{
            serviceBuilder.setServiceImplBuilder(new MybatisServiceImplBuilder());
        }
    }

    private void initPlusBuilder(){
        serviceBuilder.setServiceImplBuilder(new MybatisPlusServiceImplBuilder());
        serviceBuilder.setServiceInterfaceBuilder(new MybatisPlusServiceInterfaceBuilder());
    }

    /**
     * 构建
     * 2023/9/24 16:23
     * @author pengshuaifeng
     */
    public void build() {
        List<Mapper> mappers;
        if(mybatisPlusBuilder!=null){
            mybatisPlusBuilder.build(MybatisContent.ALL);
            mappers=mybatisPlusBuilder.getMybatisBuilder().getMappers();
        }else{
            mybatisBuilder.build(MybatisContent.ALL);
            mappers = mybatisBuilder.getMappers();
        }
        serviceBuilder.setServiceInterfaces(generateServiceInterface(serviceBuilder,mappers));
        serviceBuilder.setServiceImpls(generateServiceImpls(serviceBuilder,serviceBuilder.getServiceInterfaces()));
        serviceBuilder.build();
    }

    /**
     * 生成mybatis服务实现
     * 2023/9/23 17:39
     * @author pengshuaifeng
     */
    public static List<Service> generateServiceImpls(ServiceBuilder serviceBuilder,List<Service> serviceInterfaces){
        List<Service> result=new LinkedList<>();
        for (Service serviceInterface : serviceInterfaces) {
            MybatisService interfaceService = (MybatisService) serviceInterface;
            Entity entity = serviceInterface.getEntity();
            MybatisService serviceImpl = JsonUtils.getObject(serviceBuilder.generateServiceImpl(entity,serviceInterface), MybatisService.class);
            serviceImpl.setMapper(interfaceService.getMapper());
            result.add(serviceImpl);
        }
        return result;
    }

    /**
     * 生成mybatis服务接口
     * 2023/9/23 17:39
     * @author pengshuaifeng
     */
    public static List<Service> generateServiceInterface(ServiceBuilder serviceBuilder, List<Mapper> mappers){
        List<Service> result=new LinkedList<>();
        for (Mapper mapper : mappers) {
            Entity entity = mapper.getEntity();
            MybatisService serviceImpl = JsonUtils.getObject(serviceBuilder.generateServiceInterface(entity), MybatisService.class);
            serviceImpl.setMapper(mapper);
            result.add(serviceImpl);
        }
        return result;
    }

}
