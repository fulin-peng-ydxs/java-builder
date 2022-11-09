import build.builder.data.BuildModel;
import build.builder.data.xmls.meta.XmlElement;
import build.builder.util.CollectionUtil;
import lombok.Data;
import org.junit.Test;

import java.util.List;

/**
 * 流测试
 *
 * @author peng_fu_lin
 * 2022-09-19 11:08
 */
public class TestApi {

    @Test
    public void test01(){
        System.out.println(null instanceof String);
        List<XmlElement> xmlElements = CollectionUtil.asList(new XmlElement(), null);
        for (XmlElement xmlElement : xmlElements) {
            System.out.println(xmlElement.getName());
        }
        System.out.println(xmlElements);
    }

    @Test
    public void test02() {
        System.out.println(BuildModel.class.getResource("").getFile());
    }

    @Test
    public void test03(){
        System.out.println(Data.class.getSimpleName());
    }

}