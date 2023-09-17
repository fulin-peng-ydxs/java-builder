package builder.core.build.builder.mvc.service.mybatis.plus;

import builder.core.build.builder.mvc.service.ServiceBuilder;
import builder.model.build.config.template.Template;
import builder.model.build.mvc.service.ServiceImpl;
import builder.model.build.mvc.service.ServiceInterface;
import builder.util.StringUtil;
import builder.util.TemplateUtil;

import java.util.Map;

/**
 * mybatis-plus服务构建器
 * author: pengshuaifeng
 * 2023/9/12
 */
public class MybatisPlusServiceBuilder extends ServiceBuilder {


    @Override
    public String buildImpl(ServiceImpl serviceImpl,ServiceInterface serviceInterface,Template serviceTemplate) {
        //基础模版填充
        Map<String, String> paddings = buildBasic(serviceImpl, serviceTemplate);
        paddings.put("{Mapper}",serviceImpl.getMapper().getName());
        paddings.put("{ServiceInterface}",serviceInterface.getName());
        //克隆模版填充
        String cloneImportsTemplate = serviceTemplate.getTemplateClones().get("cloneImports");   //获取克隆模版
        //克隆模版内容构建
        String cloneImportsBuilder = TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", serviceImpl.getMapper().getReference()) +
                TemplateUtil.paddingTemplate(cloneImportsTemplate, "{import}", serviceInterface.getReference());
        paddings.put("{cloneImports}", StringUtil.clearLastSpan(cloneImportsBuilder));
        return TemplateUtil.paddingTemplate(serviceTemplate.getTemplate(),paddings);
    }
}
