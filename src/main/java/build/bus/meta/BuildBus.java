package build.bus.meta;

import build.builder.meta.BuildResult;
import build.builder.meta.BuildCoder;
import build.source.meta.source.BuildSource;
import build.response.meta.BuilderResponse;
import java.io.OutputStream;
import java.util.List;
/**
 * 构建总线
 *
 * @author peng_fu_lin
 * 2022-09-02 15:58
 */
public class BuildBus {

    private final List<BuildCoder> buildCoders;
    private final List<BuilderResponse> builderResponses;
    private final BuildSource builderDataSource;

    /**初始化构建总线
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public BuildBus(BuildSource builderDataSource,
                    List<BuildCoder> buildCoders, List<BuilderResponse> builderResponses){
        this.buildCoders = buildCoders;
        this.builderResponses =builderResponses;
        this.builderDataSource=builderDataSource;
    }

    /**构建总线方法
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public final void build(Class<? extends BuildCoder> builderClass,
                            BuildSource builderDataSource, OutputStream outputStream) throws BuildBusException {
        try {
            if(builderDataSource==null)
                builderDataSource=this.builderDataSource;
            //获取构建器
            BuildCoder buildCoder = getBuildCoder(builderClass,builderDataSource);
            //执行构建
            BuildResult buildResult = buildCoder.buildCode(builderDataSource);
            //获取构建处理器
            BuilderResponse builderResponse = getBuilderResponse(outputStream, buildResult);
            if(outputStream==null)
                builderResponse.buildResponse(buildResult);
            else
                builderResponse.buildResponse(buildResult,outputStream);
        } catch (Exception e) {
            throw new BuildBusException("Build run exception.",e);
        }
    }

    /**获取适配的构建器
     * 2022/9/2 0002-16:45
     * @author pengfulin
    */
    protected BuildCoder getBuildCoder(Class<? extends BuildCoder> builderClass, BuildSource buildSource) throws BuildBusException{
        for (BuildCoder buildCoder : this.buildCoders) {
            boolean isFind = builderClass == buildCoder.getClass();
//            if(isFind && builderCoder.isSupported(builderDataSource))
                return buildCoder;
        }
        throw new BuildBusException("The buildCoder was not found or is not supported："+builderClass.getName());
    }

    /**获取适配的构建响应器
     * 2022/9/2 0002-17:25
     * @author pengfulin
    */
    protected BuilderResponse getBuilderResponse( OutputStream outputStream,BuildResult buildResult) throws BuildBusException{
        for (BuilderResponse builderResponse : builderResponses) {
            if(builderResponse.isSupported(outputStream,buildResult))
                return builderResponse;
        }
        throw new BuildBusException("The outputStream is not supported by these responder："+outputStream.getClass().getName());
    }
}
