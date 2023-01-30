package build.builder.data.classes.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
/**继承元信息
 * 2022/9/7 0007-13:40
 * @author pengfulin
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtendsMeta {
    /**继承类型*/
    private Class<?> extendsType;
    /**继承泛型*/
    private List<GenericMeta> genericParams;
}
