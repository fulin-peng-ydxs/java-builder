package builder.core.build.builder.mvc.service.baic;


import builder.model.build.config.template.Template;
import builder.model.build.mvc.service.Service;
import builder.util.TemplateUtil;

/**
 * 服务实现构建器
 * author: pengshuaifeng
 * 2023/9/23
 */
public class ServiceImplBuilder {

    protected Template template;

    public ServiceImplBuilder(){};

    public ServiceImplBuilder(String templatePath){
        this.template= new Template(TemplateUtil.getTemplate(templatePath),
                TemplateUtil.getTemplates(templatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(templatePath)));
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
