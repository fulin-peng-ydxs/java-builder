package builder.core.build.builder.mybatis.mapper;

import builder.model.build.config.builder.BaseBuilder;
import builder.model.build.config.template.Template;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.util.ClassUtil;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
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
        this.mapperTemplate= BaseBuilder.generateTemplateIgnoreClone(mapperTemplatePath);
        this.mapperXmlTemplate=BaseBuilder.generateTemplate(mapperXmlTemplatePath);
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
        paddings.put("{entity}", ClassUtil.nameToAttribute(entity.getName()));
        Field primaryField = entity.getPrimaryField();
        paddings.put("{PrimaryKeyField}",primaryField.getType().getSimpleName());
        paddings.put("{primaryKeyField}", primaryField.getName());
        return TemplateUtil.paddingTemplate(mapperTemplate.getTemplate(),paddings);
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
            insertColumns.append(TemplateUtil.paddingTemplate(cloneInsertColumnsTemplate,clonePaddings));
            insertFields.append(TemplateUtil.paddingTemplate(cloneInsertFieldsTemplate,clonePaddings));
            insertBatchFields.append(TemplateUtil.paddingTemplate(cloneInsertBatchFieldsTemplate,clonePaddings));
            selectColumns.append(TemplateUtil.paddingTemplate(cloneSelectColumnsTemplate,clonePaddings));
            whereColumns.append(TemplateUtil.paddingTemplate(cloneWhereColumnsTemplate,clonePaddings));
            if(!field.getName().equals(primaryField.getName())){
                updateColumns.append(TemplateUtil.paddingTemplate(cloneUpdateColumnsTemplate,clonePaddings));
                results.append(TemplateUtil.paddingTemplate(cloneResultsTemplate,clonePaddings));
            }
        }
        paddings.put("{cloneInsertColumns}", StringUtil.substring(insertColumns.toString(),null,",",false,false));
        paddings.put("{cloneInsertFields}",StringUtil.substring(insertFields.toString(),null,",",false,false));
        paddings.put("{cloneInsertBatchFields}",StringUtil.substring(insertBatchFields.toString(),null,",",false,false));
        paddings.put("{cloneSelectColumns}",StringUtil.substring(selectColumns.toString(),null,",",false,false));
        paddings.put("{cloneWhereColumns}",StringUtil.clearLastSpan(whereColumns.toString()));
        paddings.put("{cloneUpdateColumns}",StringUtil.clearLastSpan(updateColumns.toString()));
        paddings.put("{cloneResults}",StringUtil.clearLastSpan(results.toString()));
        return TemplateUtil.paddingTemplate(mapperXmlTemplate.getTemplate(),paddings);
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
