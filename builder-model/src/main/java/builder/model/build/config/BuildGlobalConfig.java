package builder.model.build.config;


import builder.model.build.config.template.TemplateCreateInfo;
import builder.model.build.config.template.TemplateEntity;

/**
 * 构建全局配置
 * author: pengshuaifeng
 * 2023/9/23
 */
public class BuildGlobalConfig {

    //模版创建信息
    public static TemplateCreateInfo templateCreateInfo=new TemplateCreateInfo();
    //模版前缀
    public static String templatePrefix="";
    //模板后缀
    public static String templateSuffix="";
    //entity模版配置
    public static TemplateEntity templateEntity=new TemplateEntity();
}
