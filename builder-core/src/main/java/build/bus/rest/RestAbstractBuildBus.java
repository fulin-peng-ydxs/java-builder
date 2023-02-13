package build.bus.rest;

import build.builder.meta.BuildCoder;
import build.bus.exception.BuildBusException;
import build.bus.meta.SchemaBuildBus;
import build.response.meta.BuildResponder;
import build.source.meta.BuildSource;
import build.source.resolver.meta.BuildSourceResolver;
import java.io.OutputStream;
import java.util.List;
/**
 * Rest模式构建总线
 *
 * @author peng_fu_lin
 * 2022-09-07 16:58
 */
public abstract class RestAbstractBuildBus extends SchemaBuildBus {

    public RestAbstractBuildBus(List<BuildCoder<?>> buildCoders, List<BuildResponder> buildResponders, List<BuildSourceResolver<?>> buildSourceResolvers) {
        super(buildCoders, buildResponders, buildSourceResolvers);
    }

    @Override
    public void buildWithChoose(List<Class<BuildCoder<?>>> builderClasses, BuildSource buildSource, OutputStream outputStream, List<Object> persistSources) throws BuildBusException {

    }

    @Override
    public void buildWithCustomer(List<BuildCoder<?>> builders, BuildSource buildSource, OutputStream outputStream, List<Object> persistSources) throws BuildBusException {

    }

    @Override
    public void build(BuildSource buildSource, OutputStream outputStream, List<Object> persistSources) throws BuildBusException {

    }
}