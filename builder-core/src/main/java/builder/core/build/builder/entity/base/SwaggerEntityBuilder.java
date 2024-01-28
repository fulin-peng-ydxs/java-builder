package builder.core.build.builder.entity.base;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.Field;

/**
 * swagger-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
public class SwaggerEntityBuilder extends AnnotationEntityBuilder {

    //TODO 非模版核心构建器，可不用创建template对象
    public SwaggerEntityBuilder(){
        super("/template/basic/SwaggerEntityTemplate.txt","/template/basic/AnnotationEntityTemplate.txt");
    }


    @Override
    protected void fieldAddExecute(StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, StringBuilder importsBuilder){
        if(!BuildGlobalConfig.templateEntity.isSwaggerEnable()){
            return;
        }
        annotationBuilder.append(annotationTemplateClone.replace("{Annotation}",
                "@ApiModelProperty(value =\""+field.getColumnInfo().getDescription()+"\")"));
    }
}
