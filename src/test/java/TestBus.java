import build.bus.SimpleBuildBus;
import build.bus.source.BuildBusException;
import org.junit.Test;

/**
 * 构建总线测试
 *
 * @author peng_fu_lin
 * 2022-09-05 11:19
 */
public class TestBus {

    @Test
    public void testBus() throws BuildBusException {
        SimpleBuildBus simpleBuildBus = new SimpleBuildBus(null,null,null);
        simpleBuildBus.build(null,null,null);
    }
}