package build.builder.data.xmls.meta;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;
/**
 * Xml元信息
 *
 * @author peng_fu_lin
 * 2022-10-09 14:54
 */
@Getter
@Setter
public class  XmlElement {
    /**元素id*/
    private String id;
    /**元素名称*/
    private String name;
    /**元素属性*/
    private Map<String,String> attributes;
    /**元素内容*/
    private String content;
    /**元素子集*/
    private List<XmlElement> childrenXmlElements;
}