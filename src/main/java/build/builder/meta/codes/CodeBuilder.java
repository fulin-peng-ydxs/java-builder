package build.builder.meta.codes;

import build.builder.data.BuildResult;
import build.builder.meta.BuildCoder;
import build.builder.meta.exception.BuilderException;
import build.builder.util.StringUtil;
import java.nio.charset.StandardCharsets;
/**
 * 代码构建器
 *
 * @author peng_fu_lin
 * 2022-09-07 14:10
 */
public abstract class CodeBuilder<T> extends BuildCoder<T> {

    /**代码构建风格*/
    protected CodeBuildStyle codeBuildStyle=new CodeBuildStyle();

    @Override
    public final BuildResult buildCode(Object buildDataModel) throws BuilderException {
        try {
            //获取构建模型
            T buildModel = getBuildModel(buildDataModel);
            //将构建模型转换成代码
            String code = convertCode(buildModel);
            //将代码转换成构建字节结果集
            byte[] bytes = convertBuildBytes(code);
            //生成构建结果对象
            return convertBuildResult(buildModel,bytes);
        } catch (Exception e) {
            throw new BuilderException("The codeBuilder run exception",e);
        }
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
        return  StringUtil.cycleAppend("\n",num);
    }
    
    /**缩进风格实现
     * 2022/9/8 0008-17:38
     * @author pengfulin
     */
    protected String indentationStyle(int num){
        return StringUtil.cycleAppend(" ",num);
    }
}