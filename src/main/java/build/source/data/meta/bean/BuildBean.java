package build.source.data.meta.bean;

import build.source.data.meta.BuildResolverModel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**实体数据模型
 * 2022/9/5 0005-14:28
 * @author pengfulin
*/
@Setter
@Getter
public class BuildBean extends BuildResolverModel {
    /**元数据名：一般为表名*/
    private String name;
    /**元数据项集合：一般表的字段项集合*/
    private List<BuildBeanItem> dataItems;
}
