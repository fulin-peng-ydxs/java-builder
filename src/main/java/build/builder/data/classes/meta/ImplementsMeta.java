package build.builder.data.classes.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
/**实现元信息
 *
 * 2022/9/7 0007-13:41
 * @author pengfulin
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImplementsMeta {

    private Class<?> implementsType;

    private List<Class<?>> genericParams;
}
