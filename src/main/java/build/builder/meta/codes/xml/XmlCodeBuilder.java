package build.builder.meta.codes.xml;

import build.builder.data.BuildResult;
import build.builder.data.xmls.bean.mybatis.MybatisBeanModel;
import build.builder.data.xmls.meta.XmlElement;
import build.builder.meta.codes.CodeBuilder;
import build.builder.util.StringBuildUtil;
import java.util.Map;

/**
 * xml代码构建器
 *
 * @author peng_fu_lin
 * 2022-10-08 15:17
 */
public abstract class XmlCodeBuilder<T> extends CodeBuilder<T> {



    @Override
    protected BuildResult convertBuildResult(T model, byte[] bytes) {
        return new BuildResult("%s.xml",bytes,null);
    }

    /**生成元素
     * 2022/10/11 0011-15:35
     * @author pengfulin
    */
    protected String doGetElement(XmlElement element){
        StringBuilder builder = new StringBuilder();
        builder.append(elementExternalIndentation()).append("<").append(element.getName())
                .append(elementCodeSpaceStyle()).append("id=").append(element.getId());
        if(element.getAttributes()!=null)
            builder.append(elementCodeSpaceStyle()).append(doGetElementAttributes(element.getAttributes()));
        builder.append(">").append(elementClearanceLineStyle()).append(element.getContent()).append(elementExternalIndentation())
                .append("<").append(element.getName()).append("/>").append(elementClearanceLineStyle());
        return builder.toString();
    }

    /**生成元素属性
     * 2022/10/11 0011-15:42
     * @author pengfulin
    */
    protected String doGetElementAttributes(Map<String,String> attributes){
        StringBuilder builder = new StringBuilder();
        attributes.forEach((key,value)->{
            builder.append(key).append("=").append(value).append(elementCodeSpaceStyle());
        });
        return StringBuildUtil.clearChar(builder.toString(),' ', StringBuildUtil.ClearCharType.END,-1);
    }


    /**元素模块间行风格
     * 2022/10/9 0009-17:29
     * @author pengfulin
     */
    protected String elementModelClearanceLineStyle(){
        return clearanceLineStyle(codeBuildStyle.modelAfterLine);
    }
    
    /**元素外缩进
     * 2022/10/11 0011-15:09
     * @author pengfulin
    */
    protected String elementExternalIndentation(){ return  indentationStyle(codeBuildStyle.structureExternalSpace);}

    /**元素内缩进
     * 2022/10/11 0011-15:14
     * @author pengfulin
    */
    protected String elementInternalIndentation(){return indentationStyle(codeBuildStyle.structureInternalSpace);}

   /**元素代码间隙风格
    * 2022/10/11 0011-15:40
    * @author pengfulin
   */
    protected String elementCodeSpaceStyle(){
        return indentationStyle(codeBuildStyle.space);
    }

    /**元素间行风格
     * 2022/9/14 0014-15:52
     * @author pengfulin
     */
    protected String elementClearanceLineStyle(){
        return clearanceLineStyle(codeBuildStyle.structureSuffixLine);
    }
}