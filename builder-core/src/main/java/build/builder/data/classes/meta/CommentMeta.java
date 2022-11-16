package build.builder.data.classes.meta;

import build.builder.data.classes.enums.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**注释元信息
 *
 * 2022/9/7 0007-11:56
 * @author pengfulin
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentMeta {
    /**注释类型*/
    private CommentType commentType;

    /**注释描述内容*/
    private String description;

    /**注释标签内容*/
    private Map<String,String> labels;
}
