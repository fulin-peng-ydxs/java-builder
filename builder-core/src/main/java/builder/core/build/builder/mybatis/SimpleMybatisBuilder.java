package builder.core.build.builder.mybatis;

import builder.core.build.builder.entity.EntityBuilder;
import builder.core.build.builder.mybatis.mapper.MapperBuilder;
import builder.model.build.config.path.MybatisPath;
import builder.model.build.config.template.Template;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的mybatis构建器
 * author: pengshuaifeng
 * 2023/9/4
 */
public class SimpleMybatisBuilder extends MybatisBuilder {

    private Template entityTemplate;
    private Template mapperTemplate;
    private Template mapperXmlTemplate;

    public SimpleMybatisBuilder(ConnectionInfo connectionInfo, MybatisPath mybatisPath){
        super(connectionInfo,mybatisPath.getRoot(),mybatisPath.getEntity(),
                mybatisPath.getMapper(),mybatisPath.getMapperXml());
        initTemplate();
    }

    public SimpleMybatisBuilder(ConnectionInfo connectionInfo,String rootPath){
        super(connectionInfo,rootPath);
        initTemplate();
    }

    public SimpleMybatisBuilder(ConnectionInfo connectionInfo, MybatisPath mybatisPath, MybatisTemplatePath mybatisTemplatePath){
        this(connectionInfo,mybatisPath);
        initTemplate(mybatisTemplatePath.getEntityPath(), mybatisTemplatePath.getMapperPath(), mybatisTemplatePath.getMapperXmlPath());
    }

    public SimpleMybatisBuilder(ConnectionInfo connectionInfo, String rootPath, MybatisTemplatePath mybatisTemplatePath){
        this(connectionInfo,rootPath);
        initTemplate(mybatisTemplatePath.getEntityPath(), mybatisTemplatePath.getMapperPath(), mybatisTemplatePath.getMapperXmlPath());
    }

    /**
     * 初始化模版
     * 2023/9/4 21:01
     * @author pengshuaifeng
     */
    protected void initTemplate(String entityTemplatePath,String mapperTemplatePath, String mapperXmlTemplatePath){
        this.entityTemplate=new Template(TemplateUtil.getTemplate(entityTemplatePath),
                TemplateUtil.getTemplates(entityTemplatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(entityTemplatePath)));
        this.mapperTemplate=new Template(TemplateUtil.getTemplate(mapperTemplatePath),
                TemplateUtil.getTemplates(mapperTemplatePath),null);
        this.mapperXmlTemplate= new Template(TemplateUtil.getTemplate(mapperXmlTemplatePath),
                TemplateUtil.getTemplates(mapperXmlTemplatePath),TemplateUtil.getCloneTemplates(TemplateUtil.getCloneTemplatePath(mapperXmlTemplatePath)));
    }

    protected void initTemplate(){
        initTemplate("/template/basic/EntityTemplate.txt",
                "/template/mybatis/simple/MapperTemplate.txt",
                "/template/mybatis/simple/MapperXmlTemplate.txt");
    }


    @Override
    protected void buildEntity(Entity entity, String path) {
        String entityValue = buildEntityValue(entity);
        responder.execute(entityValue,entity.getName()+".java",path);
    }

    protected String buildEntityValue(Entity entity){
        return EntityBuilder.build(entity,entityTemplate);
    }


    @Override
    protected void buildMapper(Mapper mapper, String path) {
        String mapperValue = buildMapperValue(mapper);
        responder.execute(mapperValue,mapper.getName()+".java",path);
    }

    protected String buildMapperValue(Mapper mapper){
        return MapperBuilder.build(mapper,mapperTemplate);
    }

    @Override
    protected void buildMapperXml(Mapper mapper, String path) {
        String mapperXmlValue = buildMapperXmlValue(mapper);
        responder.execute(mapperXmlValue,mapper.getName()+".xml",path);
    }

    protected String buildMapperXmlValue(Mapper mapper){
        //基础模版填充
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        Entity entity = mapper.getEntity();
        paddings.put("{nameSpace}",mapper.getReference());
        paddings.put("{entityReference}",entity.getReference());
        Field primaryField = entity.getPrimaryField();
        paddings.put("{primaryKeyColumn}",primaryField.getColumnInfo().getName());
        paddings.put("{primaryKeyField}",primaryField.getName());
        paddings.put("{tableName}",entity.getTableInfo().getName());
        //克隆模版填充
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

}
