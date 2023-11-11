package builder.core.build.builder.entity.base;


import builder.core.build.builder.entity.EntityBuilder;

/**
 * swagger-实体构建器
 * author: pengshuaifeng
 * 2023/11/9
 */
public class SwaggerEntityBuilder extends EntityBuilder {

    public SwaggerEntityBuilder(){
        this("/template/basic/SwaggerEntityTemplate.txt");
    }

    public SwaggerEntityBuilder(String templatePath){
        super(templatePath);
    }
}
