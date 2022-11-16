package build.builder.data.classes.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
/**参数元信息
 *
 * 2022/9/7 0007-13:42
 * @author pengfulin
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamMeta {
    /**参数名称*/
    private String paramName;
    /**参数类型*/
    private Class<?> paramType;
    /**参数泛型*/
    private List<Class<?>> genericParams;
}
