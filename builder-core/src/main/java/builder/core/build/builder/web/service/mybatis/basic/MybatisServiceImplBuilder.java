package builder.core.build.builder.web.service.mybatis.basic;


import builder.core.build.builder.web.service.ServiceBuilderProcessor;
import builder.core.build.builder.web.service.baic.ServiceImplBuilder;
import builder.model.build.orm.entity.Entity;
import builder.model.build.orm.entity.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.build.web.service.MybatisService;
import builder.model.build.web.service.Service;
import builder.util.ClassUtils;
import builder.util.TemplateUtils;

import java.util.Map;

/**
 * mybatis服务实现构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class MybatisServiceImplBuilder extends ServiceImplBuilder {

    public MybatisServiceImplBuilder(){
        this("/template/web/service/mybatis/MybatisServiceImpl.txt");
    }

    public MybatisServiceImplBuilder(String templatePath){
        super(templatePath);
    }

    public MybatisServiceImplBuilder(String templatePath,String templateClonePath){
        super(templatePath,templateClonePath);
    }

    @Override
    public String buildImpl(Service serviceImpl) {
        MybatisService mybatisService = (MybatisService) serviceImpl;
        //基础模版填充
        Map<String, String> paddings = ServiceBuilderProcessor.buildBasicPadding(serviceImpl, template);
        Mapper mapper = mybatisService.getMapper();
        Entity entity = serviceImpl.getEntity();
        paddings.put("{Mapper}", mapper.getName());
        paddings.put("{mapper}", ClassUtils.nameToAttribute(mapper.getName()));
        paddings.put("{ServiceInterface}",serviceImpl.getServiceInterface().getName());
        Field primaryField = entity.getPrimaryField();
        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        paddings.put("{primaryKeyField}",primaryField.getName());
        paddings.put("{primaryKeyField_Up}",ClassUtils.attributeToName(primaryField.getName()));
        paddings.put("{primaryKeyFieldName}", ClassUtils.attributeToName(primaryField.getName()));
        //克隆模版填充
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");   //获取克隆模版
        //克隆模版内容构建
        String cloneImportsBuilder = TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", mapper.getReference()) +
                TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", serviceImpl.getServiceInterface().getReference())+
                TemplateUtils.paddingTemplate(cloneImportsTemplate, "{import}", entity.getReference());
        paddings.put("{cloneImports}", cloneImportsBuilder);
        return TemplateUtils.paddingTemplate(template.getTemplate(),paddings);
    }
}
