package builder.core.build.builder.mybatis;

import builder.core.build.builder.entity.EntityBuilder;
import builder.core.build.builder.mybatis.mapper.MapperBuilder;
import builder.model.build.config.path.MybatisPath;
import builder.model.build.config.template.Template;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.model.build.orm.Entity;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.jdbc.ConnectionInfo;
import builder.util.TemplateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 简单的mybatis构建器
 * author: pengshuaifeng
 * 2023/9/4
 */
@Setter
@Getter
@NoArgsConstructor
public class SimpleMybatisBuilder extends MybatisBuilder {

    //构建模版
    protected Template entityTemplate;
    protected Template mapperTemplate;
    protected Template mapperXmlTemplate;
    //通用构建器
    protected EntityBuilder entityBuilder=new EntityBuilder();
    protected MapperBuilder mapperBuilder=new MapperBuilder();

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
        return entityBuilder.build(entity,entityTemplate);
    }


    @Override
    protected void buildMapper(Mapper mapper, String path) {
        String mapperValue = buildMapperValue(mapper);
        responder.execute(mapperValue,mapper.getName()+".java",path);
    }

    protected String buildMapperValue(Mapper mapper){
        return mapperBuilder.buildMapper(mapper,mapperTemplate);
    }

    @Override
    protected void buildMapperXml(Mapper mapper, String path) {
        String mapperXmlValue = buildMapperXmlValue(mapper);
        responder.execute(mapperXmlValue,mapper.getName()+".xml",path);
    }

    protected String buildMapperXmlValue(Mapper mapper){
       return mapperBuilder.buildMapperXml(mapper,mapperXmlTemplate);
    }
}
