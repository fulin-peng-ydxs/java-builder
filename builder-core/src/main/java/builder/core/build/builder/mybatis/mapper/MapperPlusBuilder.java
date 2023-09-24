package builder.core.build.builder.mybatis.mapper;

import builder.model.build.orm.Entity;
import builder.model.build.orm.Field;
import builder.model.build.orm.mybatis.Mapper;
import builder.model.resolve.database.ColumnInfo;
import builder.util.StringUtil;
import builder.util.TemplateUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis-plus的mapper构建器
 * author: pengshuaifeng
 * 2023/9/20
 */
public class MapperPlusBuilder extends MapperBuilder{

    public MapperPlusBuilder(){
        this("/template/mybatis/mybatis-plus/MapperTemplate.txt",
                "/template/mybatis/mybatis-plus/MapperXmlTemplate.txt");
    }

    public MapperPlusBuilder(String mapperTemplatePath,String mapperXmlTemplatePath){
        super(mapperTemplatePath,mapperXmlTemplatePath);
    }


    @Override
    public String buildMapperXml(Mapper mapper) {
        Map<String, String> paddings = new HashMap<>();
        //基础模版填充
        paddings=basicMapperXmlPaddings(paddings);
        //克隆模版填充
        Entity entity = mapper.getEntity();
        Field primaryField = entity.getPrimaryField();
        Map<String, String> templateClones = mapperXmlTemplate.getTemplateClones();
        String cloneResultsTemplate = templateClones.get("cloneResults");
        StringBuilder results = new StringBuilder();
        Map<String, String> clonePaddings = new HashMap<>();
        for (int i = 0; i < entity.getFields().size(); i++) {
            Field field = entity.getFields().get(i);
            ColumnInfo columnInfo = field.getColumnInfo();
            String fieldName = field.getName();
            String columnInfoName = columnInfo.getName();
            clonePaddings.put("{field}",fieldName);
            clonePaddings.put("{column}",columnInfoName);
            if(!field.getName().equals(primaryField.getName())){
                results.append(TemplateUtil.paddingTemplate(cloneResultsTemplate,clonePaddings));
            }
        }
        paddings.put("{cloneResults}", StringUtil.clearLastSpan(results.toString()));
        return TemplateUtil.paddingTemplate(mapperXmlTemplate.getTemplate(),paddings);
    }
}
