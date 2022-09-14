package build.bus.meta;

import build.builder.data.BuildResult;
import build.builder.meta.BuildCoder;
import build.bus.exception.BuildBusException;
import build.source.meta.BuildSource;
import build.response.meta.BuilderResponse;
import build.source.resolver.meta.BuildSourceResolver;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * 构建总线
 *
 * @author peng_fu_lin
 * 2022-09-02 15:58
 */
public abstract class BuildBus {

    private final List<BuildCoder<?>> buildCoders;
    private final List<BuilderResponse> builderResponses;
    private final List<BuildSourceResolver<?>> buildSourceResolvers;

    /**初始化构建总线
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public BuildBus(List< BuildCoder<?>> buildCoders, List<BuilderResponse> builderResponses,
                    List< BuildSourceResolver<?>> buildSourceResolvers){
        this.buildCoders = buildCoders;
        this.builderResponses =builderResponses;
        this.buildSourceResolvers=buildSourceResolvers;
    }

    /**构建总线方法:适用单体构建
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public final void build(Class<? extends BuildCoder<?>> builderClass,
                            BuildSource buildSource, OutputStream outputStream) throws BuildBusException {
        //获取构建器
        BuildCoder<?> buildCoder = getBuildCoder(builderClass);
        //执行构建
        build(buildCoder,buildSource,outputStream);
    }

    /**构建总线方法：自定义选择构建器
     * 2022/9/7 0007-11:32
     * @author pengfulin
    */
    public final void build(BuildCoder<?> buildCoder, BuildSource buildSource, OutputStream outputStream) throws BuildBusException {
        try {
            //获取解析器
            BuildSourceResolver<?> buildSourceResolver = getBuildSourceResolver(buildSource,buildCoder.supportedBuildSource());
            //解析构建模型
            Object buildDataModel = buildSourceResolver.resolve(buildSource);
            //执行构建
            List<BuildResult> buildResults;
            if (buildDataModel instanceof List){
                buildResults=new LinkedList<>();
                for (Object resolverObj : (List<?>) buildDataModel) {
                    buildResults.add(buildCoder.buildCode(resolverObj));
                }
            }else
                buildResults= Collections.singletonList(buildCoder.buildCode(buildDataModel));
            //获取构建处理器
            BuilderResponse builderResponse = getBuilderResponse(outputStream);
            //响应构建结果
            builderResponse.buildResponse(outputStream,buildResults);
        } catch (Exception e) {
            throw new BuildBusException("The buildBus run exception",e);
        }
    }


    /**获取指定的构建器
     * 2022/9/2 0002-16:45
     * @author pengfulin
    */
    protected BuildCoder<?> getBuildCoder(Class<? extends BuildCoder<?>> builderClass) throws BuildBusException{
        for (BuildCoder<?> buildCoder : this.buildCoders) {
            if (builderClass == buildCoder.getClass())
                return buildCoder;
        }
        throw new BuildBusException("The buildCoder was not found："+builderClass.getName());
    }

    /**获取适配的构建响应器
     * 2022/9/2 0002-17:25
     * @author pengfulin
    */
    protected BuilderResponse getBuilderResponse(OutputStream outputStream) throws BuildBusException{
        for (BuilderResponse builderResponse : builderResponses) {
            if(builderResponse.isSupported(outputStream))
                return builderResponse;
        }
        throw new BuildBusException("The outputStream is not supported by these responder："+outputStream.getClass().getName());
    }

    /**获取适配的构建源解析器
     * 2022/9/6 0006-17:27
     * @author pengfulin
    */
    protected BuildSourceResolver<?> getBuildSourceResolver(BuildSource buildSource,Class<?> resolverType)  throws BuildBusException{
        for (BuildSourceResolver<?> buildSourceResolver : buildSourceResolvers) {
            if(buildSourceResolver.isSupported(buildSource,resolverType))
                return buildSourceResolver;
        }
        throw new BuildBusException("The buildSource is not supported by these parser："+buildSource.getClass().getName());
    }
}