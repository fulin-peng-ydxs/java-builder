package builder.core.build.builder.entity;

import builder.core.build.builder.base.Builder;
import builder.model.build.orm.entity.Entity;
import builder.model.build.orm.entity.Field;
import builder.util.ClassUtils;
import builder.util.TemplateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * 实体构建器
 * author: pengshuaifeng
 * 2023/9/6
 */
@Setter
@Getter
public class EntityBuilder extends Builder {

    protected Entity entity;

    protected boolean isIgnorePrimaryKey=false;

    private final List<EntityBuilder> entityBuilders;

    public EntityBuilder(){
        this("/template/basic/EntityTemplate.txt");
    }

    public EntityBuilder(String templatePath){
        this(templatePath,new LinkedList<>());
    }

    public EntityBuilder(List<EntityBuilder> entityBuilders){
        this("/template/basic/EntityTemplate.txt",entityBuilders);
    }

    public EntityBuilder(String templatePath,String cloneTemplatePath){
        this(templatePath,cloneTemplatePath,new LinkedList<>());
    }

    public EntityBuilder(String templatePath,List<EntityBuilder> entityBuilders){
        this(templatePath,"/template/basic/EntityTemplate.txt",entityBuilders);
    }

    public EntityBuilder(String templatePath,String cloneTemplatePath, List<EntityBuilder> entityBuilders){
        super(templatePath,cloneTemplatePath);
        this.entityBuilders=entityBuilders;
    }


    /**
     * 构建实体
     * 2023/9/6 21:53
     * @author pengshuaifeng
     */
    public String build(Entity entity){
        this.entity=entity;
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        paddings.put("{package}",entity.getPackages());
        paddings.put("{Entity}",entity.getName());
        paddings.put("{entity}", ClassUtils.nameToAttribute(entity.getName()));
        paddings.put("{description}",entity.getTableInfo().getDescription());
        //克隆模版填充
        Map<String, String> templateClones = template.getTemplateClones();
        String cloneFieldsTemplate = templateClones.get("cloneFields");
        String cloneImportsTemplate = templateClones.get("cloneImports");   //获取克隆模版
        Map<String, String> cloneFieldPaddings = new HashMap<>();//克隆模版内容填充
        StringBuilder cloneFieldsBuilder = new StringBuilder();
        Set<String> cloneImports=new HashSet<>();//克隆模版内容构建
        List<Field> fields = entity.getFields();
        for (Field field : fields) {
            if (isIgnorePrimaryKey) {  //是否忽略主键字段的处理
                if (field.getName().equals(entity.getPrimaryField().getName()))
                    continue;
            }
            cloneFieldPaddings.put("{fieldType}", field.getType().getSimpleName());
            cloneFieldPaddings.put("{filed}", field.getName());
            cloneFieldPaddings.put("{comment}", field.getColumnInfo().getDescription());
            String reference = field.getReference();
            if(!ClassUtils.ignoreReference(reference)){ //引用是否需要导入
                cloneImports.add(TemplateUtils.paddingTemplate(cloneImportsTemplate,"{import}", reference));
            }
            for (EntityBuilder entityBuilder : entityBuilders) {
                entityBuilder.setEntity(entity);
                entityBuilder.setIgnorePrimaryKey(isIgnorePrimaryKey);
                entityBuilder.fieldAddExt(cloneFieldPaddings,templateClones,field,cloneImports);
            }
            cloneFieldsBuilder.append(TemplateUtils.paddingTemplate(cloneFieldsTemplate,cloneFieldPaddings));
            cloneFieldPaddings = new HashMap<>();
        }
        paddings.put("{cloneFields}", cloneFieldsBuilder.toString());
        StringBuilder cloneImportsBuilder = new StringBuilder();
        cloneImports.forEach(cloneImportsBuilder::append);
        paddings.put("{cloneImports}",cloneImports.isEmpty()?"":cloneImportsBuilder.toString());
        entityBuilders.forEach(value->{
            value.setEntity(entity);
            value.setIgnorePrimaryKey(isIgnorePrimaryKey);
            value.globalAddExt(paddings);
        });
        return TemplateUtils.paddingTemplate(template.getTemplate(),paddings);
    }

    /**
     * 字段补充扩展
     * 2023/11/9 22:08
     * @author pengshuaifeng
     */
    protected void fieldAddExt(Map<String, String> cloneFieldPaddings, Map<String, String> templateClones, Field field, Set<String> cloneImports){}

    /**
     * 全局补充扩展
     * 2023/9/19 23:26
     * @author pengshuaifeng
     */
    protected void globalAddExt(Map<String, String> paddings){}
}
