package builder.model.build.config.builder;


import builder.model.build.config.template.Template;
import builder.util.TemplateUtil;
import lombok.NoArgsConstructor;

/**
 * 基础构建器
 * author: pengshuaifeng
 * 2023/9/24
 */
@NoArgsConstructor
public class BaseBuilder{

    protected Template template;

    public BaseBuilder(String path){
        template=generateTemplate(path);
    }

    public static Template generateTemplate(String path){
        return new Template(TemplateUtil.getTemplate(path),
                TemplateUtil.getTemplates(path),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(path)));
    }

    public static Template generateTemplateIgnoreClone(String path){
        return new Template(TemplateUtil.getTemplate(path),
                TemplateUtil.getTemplates(path),null);
    }
}
