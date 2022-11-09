package build.source.data.meta.bean;

import build.source.data.meta.BuildResolverModel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**元实体数据模型
 * 2022/9/5 0005-14:28
 * @author pengfulin
*/
@Setter
@Getter
public class BuildBean extends BuildResolverModel {
    /**元实体名*/
    private String name;
    /**元实体描述*/
    private String description;
    /**元数据项集合*/
    private List<BuildBeanItem> dataItems;
}
