package builder.core.build.builder.entity.mybatis;

import builder.core.build.builder.entity.EntityBuilder;
import builder.model.build.orm.entity.Field;
import java.util.Map;
/**
 * mybatisPlus-实体构建器
 * author: pengshuaifeng
 * 2023/9/19
 */
public class MybatisPlusEntityBuilder extends EntityBuilder {


    public MybatisPlusEntityBuilder(){
        this("/template/basic/MybatisPlusEntityTemplate.txt","/template/basic/EntityTemplate.txt");
    }

    public MybatisPlusEntityBuilder(String templatePath,String cloneTemplatePath){
        super(templatePath,cloneTemplatePath);
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
}
