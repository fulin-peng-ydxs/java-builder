package build.builder.data.classes.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 业务类:用于代替构建中的类的信息描述
 *
 * @author peng_fu_lin
 * 2022-11-16 14:42
 */
@Data
@AllArgsConstructor
public class BusinessClass {
    /**类名称*/
    private String name;
    /**包声明*/
    private String packages;
}