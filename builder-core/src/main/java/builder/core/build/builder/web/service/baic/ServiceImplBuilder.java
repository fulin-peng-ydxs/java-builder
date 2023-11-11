package builder.core.build.builder.web.service.baic;


import builder.core.build.builder.base.Builder;
import builder.model.build.web.service.Service;

/**
 * 服务实现构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class ServiceImplBuilder extends Builder {

    public ServiceImplBuilder(){};

    public ServiceImplBuilder(String templatePath){
        super(templatePath);
    }

    /**
     * 构建服务实现
     * 2023/9/23 12:55
     * @author pengshuaifeng
     */
    public String buildImpl(Service serviceImpl){
        throw new RuntimeException("无具体的构建服务实现方法");
    }
}
