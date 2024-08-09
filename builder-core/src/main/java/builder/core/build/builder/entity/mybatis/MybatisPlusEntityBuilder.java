package builder.core.build.builder.entity.mybatis;

import builder.core.build.builder.entity.base.AnnotationEntityBuilder;
import builder.model.build.config.BuildGlobalConfig;
import builder.model.build.orm.entity.Field;

import java.util.Map;
import java.util.Set;

/**
 * mybatisPlus-实体构建器
 * author: pengshuaifeng
 * 2023/9/19
 */
public class MybatisPlusEntityBuilder extends AnnotationEntityBuilder {


    public MybatisPlusEntityBuilder(){
        this("/template/basic/MybatisPlusEntityTemplate.txt");
    }

    public MybatisPlusEntityBuilder(String templatePath){
        super(templatePath);
        super.isIgnorePrimaryKey=true;
    }

    @Override
    protected void globalAddExt(Map<String, String> paddings) {
        //处理表名
        paddings.put("{tableName}",entity.getTableInfo().getName());
        //处理主键
        Field primaryField = entity.getPrimaryField();
        paddings.put("{fieldType}", primaryField.getType().getSimpleName());
        paddings.put("{filed}", primaryField.getName());
        paddings.put("{comment}", primaryField.getColumnInfo().getDescription());
    }


    @Override
    protected void fieldAddExecute(String annotationsValue,StringBuilder annotationBuilder, String annotationTemplateClone, Field field, Set<String> cloneImports) {
        Set<String> manualOperationFields = BuildGlobalConfig.mybatisPlusConfig.getManualOperationMappingFields();
        //添加@TableField
        if(!manualOperationFields.isEmpty() &&
                (manualOperationFields.contains(field.getColumnInfo().getName()) ||manualOperationFields.contains(field.getName()))
        ){
            addTableField(annotationBuilder,annotationTemplateClone,field,cloneImports);
        }
    }
    
    /**
     * 添加@TableField
     * 2024/8/9 上午11:06
     * @author fulin-peng
     */
    public void addTableField(StringBuilder annotationBuilder, String annotationTemplateClone, Field field, Set<String> cloneImports){
        fieldGeneralPadding(annotationBuilder,"@TableField(value=\""+field.getColumnInfo().getName()+"\")",annotationTemplateClone,
                "com.baomidou.mybatisplus.annotation.TableField",cloneImports);
    }
}
