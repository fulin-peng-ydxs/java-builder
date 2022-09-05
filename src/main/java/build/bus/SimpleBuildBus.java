package build.bus;

import build.builder.source.BuilderCoder;
import build.bus.source.BuildBus;
import build.datasource.source.BuildDataSource;
import build.response.source.BuilderResponse;

import java.util.List;

/**
 * 简单的总线构建器
 *
 * @author peng_fu_lin
 * 2022-09-05 11:20
 */
public class SimpleBuildBus extends BuildBus {

    public SimpleBuildBus(BuildDataSource builderDataSource, List<BuilderCoder> builderCoders, List<BuilderResponse> builderResponses) {
        super(builderDataSource, builderCoders, builderResponses);
    }
}