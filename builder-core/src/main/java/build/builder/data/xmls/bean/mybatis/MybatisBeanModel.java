package build.builder.data.xmls.bean.mybatis;

import build.builder.data.BuildModel;
import build.builder.data.xmls.meta.XmlElement;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
/**
 * Mybatis-Bean构建模型
 *
 * @author peng_fu_lin
 * 2022-10-09 15:11
 */

@Getter
@Setter
public class MybatisBeanModel extends BuildModel {
    /**映射名*/
    private String mappingName;
    /**命名空间*/
    private String nameSpace;
    /**sql元素*/
    private List<XmlElement> sqlXmlElements;
    /**基础元素*/
    private List<XmlElement> basicXmlElements;
    /**其他元素*/
    private List<XmlElement> otherXmlElements;
}