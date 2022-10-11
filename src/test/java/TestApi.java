import build.builder.data.BuildModel;
import org.junit.Test;

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
    }

    @Test
    public void test02() {
        System.out.println(BuildModel.class.getResource("").getFile());
    }
}