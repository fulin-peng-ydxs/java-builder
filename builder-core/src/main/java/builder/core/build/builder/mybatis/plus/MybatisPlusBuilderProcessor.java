package builder.core.build.builder.mybatis.plus;

import builder.core.build.builder.entity.mybatis.MybatisPlusEntityBuilder;
import builder.core.build.builder.mybatis.MybatisBuilderProcessor;
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
public class MybatisPlusBuilderProcessor {

    private MybatisBuilderProcessor mybatisBuilderProcessor;

    private MybatisContent mybatisContent;

    private boolean ignoreInitEntityBuilder;
    private boolean ignoreInitMapperBuilder;

    /**
     * 构建器创建
     * 2023/9/20 21:57
     * @author pengshuaifeng
     */
    MybatisPlusBuilderProcessor(MybatisBuilderProcessor mybatisBuilderProcessor, MybatisContent mybatisContent,
                                boolean ignoreInitEntityBuilder, boolean ignoreInitMapperBuilder){
        this.mybatisBuilderProcessor = mybatisBuilderProcessor;
        this.mybatisContent=mybatisContent;
        this.ignoreInitEntityBuilder=ignoreInitEntityBuilder;
        this.ignoreInitMapperBuilder= ignoreInitMapperBuilder;
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
        if(!ignoreInitEntityBuilder)
            mybatisBuilderProcessor.setEntityBuilder(new MybatisPlusEntityBuilder());
        if(!ignoreInitMapperBuilder)
            mybatisBuilderProcessor.setMapperBuilder(new MapperPlusBuilder());
    }

    /**
     * 构建
     * 2023/9/18 00:06
     * @author pengshuaifeng
     */
    public void build(MybatisContent mybatisContent){
        mybatisBuilderProcessor.build(mybatisContent);
    }

    public void build(){
        mybatisBuilderProcessor.build(mybatisContent);
    }
}
