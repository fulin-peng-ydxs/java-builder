package builder.core.build.builder.mybatis.plus;

import builder.core.build.builder.entity.mybatis.MybatisPlusEntityBuilder;
import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.core.build.builder.mybatis.mapper.MapperPlusBuilder;
import builder.model.build.config.content.orm.MybatisContent;
import builder.model.build.config.template.path.MybatisTemplatePath;
import builder.util.StringUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * mybatis-plus构建器
 * author: pengshuaifeng
 * 2023/9/10
 */
@Setter
@Getter
@Builder
public class MybatisPlusBuilder {

    protected MybatisBuilder mybatisBuilder;

    protected MybatisTemplatePath mybatisTemplatePath;

    /**
     * 构建器创建
     * 2023/9/20 21:57
     * @author pengshuaifeng
     */
    public MybatisPlusBuilder(MybatisBuilder mybatisBuilder,MybatisTemplatePath mybatisTemplatePath){
        this.mybatisBuilder=mybatisBuilder;
        this.mybatisTemplatePath=mybatisTemplatePath;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    protected void init(){
        initTemplatePath();
    }

    protected void initTemplatePath(){
        mybatisTemplatePath=mybatisTemplatePath==null?
                new MybatisTemplatePath():mybatisTemplatePath;
        if(StringUtil.isEmpty(mybatisTemplatePath.getEntityPath())){
            mybatisTemplatePath.setEntityPath("/template/basic/EntityMybatisPlusTemplate.txt");
        }
        if (StringUtil.isEmpty(mybatisTemplatePath.getMapperPath())) {
            mybatisTemplatePath.setMapperPath("/template/mybatis/mybatis-plus/MapperTemplate.txt");
        }
        if (StringUtil.isEmpty(mybatisTemplatePath.getMapperXmlPath())) {
            mybatisTemplatePath.setMapperXmlPath("/template/mybatis/mybatis-plus/MapperXmlTemplate.txt");
        }
        mybatisBuilder.setMapperBuilder(new MapperPlusBuilder());
        mybatisBuilder.setEntityBuilder(new MybatisPlusEntityBuilder());
        mybatisBuilder.setMybatisTemplatePath(mybatisTemplatePath);
        mybatisBuilder.initTemplate();
    }

    /**
     * 构建
     * 2023/9/18 00:06
     * @author pengshuaifeng
     */
    public void build(MybatisContent mybatisContent){
        mybatisBuilder.build(mybatisContent);
    }
}
