package builder.core.build.builder.entity.base;


import builder.core.build.builder.entity.EntityBuilder;
import builder.model.build.orm.entity.Field;
import builder.util.DateUtils;
import builder.util.TemplateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.Set;

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
    protected String dateFormat= DateUtils.defaultFormat;

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
    protected void fieldAddExt(Map<String, String> cloneFieldPaddings, Map<String, String> templateClones , Field field, Set<String> cloneImports) {
        String annotationsValue = cloneFieldPaddings.get("{Annotations}");
        String value = fieldAddExecute(annotationsValue,templateClones, field, cloneImports);
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
     * 字段补充执行
     * 2024/1/25 22:43
     * @author pengshuaifeng
     */
    protected String fieldAddExecute(String annotationsValue,Map<String, String> templateClones ,Field field, Set<String> cloneImports){
        StringBuilder valueBuilder = new StringBuilder();
        String annotationTemplateClone = templateClones.get("cloneFieldAnnotations");
        fieldAddDefaultExecute(annotationsValue,valueBuilder,annotationTemplateClone,field,cloneImports);
        fieldAddExecute(annotationsValue,valueBuilder,annotationTemplateClone,field,cloneImports);
        return valueBuilder.length()==0?null:valueBuilder.toString();
    }

    /**
     * 字段补充默认执行
     * 2024/1/25 22:29
     * @author pengshuaifeng
     */
    protected void fieldAddDefaultExecute(String annotationsValue,StringBuilder annotationBuilder,String annotationTemplateClone ,Field field,Set<String> cloneImports){
        //时间字段补充  //TODO 目前时间仅支持Date类型
        if(field.getType()==Date.class){
            if (annotationsValue==null || !annotationsValue.contains("@JsonFormat")) {
                annotationBuilder.append(annotationTemplateClone.replace("{Annotation}",String.format("@JsonFormat(pattern =\"%s\", timezone = \"GMT+8\")",dateFormat)));
                String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
                cloneImports.add(TemplateUtils.paddingTemplate(cloneImportsTemplate,"{import}","com.fasterxml.jackson.annotation.JsonFormat"));
            }
        }
    }

    /**
     * 字段补充执行
     * 2024/1/25 22:29
     * @author pengshuaifeng
     */
    protected void fieldAddExecute(String annotationsValue,StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, Set<String> cloneImports){}


    /**
     * 字段通用填充
     * 2024/8/9 上午11:22
     * @author fulin-peng
     */
    protected void fieldGeneralPadding(StringBuilder annotationBuilder,String annotationBuildValue, String annotationTemplateClone,String annotationReference ,Set<String> cloneImports){
        annotationBuilder.append(annotationTemplateClone.replace("{Annotation}", annotationBuildValue));
        String cloneImportsTemplate = template.getTemplateClones().get("cloneImports");
        if (!cloneImports.contains(annotationReference)) {
            cloneImports.add(TemplateUtils.paddingTemplate(cloneImportsTemplate,"{import}",annotationReference));
        }
    }
}
