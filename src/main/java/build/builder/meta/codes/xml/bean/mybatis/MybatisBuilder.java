package build.builder.meta.codes.xml.bean.mybatis;

import build.builder.data.BuildResult;
import build.builder.data.xmls.bean.mybatis.MybatisBeanModel;
import build.builder.data.xmls.meta.XmlElement;
import build.builder.meta.codes.xml.XmlCodeBuilder;
import build.builder.util.TemplateCacheUtil;
import build.source.data.meta.business.mybatis.MybatisBean;
import java.io.IOException;
import java.util.List;
/**
 * Mybatis实体构建器
 *
 * @author peng_fu_lin
 * 2022-10-09 14:02
 */
public abstract class MybatisBuilder extends XmlCodeBuilder<MybatisBeanModel> {

    @Override
    public Class<?> supportedBuildSource() {
        return MybatisBean.class;
    }

    @Override
    protected MybatisBeanModel getBuildModel(Object buildDataModel) {
        if(buildDataModel instanceof MybatisBean)
            return doGetMybatisBeanModel((MybatisBean) buildDataModel);
        return null;
    }

    /**生成MybatisBean模型
     * 2022/10/9 0009-16:27
     * @author pengfulin
     */
    protected  MybatisBeanModel doGetMybatisBeanModel(MybatisBean mybatisBean){
        MybatisBeanModel mybatisBeanModel = new MybatisBeanModel();
        //获取命名空间
        mybatisBeanModel.setNameSpace(getNameSpace(mybatisBean));
        //获取映射名
        mybatisBeanModel.setMappingName(getMappingName(mybatisBean));
        //获取sql元素
        mybatisBeanModel.setSqlXmlElements(getSqlElements(mybatisBean));
        //获取基础元素
        mybatisBeanModel.setBasicXmlElements(getBasicSqlElements(mybatisBean));
        //获取其他元素
        mybatisBeanModel.setOtherXmlElements(getOtherElements(mybatisBean));
        return mybatisBeanModel;
    }

    /**获取命名空间
     * 2022/10/9 0009-17:57
     * @author pengfulin
     */
    protected String getNameSpace(MybatisBean mybatisBean){
        return mybatisBean.getBeanInfo().getName();
    }

    /**获取映射名
     * 2022/10/9 0009-18:05
     * @author pengfulin
     */
    protected String getMappingName(MybatisBean mybatisBuildBea){
        return mybatisBuildBea.getBeanInfo().getSimpleName()+"Mapper";
    }

    /**获取sql元素模型
     * 2022/10/10 0010-11:54
     * @author pengfulin
    */
    protected abstract List<XmlElement> getSqlElements(MybatisBean mybatisBean);
    /**获取基础元素模型
     * 2022/10/10 0010-11:58
     * @author pengfulin
    */
    protected abstract List<XmlElement> getBasicSqlElements(MybatisBean mybatisBean);

    /**获取其他元素模型
     * 2022/10/10 0010-11:58
     * @author pengfulin
    */
    protected  List<XmlElement> getOtherElements(MybatisBean mybatisBean){return null;}

    @Override
    protected BuildResult convertBuildResult(MybatisBeanModel model, byte[] bytes) {
        BuildResult buildResult = super.convertBuildResult(model, bytes);
        buildResult.setBuildName(String.format(buildResult.getBuildName(),model.getMappingName()));
        buildResult.setBuildTarget(model.getTarget());
        return buildResult;
    }

    @Override
    protected String convertCode(MybatisBeanModel model) {
        String buildResult = null;
        try {
            buildResult = TemplateCacheUtil.getTemplate("classpath:/mybatis/MybatisTemplate.xml").replace("${nameSpace}", model.getNameSpace());
        } catch (IOException e) {
            throw new RuntimeException("Template acquisition Exception",e);
        }
        String sqlElement = doGetModelElements(model.getSqlXmlElements()) + elementModelClearanceLineStyle();
        String basicElement = doGetModelElements(model.getBasicXmlElements()) + elementModelClearanceLineStyle();
        String otherElement = doGetModelElements(model.getOtherXmlElements()) + elementModelClearanceLineStyle();
        buildResult=buildResult.replace("${sqlValue}",sqlElement)
                .replace("${basicValue}",basicElement).replace("${otherValue}",otherElement);
        return buildResult;
    }

    /**生成模型元素
     * 2022/10/9 0009-17:19
     * @author pengfulin
    */
    protected String doGetModelElements(List<XmlElement> elements){
        StringBuilder builder = new StringBuilder();
        elements.forEach(value->{
            builder.append(doGetElement(value)).append(elementClearanceLineStyle());
        });
        return builder.toString();
    }
}