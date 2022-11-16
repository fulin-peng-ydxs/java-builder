package build.builder.meta.codes;

import build.builder.data.BuildResult;
import build.builder.meta.BuildCoder;
import build.builder.meta.codes.persist.PersistResolver;
import build.builder.meta.exception.BuilderException;
import build.builder.util.StringBuildUtil;
import java.nio.charset.StandardCharsets;
/**
 * 代码构建器
 *
 * @author peng_fu_lin
 * 2022-09-07 14:10
 */
public abstract class CodeBuilder<T> extends BuildCoder<T> implements PersistResolver<T> {

    /**代码构建风格*/
    protected CodeBuildStyle codeBuildStyle=new CodeBuildStyle();

    @Override
    public final BuildResult buildCode(Object buildDataModel,Object persistDataModel) throws BuilderException {
        try {
            //获取构建模型
            T buildModel=convertBuildModel(buildDataModel,persistDataModel);
            //执行构建
            return doBuildResult(buildModel);
        } catch (Exception e) {
            throw new BuilderException("The codeBuilder run exception",e);
        }
    }



    /**将数据模型转化为构建模型
     * 2022/9/23 0023-11:19
     * @author pengfulin
     */
    protected final T convertBuildModel(Object buildDataModel,Object persistDataModel) throws BuilderException {
        try {
            T buildModel = getBuildModel(buildDataModel);
            if(persistDataModel==null)
                return buildModel;
            return mergePersistBuildModel(buildModel,resolvePersistSource(persistDataModel));
        } catch (Exception e) {
             throw new BuilderException("The data model transformation failed",e);
        }
    }

    /**将构建模型合并成持续集成构建模型
     * 2022/9/23 0023-14:43
     * @author pengfulin
     */
    protected T mergePersistBuildModel(T newBuildModel,T oldBuildModel) {
        throw new RuntimeException("An unsupported method");
    }






    /**将构建模型转化为代码
     * 2022/9/7 0007-14:26
     * @author pengfulin
     */
    protected abstract String convertCode(T model);

    /**将构建代码转化为构建字节结果集
     * 2022/9/7 0007-11:12
     * @author pengfulin
     */
    protected  byte[] convertBuildBytes(String code){
        return code.getBytes(StandardCharsets.UTF_8);
    }

    /**生成构建结果对象
     * 2022/9/8 0008-16:59
     * @author pengfulin
     */
    protected abstract BuildResult convertBuildResult(T model,byte[] bytes);

    /**执行构建
     * 2022/9/23 0023-15:35
     * @author pengfulin
     */
    protected BuildResult doBuildResult(T buildModel){
        //将构建模型转换成代码
        String code = convertCode(buildModel);
        //将代码转换成构建字节结果集
        byte[] bytes = convertBuildBytes(code);
        //生成构建结果对象
        return convertBuildResult(buildModel,bytes);
    }


    public static class CodeBuildStyle{
        //代码间的间隙
        public int space=1;
        //代码模块间前间行
        public int modelPrefixLine=2;
        //代码模块间后间行
        public int modelAfterLine=2;
        //代码结构区域外的缩进
        public int structureExternalSpace=4;
        //代码结构区域内的缩进
        public int structureInternalSpace=8;
        //代码结构间前间行
        public int structurePrefixLine=1;
        //代码结构间后间行
        public int structureSuffixLine=1;
    }

    /**间行风格实现
     * 2022/9/8 0008-17:27
     * @author pengfulin
     */
    protected String clearanceLineStyle(int num){
        return  StringBuildUtil.cycleAppend("\n",num);
    }
    
    /**缩进风格实现
     * 2022/9/8 0008-17:38
     * @author pengfulin
     */
    protected String indentationStyle(int num){
        return StringBuildUtil.cycleAppend(" ",num);
    }
}