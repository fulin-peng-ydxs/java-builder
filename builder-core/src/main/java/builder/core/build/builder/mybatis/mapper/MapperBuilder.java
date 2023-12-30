package builder.core.build.builder.mybatis.mapper;

import builder.core.build.builder.base.Builder;
import builder.model.build.config.template.Template;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.util.ClassUtils;
import builder.util.StringUtils;
import builder.util.TemplateUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * mapper构建器
 * author: pengshuaifeng
 * 2023/9/9
 */
public class MapperBuilder {

    protected Mapper mapper;

    protected Template mapperTemplate;
    protected Template mapperXmlTemplate;

    public MapperBuilder(){
        this("/template/mybatis/MapperTemplate.txt","/template/mybatis/MapperXmlTemplate.txt");
    }

    public MapperBuilder(String mapperTemplatePath,String mapperXmlTemplatePath){
        this.mapperTemplate= Builder.generateTemplateIgnoreClone(mapperTemplatePath);
        this.mapperXmlTemplate= Builder.generateTemplate(mapperXmlTemplatePath);
    }


    /**
     * 构建映射器
     * 2023/9/6 21:53
     * @author pengshuaifeng
     */
    public String buildMapper(Mapper mapper){
        this.mapper=mapper;
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        Entity entity = mapper.getEntity();
        paddings.put("{package}",mapper.getPackages());
        paddings.put("{entityImport}",entity.getReference());
        paddings.put("{Mapper}",mapper.getName());
        paddings.put("{description}",mapper.getDescription());
        paddings.put("{Entity}",entity.getName());
        paddings.put("{entity}", ClassUtils.nameToAttribute(entity.getName()));
        Field primaryField = entity.getPrimaryField();
        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        paddings.put("{primaryKeyField}", primaryField.getName());
        return TemplateUtils.paddingTemplate(mapperTemplate.getTemplate(),paddings);
    }

    /**
     * 构建映射文件
     * 2023/9/9 21:16
     * @author pengshuaifeng
     */
    public String buildMapperXml(Mapper mapper){
        this.mapper=mapper;
        //基础模版填充
        Map<String, String> paddings = new HashMap<>();
        paddings=basicMapperXmlPaddings(paddings);
        //克隆模版填充
        Entity entity = mapper.getEntity();
        Field primaryField = entity.getPrimaryField();
        Map<String, String> templateClones = mapperXmlTemplate.getTemplateClones();
        String cloneInsertColumnsTemplate = templateClones.get("cloneInsertColumns");
        String cloneInsertFieldsTemplate = templateClones.get("cloneInsertFields");
        String cloneInsertBatchFieldsTemplate = templateClones.get("cloneInsertBatchFields");
        String cloneSelectColumnsTemplate = templateClones.get("cloneSelectColumns");
        String cloneWhereColumnsTemplate = templateClones.get("cloneWhereColumns");
        String cloneUpdateColumnsTemplate= templateClones.get("cloneUpdateColumns");
        String cloneResultsTemplate = templateClones.get("cloneResults"); //获取克隆模版
        StringBuilder insertColumns = new StringBuilder();
        StringBuilder insertFields = new StringBuilder();
        StringBuilder insertBatchFields = new StringBuilder();
        StringBuilder selectColumns = new StringBuilder();
        StringBuilder whereColumns = new StringBuilder();
        StringBuilder updateColumns = new StringBuilder();
        StringBuilder results = new StringBuilder();
        Map<String, String> clonePaddings = new HashMap<>();
        for (int i = 0; i < entity.getFields().size(); i++) {
            Field field = entity.getFields().get(i);
            ColumnInfo columnInfo = field.getColumnInfo();
            String fieldName = field.getName();
            String columnInfoName = columnInfo.getName();
            clonePaddings.put("{field}",fieldName);
            clonePaddings.put("{column}",columnInfoName);
            insertColumns.append(TemplateUtils.paddingTemplate(cloneInsertColumnsTemplate,clonePaddings));
            insertFields.append(TemplateUtils.paddingTemplate(cloneInsertFieldsTemplate,clonePaddings));
            insertBatchFields.append(TemplateUtils.paddingTemplate(cloneInsertBatchFieldsTemplate,clonePaddings));
            selectColumns.append(TemplateUtils.paddingTemplate(cloneSelectColumnsTemplate,clonePaddings));
            whereColumns.append(TemplateUtils.paddingTemplate(cloneWhereColumnsTemplate,clonePaddings));
            if(!field.getName().equals(primaryField.getName())){
                updateColumns.append(TemplateUtils.paddingTemplate(cloneUpdateColumnsTemplate,clonePaddings));
                results.append(TemplateUtils.paddingTemplate(cloneResultsTemplate,clonePaddings));
            }
        }
        paddings.put("{cloneInsertColumns}", StringUtils.substring(insertColumns.toString(),null,",",false,false));
        paddings.put("{cloneInsertFields}", StringUtils.substring(insertFields.toString(),null,",",false,false));
        paddings.put("{cloneInsertBatchFields}", StringUtils.substring(insertBatchFields.toString(),null,",",false,false));
        paddings.put("{cloneSelectColumns}", StringUtils.substring(selectColumns.toString(),null,",",false,false));
        paddings.put("{cloneWhereColumns}", StringUtils.clearLastSpan(whereColumns.toString()));
        paddings.put("{cloneUpdateColumns}", StringUtils.clearLastSpan(updateColumns.toString()));
        paddings.put("{cloneResults}", StringUtils.clearLastSpan(results.toString()));
        return TemplateUtils.paddingTemplate(mapperXmlTemplate.getTemplate(),paddings);
    }

    /**
     * 基础模版填充
     * 2023/9/20 01:27
     * @author pengshuaifeng
     */
    protected  Map<String, String> basicMapperXmlPaddings(Map<String, String> paddings ){
        Entity entity = mapper.getEntity();
        paddings.put("{nameSpace}",mapper.getReference());
        paddings.put("{entityReference}",entity.getReference());
        Field primaryField = entity.getPrimaryField();
        paddings.put("{primaryKeyColumn}",primaryField.getColumnInfo().getName());
        paddings.put("{primaryKeyField}",primaryField.getName());
        paddings.put("{tableName}",entity.getTableInfo().getName());
        return paddings;
    }
}
