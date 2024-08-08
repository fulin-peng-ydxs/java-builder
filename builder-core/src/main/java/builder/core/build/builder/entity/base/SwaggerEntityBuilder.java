package builder.core.build.builder.entity.base;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.entity.Field;
import lombok.extern.slf4j.Slf4j;

/**
 * swagger-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
@Slf4j
public class SwaggerEntityBuilder extends AnnotationEntityBuilder {

    public SwaggerEntityBuilder(){
        super("/template/basic/SwaggerEntityTemplate.txt",
                "/template/basic/AnnotationEntityTemplate.txt");
    }


    @Override
    protected void fieldAddExecute(StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, StringBuilder importsBuilder){
        if(!BuildGlobalConfig.templateEntity.isSwaggerEnable()){
            log.debug("swagger-实体构建器不参与构建：isSwaggerEnable={}",false);
            return;
        }
        annotationBuilder.append(annotationTemplateClone.replace("{Annotation}",
                "@ApiModelProperty(value =\""+field.getColumnInfo().getDescription()+"\")"));
    }
}
