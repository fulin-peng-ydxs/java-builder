package build.source.data;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
/**构建元数据
 * 2022/9/5 0005-14:28
 * @author pengfulin
*/
@Setter
@Getter
public class BuildMeta {
    /**元数据名：一般为表名*/
    private String name;
    /**元数据项集合：一般表的字段项集合*/
    private List<BuildMetaItem> dataItems;
}
