package builder.core.build.builder.web.service.mybatis;

import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
import builder.core.build.builder.mybatis.plus.MybatisPlusBuilderProcessor;
import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.model.build.config.content.MybatisContent;
import builder.model.build.orm.entity.Entity;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.build.web.service.MybatisService;
import builder.model.build.web.service.Service;
import builder.util.JsonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
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
    MybatisServiceBuilderProcessor(ServiceBuilderProcessor serviceBuilderProcessor, MybatisBuilderProcessor mybatisBuilderProcessor,
                                   MybatisPlusBuilderProcessor mybatisPlusBuilderProcessor){
        this.serviceBuilderProcessor = serviceBuilderProcessor;
        this.mybatisBuilderProcessor = mybatisBuilderProcessor;
        this.mybatisPlusBuilderProcessor = mybatisPlusBuilderProcessor;
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
        Collection<Service> serviceInterfaces = generateServiceInterfaces(serviceBuilderProcessor, mappers);
        Collection<Service> generateServiceImpls = generateServiceImpls(serviceBuilderProcessor, serviceInterfaces);
        serviceBuilderProcessor.build(serviceInterfaces,generateServiceImpls);
    }

    /**
     * 生成mybatis服务实现
     * 2023/9/23 17:39
     * @author pengshuaifeng
     */
    public static Collection<Service> generateServiceImpls(ServiceBuilderProcessor serviceBuilderProcessor, Collection<Service> serviceInterfaces){
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
    public static Collection<Service> generateServiceInterfaces(ServiceBuilderProcessor serviceBuilderProcessor,Collection<Mapper> mappers){
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
