package builder.model.build.config.template.mybatis;

import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * mybatis-plus全局配置
 *
 * @author fulin-peng
 * 2024-08-09  10:16
 */
@Data
public class MybatisPlusGlobalConfig {

    public static final MybatisPlusGlobalConfig defaultInstance=new MybatisPlusGlobalConfig();

    //需要使用手动字段映射的字段（加入TableField注解实现）
    private Set<String> manualOperationMappingFields = Collections.EMPTY_SET;
}
