package builder.model.build.config;


import builder.model.build.config.template.basic.TemplateEntity;
import builder.model.build.config.template.basic.TemplateInfo;
import builder.model.build.config.template.basic.TemplateMapper;
import builder.model.build.config.template.basic.TemplateWeb;
import builder.model.build.config.template.mybatis.MybatisPlusGlobalConfig;

/**
 * 构建全局配置
 * author: pengshuaifeng
 * 2023/9/23
 */
// TODO 放入线程变量，以适应web并发环境
public class BuildGlobalConfig {

    //模版前缀 TODO 暂未实现
    public static String templatePrefix="";

    //模板后缀 TODO 暂未实现
    public static String templateSuffix="";

    //模版信息
    public static TemplateInfo templateInfo =TemplateInfo.defaultInstance;

    //entity模版配置
    public static TemplateEntity templateEntity=TemplateEntity.defaultInstance;

    //entity模版配置
    public static TemplateMapper templateMapper=TemplateMapper.defaultInstance;

    //web模板配置
    public static TemplateWeb templateWeb=TemplateWeb.defaultInstance;

    //mybatis-plus配置
    public static MybatisPlusGlobalConfig mybatisPlusConfig=MybatisPlusGlobalConfig.defaultInstance;
}
