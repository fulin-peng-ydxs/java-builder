package builder.core.build.builder.web.service.mybatis.plus;


import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.mybatis.basic.MybatisServiceImplBuilder;
import builder.model.build.web.service.MybatisService;
import builder.model.build.web.service.Service;
import builder.util.TemplateUtils;

import java.util.Map;
/**
 * mybatis-plus服务实现构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class MybatisPlusServiceImplBuilder extends MybatisServiceImplBuilder {

    public static final MybatisPlusServiceImplBuilder INSTANCE = new MybatisPlusServiceImplBuilder();

    public MybatisPlusServiceImplBuilder(){
        this("/template/web/service/mybatis/plus/MybatisPlusServiceImpl.txt");
    }

    public MybatisPlusServiceImplBuilder(String templatePath){
        super(templatePath);
    }

    public MybatisPlusServiceImplBuilder(String templatePath,String templateClonePath){
        super(templatePath,templateClonePath);
    }

    @Override
    public String buildImpl(Service serviceImpl) {
        MybatisService mybatisService = (MybatisService) serviceImpl;
        Service serviceInterface = serviceImpl.getServiceInterface();
        //基础模版填充
        Map<String, String> paddings = ServiceBuilderProcessor.buildBasicPadding(serviceImpl, template);
        paddings.put("{Mapper}",mybatisService.getMapper().getName());
        paddings.put("{ServiceInterface}",mybatisService.getServiceInterface().getName());
        //克隆模版填充
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");   //获取克隆模版
        //克隆模版内容构建
        String cloneImportsBuilder = TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", mybatisService.getMapper().getReference()) +
                TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", serviceInterface.getReference())+
                TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", mybatisService.getEntity().getReference());
        paddings.put("{cloneImports}", cloneImportsBuilder);
        return TemplateUtils.paddingTemplate(template.getTemplate(),paddings);
    }
}



