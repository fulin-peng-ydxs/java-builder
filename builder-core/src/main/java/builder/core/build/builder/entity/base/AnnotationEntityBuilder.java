package builder.core.build.builder.entity.base;


import builder.core.build.builder.entity.EntityBuilder;
import builder.model.build.orm.Field;
import builder.util.TemplateUtils;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Map;

/**
 * 注解-实体构造器
 *
 * @author pengshuaifeng
 * 2024/1/25
 */
@Getter
@Setter
public class AnnotationEntityBuilder extends EntityBuilder {

    // 时间格式
    protected String dateFormat="yyyy-MM-dd HH:mm:ss";

    public AnnotationEntityBuilder(){
        this("/template/basic/AnnotationEntityTemplate.txt");
    }

    public AnnotationEntityBuilder(String templatePath){
        this(templatePath,"/template/basic/AnnotationEntityTemplate.txt");
    }

    public AnnotationEntityBuilder(String templatePath,String cloneTemplatePath){
        super(templatePath,cloneTemplatePath);
    }


    @Override
    protected void fieldAddExt(Map<String, String> cloneFieldPaddings,Map<String, String> templateClones ,Field field, StringBuilder cloneImportsBuilder) {
        String annotationsValue = cloneFieldPaddings.get("{Annotations}");
        String value = fieldAddExecute(annotationsValue,templateClones, field, cloneImportsBuilder);
        if(value==null && annotationsValue==null){  //都为null,使用空代替
            value=TemplateUtils.templateNullValue;
        }else if(value != null && annotationsValue!=null){ //都不为null，如果是空，则覆盖，如果不是空，则追加
            boolean nullValue = annotationsValue.equals(TemplateUtils.templateNullValue);
            if(!nullValue)
                value=annotationsValue+value;
        }else if (annotationsValue!=null){ //原值不为null，则保持不变
            value=annotationsValue;
        }
        cloneFieldPaddings.put("{Annotations}",value);
    }

    /**
     * 默认字段补充执行
     * 2024/1/25 22:43
     * @author pengshuaifeng
     */
    protected String fieldAddExecute(String cloneFieldPadding,Map<String, String> templateClones ,Field field, StringBuilder cloneImportsBuilder){
        StringBuilder valueBuilder = new StringBuilder();
        String annotationTemplateClone = templateClones.get("cloneFieldAnnotations");
        //时间字段补充  //TODO 目前时间仅支持Date类型
        if(field.getType()==Date.class){
            if (cloneFieldPadding==null || !cloneFieldPadding.contains("@JsonFormat")) {
                valueBuilder.append(annotationTemplateClone.replace("{Annotation}",String.format("@JsonFormat(pattern =\"%s\", timezone = \"GMT+8\")",dateFormat)));
                String cloneImports = template.getTemplateClones().get("cloneImports");
                cloneImportsBuilder.append(TemplateUtils.paddingTemplate(cloneImports,"{import}","com.fasterxml.jackson.annotation.JsonFormat"));
            }
        }
        fieldAddExecute(valueBuilder,annotationTemplateClone,field,cloneImportsBuilder);
        return valueBuilder.length()==0?null:valueBuilder.toString();
    }

    /**
     * 字段补充执行
     * 2024/1/25 22:29
     * @author pengshuaifeng
     */
    protected void fieldAddExecute(StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, StringBuilder importsBuilder){}

}
