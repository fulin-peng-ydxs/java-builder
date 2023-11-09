package builder.core.build.builder.mvc.service.mybatis;

import builder.core.build.builder.mvc.service.ServiceBuilderProcessor;
import builder.core.build.builder.mvc.service.mybatis.basic.MybatisServiceImplBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.MybatisPlusServiceImplBuilder;
import builder.core.build.builder.mvc.service.mybatis.plus.MybatisPlusServiceInterfaceBuilder;
import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
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
public class MybatisServiceBuilderProcessor {

    private ServiceBuilderProcessor serviceBuilderProcessor;

    private MybatisBuilderProcessor mybatisBuilderProcessor;
    private MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor;

    /**
     * 构建器创建
     * 2023/9/23 17:54
     * @author pengshuaifeng
     */
    MybatisServiceBuilderProcessor(ServiceBuilderProcessor serviceBuilderProcessor, MybatisBuilderProcessor mybatisBuilderProcessor, MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor){
        this.serviceBuilderProcessor = serviceBuilderProcessor;
        this.mybatisBuilderProcessor = mybatisBuilderProcessor;
        this.mybatisPlusBuilderProcessor = mybatisPlusBuilderProcessor;
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
        if(mybatisPlusBuilderProcessor !=null){
            initPlusBuilder();
        }else{
            serviceBuilderProcessor.setServiceImplBuilder(new MybatisServiceImplBuilder());
        }
    }

    private void initPlusBuilder(){
        serviceBuilderProcessor.setServiceImplBuilder(new MybatisPlusServiceImplBuilder());
        serviceBuilderProcessor.setServiceInterfaceBuilder(new MybatisPlusServiceInterfaceBuilder());
    }

    /**
     * 构建
     * 2023/9/24 16:23
     * @author pengshuaifeng
     */
    public void build() {
        List<Mapper> mappers;
        if(mybatisPlusBuilderProcessor !=null){
            mybatisPlusBuilderProcessor.build(MybatisContent.ALL);
            mappers= mybatisPlusBuilderProcessor.getMybatisBuilderProcessor().getMappers();
        }else{
            mybatisBuilderProcessor.build(MybatisContent.ALL);
            mappers = mybatisBuilderProcessor.getMappers();
        }
        serviceBuilderProcessor.setServiceInterfaces(generateServiceInterface(serviceBuilderProcessor,mappers));
        serviceBuilderProcessor.setServiceImpls(generateServiceImpls(serviceBuilderProcessor, serviceBuilderProcessor.getServiceInterfaces()));
        serviceBuilderProcessor.build();
    }

    /**
     * 生成mybatis服务实现
     * 2023/9/23 17:39
     * @author pengshuaifeng
     */
    public static List<Service> generateServiceImpls(ServiceBuilderProcessor serviceBuilderProcessor, List<Service> serviceInterfaces){
        List<Service> result=new LinkedList<>();
        for (Service serviceInterface : serviceInterfaces) {
            MybatisService interfaceService = (MybatisService) serviceInterface;
            Entity entity = serviceInterface.getEntity();
            MybatisService serviceImpl = JsonUtils.getObject(serviceBuilderProcessor.generateServiceImpl(entity,serviceInterface), MybatisService.class);
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
    public static List<Service> generateServiceInterface(ServiceBuilderProcessor serviceBuilderProcessor, List<Mapper> mappers){
        List<Service> result=new LinkedList<>();
        for (Mapper mapper : mappers) {
            Entity entity = mapper.getEntity();
            MybatisService serviceImpl = JsonUtils.getObject(serviceBuilderProcessor.generateServiceInterface(entity), MybatisService.class);
            serviceImpl.setMapper(mapper);
            result.add(serviceImpl);
        }
        return result;
    }

}
