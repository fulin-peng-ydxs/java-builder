package builder.core.build.builder.base;


import builder.model.build.config.template.Template;
import builder.util.TemplateUtils;
import lombok.NoArgsConstructor;

/**
 * 基础构建器
 * author: pengshuaifeng
 * 2023/9/24
 */
@NoArgsConstructor
public class Builder {

    protected Template template;

    public Builder(String path){
        this(path,null);
    }

    public Builder(String path,String templateClonePath){
        if(templateClonePath!= null)
            template=generateTemplate(path,templateClonePath);
        else
            template=generateTemplate(path);
    }

    public static Template generateTemplate(String path){
        return new Template(TemplateUtils.getTemplate(path),
                TemplateUtils.getTemplates(path), TemplateUtils.getCloneTemplates(TemplateUtils.getCloneTemplatePath(path)));
    }

    public static Template generateTemplate(String path,String clonePath){
        return new Template(TemplateUtils.getTemplate(path),
                TemplateUtils.getTemplates(path), TemplateUtils.getCloneTemplates(TemplateUtils.getCloneTemplatePath(clonePath)));
    }

    public static Template generateTemplateIgnoreClone(String path){
        return new Template(TemplateUtils.getTemplate(path),
                TemplateUtils.getTemplates(path),null);
    }
}
