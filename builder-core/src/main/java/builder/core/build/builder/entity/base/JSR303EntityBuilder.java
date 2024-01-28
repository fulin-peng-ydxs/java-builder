package builder.core.build.builder.entity.base;


import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.Field;
import builder.util.TemplateUtils;

/**
 * JSR303规范-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
public class JSR303EntityBuilder extends AnnotationEntityBuilder {

    public JSR303EntityBuilder(){
        super();
    }


    @Override
    protected void fieldAddExecute(StringBuilder annotationBuilder,String annotationTemplateClone ,Field field, StringBuilder importsBuilder){
        if (!BuildGlobalConfig.templateEntity.isJsr303Enable()) { //TODO 建议在统一调用里去做处理
            return;
        }
        if(!field.getColumnInfo().isNull()){
            annotationBuilder.append(annotationTemplateClone.replace("{Annotation}",
                    "@NotNull(message=\""+field.getColumnInfo().getDescription()+"不能为空\")"));
            String cloneImports = template.getTemplateClones().get("cloneImports");
            if (!importsBuilder.toString().contains("javax.validation.constraints.NotNull")) {
                importsBuilder.append(TemplateUtils.paddingTemplate(cloneImports,"{import}","javax.validation.constraints.NotNull"));
            }
        }
    }
}
