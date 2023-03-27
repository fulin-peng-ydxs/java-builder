package build.bus.meta;

import build.builder.meta.BuildCoder;
import build.bus.exception.BuildBusException;
import build.response.meta.BuildResponder;
import build.source.meta.BuildSource;
import build.source.resolver.meta.BuildSourceResolver;
import java.io.OutputStream;
import java.util.List;
/**
 * 模式构建总线
 *
 * @author peng_fu_lin
 * 2022-09-09 09:38
 */
public abstract class SchemaBuildBus extends BuildBus {

    /**
     * 初始化构建总线
     * 2022/9/2 0002-16:14
     * @author pengfulin
     */
    public SchemaBuildBus(List<BuildCoder<?>> buildCoders,List<BuildResponder> buildResponders, List<BuildSourceResolver<?>> buildSourceResolvers) {
        super(buildCoders,buildResponders, buildSourceResolvers);
    }

    /**构建总线方法：模式化构建
     * 2022/9/6 0006-16:47
     * @author pengfulin
     */
    public abstract void buildWithChoose(List<Class<BuildCoder<?>> > builderClasses,
                               BuildSource buildSource, OutputStream outputStream,List<Object> persistSources) throws BuildBusException;

    public abstract void buildWithCustomer(List<BuildCoder<?>> builders,
                               BuildSource buildSource, OutputStream outputStream,List<Object> persistSources) throws BuildBusException;

    public abstract void build(BuildSource buildSource, OutputStream outputStream, List<Object> persistSources) throws BuildBusException;
}