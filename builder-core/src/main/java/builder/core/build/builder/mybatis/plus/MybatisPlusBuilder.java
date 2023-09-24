package builder.core.build.builder.mybatis.plus;

import builder.core.build.builder.entity.mybatis.MybatisPlusEntityBuilder;
import builder.core.build.builder.mybatis.MybatisBuilder;
import builder.core.build.builder.mybatis.mapper.MapperPlusBuilder;
import builder.model.build.config.content.MybatisContent;
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

    private MybatisBuilder mybatisBuilder;

    private MybatisContent mybatisContent;

    /**
     * 构建器创建
     * 2023/9/20 21:57
     * @author pengshuaifeng
     */
    MybatisPlusBuilder(MybatisBuilder mybatisBuilder,MybatisContent mybatisContent){
        this.mybatisBuilder=mybatisBuilder;
        this.mybatisContent=mybatisContent;
        init();
    }

    /**
     * 构建器初始化
     * 2023/9/17 19:18
     * @author pengshuaifeng
     */
    private void init(){
        initBuilder();
    }


    public void initBuilder(){
        mybatisBuilder.setMapperBuilder(new MapperPlusBuilder());
        mybatisBuilder.setEntityBuilder(new MybatisPlusEntityBuilder());
    }

    /**
     * 构建
     * 2023/9/18 00:06
     * @author pengshuaifeng
     */
    public void build(MybatisContent mybatisContent){
        mybatisBuilder.build(mybatisContent);
    }

    public void build(){
        mybatisBuilder.build(mybatisContent);
    }
}
