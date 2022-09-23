package build.bus.meta;

import build.builder.data.BuildResult;
import build.builder.meta.BuildCoder;
import build.builder.meta.codes.persist.PersistCodeBuilder;
import build.bus.exception.BuildBusException;
import build.response.meta.file.FileBuildResponder;
import build.source.meta.BuildSource;
import build.response.meta.BuildResponder;
import build.source.resolver.jdbc.JdbcBSRBuildBean;
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

    protected final List<BuildCoder<?>> buildCoders=new LinkedList<>();
    protected final List<BuildResponder> buildResponders=new LinkedList<>();
    protected final List<BuildSourceResolver<?>> buildSourceResolvers=new LinkedList<>();

    /**外部初始化构建总线
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public BuildBus(List< BuildCoder<?>> buildCoders, List<BuildResponder> buildResponders,
                    List< BuildSourceResolver<?>> buildSourceResolvers){
        this.buildCoders.addAll(buildCoders);
        this.buildResponders.addAll(buildResponders);
        this.buildSourceResolvers.addAll(buildSourceResolvers);
        init();
    }

    /**默认初始化构建总线
     * 2022/9/19 0019-12:05
     * @author pengfulin
    */
    public BuildBus(){
        init();
    }

    /**初始化默认的组件
     * 2022/9/19 0019-12:01
     * @author pengfulin
    */
    protected void init(){
        //初始化响应器
        buildResponders.add(new FileBuildResponder());
        //初始化解析器
        buildSourceResolvers.add(new JdbcBSRBuildBean());
    }

    /**构建总线方法:适用单体构建
     * 2022/9/2 0002-16:14
     * @author pengfulin
    */
    public final void build(Class<? extends BuildCoder<?>> builderClass,
                            BuildSource buildSource,Object persistSource,OutputStream outputStream) throws BuildBusException {
        //获取构建器
        BuildCoder<?> buildCoder = getBuildCoder(builderClass,persistSource);
        //执行构建
        build(buildCoder,buildSource,persistSource,outputStream);
    }

    /**构建总线方法：自定义选择构建器
     * 2022/9/7 0007-11:32
     * @author pengfulin
    */
    public final void build(BuildCoder<?> buildCoder, BuildSource buildSource,Object persistSource,OutputStream outputStream) throws BuildBusException {
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
                    buildResults.add(buildCoder.buildCode(resolverObj,persistSource));
                }
            }else
                buildResults= Collections.singletonList(buildCoder.buildCode(buildDataModel,persistSource));
            //获取构建处理器
            BuildResponder buildResponder = getBuilderResponse(outputStream);
            //响应构建结果
            buildResponder.buildResponse(outputStream,buildResults);
        } catch (Exception e) {
            throw new BuildBusException("The buildBus run exception",e);
        }
    }


    /**获取指定的构建器
     * 2022/9/2 0002-16:45
     * @author pengfulin
    */
    protected BuildCoder<?> getBuildCoder(Class<? extends BuildCoder<?>> builderClass,Object persistSource) throws BuildBusException{
        for (BuildCoder<?> buildCoder : this.buildCoders) {
            if (builderClass == buildCoder.getClass()){
                if(persistSource!=null &&buildCoder instanceof PersistCodeBuilder)
                  if(((PersistCodeBuilder<?>)buildCoder).analysable()!=persistSource.getClass())
                      continue;
                return buildCoder;
            }
        }
        throw new BuildBusException("The buildCoder was not found："+builderClass.getName());
    }

    /**获取适配的构建响应器
     * 2022/9/2 0002-17:25
     * @author pengfulin
    */
    protected BuildResponder getBuilderResponse(OutputStream outputStream) throws BuildBusException{
        for (BuildResponder buildResponder : buildResponders) {
            if(buildResponder.isSupported(outputStream))
                return buildResponder;
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