package build.bus.rest;

import build.builder.meta.BuildCoder;
import build.response.meta.BuildResponder;
import build.source.resolver.meta.BuildSourceResolver;

import java.util.List;

/**
 * Rest-Mybatis模式构建总线
 *
 * @author peng_fu_lin
 * 2023-02-13 14:47
 */
public class RestMybatisBuildBus extends RestAbstractBuildBus{

    public RestMybatisBuildBus(List<BuildCoder<?>> buildCoders, List<BuildResponder> buildResponders, List<BuildSourceResolver<?>> buildSourceResolvers) {
        super(buildCoders, buildResponders, buildSourceResolvers);
    }
}