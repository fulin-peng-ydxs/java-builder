package build.builder.data.classes.meta;

import lombok.*;

import java.util.List;

/**
 * 泛型元信息
 *
 * @author peng_fu_lin
 * 2022-12-22 11:13
 */
@Getter
@Setter
@AllArgsConstructor
public class GenericMeta {
    /**泛型类型*/
    private Class<?> type;
    /**泛型子类*/
    private List<GenericMeta> children;

}