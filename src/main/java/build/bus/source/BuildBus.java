package build.bus.source;

import build.builder.source.BuildResult;
import build.builder.source.BuilderCoder;
import build.datasource.source.BuildDataSource;
import build.response.source.BuilderResponse;
import java.io.OutputStream;
import java.util.List;
/**
 * 构建总线
 *
 * @author peng_fu_lin
 * 2022-09-02 15:58
 */
public abstract class BuildBus {

    private final List<BuilderCoder> builderCoders;
    private final List<BuilderResponse> builderResponses;
    private final BuildDataSource builderDataSource;

    /**初始化构建总线
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public BuildBus(BuildDataSource builderDataSource,
                      List<BuilderCoder> builderCoders, List<BuilderResponse> builderResponses){
        this.builderCoders = builderCoders;
        this.builderResponses =builderResponses;
        this.builderDataSource=builderDataSource;
    }

    /**构建总线方法
     * 2022/9/2 0002-16:14
     * @author pengfulin
     * @param builderClass 构建器
     * @param builderDataSource 构建数据源
     * @param outputStream 构建响应流
    */
    public final void build(Class<? extends BuilderCoder> builderClass,
                            BuildDataSource builderDataSource, OutputStream outputStream) throws BuildBusException {
        try {
            if(builderDataSource==null)
                builderDataSource=this.builderDataSource;
            //获取构建器
            BuilderCoder builderCoder = getBuilderCoder(builderClass,builderDataSource);
            //执行构建
            BuildResult buildResult = builderCoder.buildCode(builderDataSource);
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
     * @param builderClass 构建器类型
     * @param builderDataSource 构建数据源
    */
    protected BuilderCoder getBuilderCoder(Class<? extends BuilderCoder> builderClass, BuildDataSource builderDataSource) throws BuildBusException{
        for (BuilderCoder builderCoder : this.builderCoders) {
            boolean isFind = builderClass == builderCoder.getClass();
            if(isFind && builderCoder.isSupported(builderDataSource))
                return builderCoder;
        }
        throw new BuildBusException("The builderCoder was not found or is not supported："+builderClass.getName());
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
