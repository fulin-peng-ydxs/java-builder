package build.builder.meta.codes.xml.mybatis;

import build.builder.data.busness.RestMethod;
import build.builder.data.xmls.meta.XmlElement;
import build.builder.util.CollectionUtil;
import build.source.data.business.rest.MybatisInfo;
import java.util.*;
/**
 * 简单的Mybatis实体构建器
 *
 * @author peng_fu_lin
 * 2022-10-09 17:36
 */
public class SimpleMybatisBuilder extends MybatisBuilder {

    /**赋值模板*/
    protected String eqTemplate="and %s=#{%s}";
    protected String valuationTemplate="%s=#{%s},";

    /**条件判断模板*/
    protected String ifTemplate="<if test=\"%s!=null\">\n%s\n</if>";

    @Override
    protected List<XmlElement> getSqlElements(MybatisInfo mybatisInfo) {
        List<XmlElement> xmlElements = new LinkedList<>();
        xmlElements.addAll(doGetSqlColumnElements(mybatisInfo));
        xmlElements.addAll(doGetSqlConditionElements(mybatisInfo));
        xmlElements.addAll(doGetSqlUpdateElements(mybatisInfo));
        xmlElements.addAll(doGetSqlResultElements(mybatisInfo));
        return xmlElements;
    }

    /**
     * 生成列元素
     * 2022/10/10 0010-16:43
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlColumnElements(MybatisInfo mybatisInfo) {
        List<XmlElement> xmlElements = new LinkedList<>();
        Map<String, String> entityFields = mybatisInfo.getEntityFields();
        //查询列元素
        xmlElements.add(doGetSelectColumnElement(entityFields));
        //插入列元素
        xmlElements.add(doGetInsertColumnElement(entityFields));
        xmlElements.addAll(doGetInsertValueColumn(entityFields));
        return xmlElements;
    }

    /**生成select_column元素
     * 2022/10/12 0012-10:17
     * @author pengfulin
    */
    protected XmlElement doGetSelectColumnElement(Map<String, String> entityFields){
        String template="%s,";
        XmlElement element = getObjectSqlElement();
        element.setId("select_column");
        StringBuilder builder = new StringBuilder();
        entityFields.forEach((key,value)->{
            builder.append(elementInternalIndentation())
                    .append(String.format(template,key)).append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return element;
    }

    /**生成insert_column元素
     * 2022/10/12 0012-10:28
     * @author pengfulin
    */
    protected XmlElement doGetInsertColumnElement( Map<String, String> entityFields){
        String template="%s,";
        XmlElement element = getObjectSqlElement();
        element.setId("insert_column");
        StringBuilder builder = new StringBuilder();
        builder.append(elementInternalIndentation()).append("(").append(elementClearanceLineStyle());
        entityFields.keySet().forEach((value)->{
            builder.append(elementInternalIndentation())
                    .append(elementExternalIndentation()).append(String.format(template,value)).append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return element;
    }
    
    /**生成insert_value元素
     * 2022/10/12 0012-10:36
     * @author pengfulin
    */
    protected List<XmlElement> doGetInsertValueColumn(Map<String, String> entityFields){
        return CollectionUtil.asList(doGetSingleInsertValueColumn(entityFields)
        ,doGetBatchInsertValueColumn(entityFields));
    }

    /**生成insert_value单个元素
     * 2022/10/13 0013-12:00
     * @author pengfulin
    */
    protected XmlElement doGetSingleInsertValueColumn(Map<String, String> entityFields){
        return doGetInsertValueColumnTemplate("#{%s},", entityFields);
    }
    
    /**生成insert_value批量元素
     * 2022/10/13 0013-12:02
     * @author pengfulin
    */
    protected XmlElement doGetBatchInsertValueColumn(Map<String, String> entityFields){
        return doGetInsertValueColumnTemplate("#{obj.%s},",entityFields);
    }
    
    /**生成insert_value元素模板
     * 2022/10/13 0013-11:53
     * @author pengfulin
    */
    protected XmlElement doGetInsertValueColumnTemplate(String valuationTemplate,Map<String, String> entityFields){
        XmlElement element = getObjectSqlElement();
        element.setId("batch_insert_value");
        StringBuilder builder = new StringBuilder();
        builder.append(elementInternalIndentation()).append("(").append(elementClearanceLineStyle());
        entityFields.values().forEach((value)->{
            builder.append(elementInternalIndentation())
                    .append(elementExternalIndentation()).append(String.format(valuationTemplate,value)).append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return element;
    }

    /**
     * 生成条件元素
     * 2022/10/11 0011-14:36
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlConditionElements(MybatisInfo mybatisInfo) {
        XmlElement element = getObjectSqlElement();
        element.setId("where_column");
        StringBuilder builder = new StringBuilder();
        mybatisInfo.getEntityFields().forEach((key, value)->{
            builder.append(elementInternalIndentation())
                    .append(String.format(ifTemplate,value,String.format(
                            eqTemplate,elementInternalIndentation()+elementExternalIndentation()+key,value))).append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return Collections.singletonList(element);
    }

    /**
     * 生成更新元素
     * 2022/10/11 0011-14:43
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlUpdateElements(MybatisInfo mybatisInfo) {
        XmlElement element = getObjectSqlElement();
        element.setId("update_column");
        StringBuilder builder = new StringBuilder();
        mybatisInfo.getEntityFields().forEach((key, value)->{
            if (!key.equals(mybatisInfo.getBuildBeanInfo().getPrimaryKey()))
                builder.append(elementInternalIndentation())
                        .append(String.format(valuationTemplate,elementInternalIndentation()+elementExternalIndentation()+key,value))
                        .append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return Collections.singletonList(element);
    }

    /**
     * 生成结果集元素
     * 2022/10/11 0011-14:39
     *
     * @author pengfulin
     */
    protected List<XmlElement> doGetSqlResultElements(MybatisInfo mybatisInfo) {
        String idTemplate="<id property=\"%s\" column=\"%s\"/>";
        String resultTemplate="<result property=\"%s\" column=\"%s\"/>";
        XmlElement element = getObjectSqlElement();
        element.setId("result_mapper");
        element.setAttributes(Collections.singletonMap("type", mybatisInfo.getBeanInfo().getName()));
        StringBuilder builder = new StringBuilder();
        mybatisInfo.getEntityFields().forEach((key, value)->{
            builder.append(elementInternalIndentation());
            if(key.equals(mybatisInfo.getBuildBeanInfo().getPrimaryKey())){
                builder.append(String.format(idTemplate,value,key));
            }else{
                builder.append(String.format(resultTemplate, value,key));
            }
            builder.append(elementClearanceLineStyle());
        });
        element.setContent(builder.toString());
        return Collections.singletonList(element);
    }



    @Override
    protected List<XmlElement> getBasicSqlElements(MybatisInfo mybatisInfo) {
        List<XmlElement> xmlElements = new LinkedList<>();
        //基础新增元素
        xmlElements.addAll(doGetInsertElement(mybatisInfo));
        //基础删除元素
        xmlElements.addAll(doGetDeleteElement(mybatisInfo));
        //基础更新元素
        xmlElements.addAll(doGetUpdateElement(mybatisInfo));
        //基础查询元素
        xmlElements.addAll(doGetSelectElement(mybatisInfo));
        return xmlElements;
    }

    /**生成新增元素
     * 2022/10/13 0013-09:40
     * @author pengfulin
    */
    protected List<XmlElement> doGetInsertElement(MybatisInfo mybatisInfo){
        return CollectionUtil.asList(doGetSingletonInsertElement(mybatisInfo),
                doGetBatchInsertElement(mybatisInfo));
    }

    /**生成单个新增元素
     * 2022/10/13 0013-13:43
     * @author pengfulin
    */
    protected XmlElement doGetSingletonInsertElement(MybatisInfo mybatisInfo){
        XmlElement element = getObjectElement("insert");
        element.setId(RestMethod.ADD.name());
        String builder = elementInternalIndentation() +
                "insert into " + mybatisInfo.getBuildBeanInfo().getName() +
                elementClearanceLineStyle() + elementInternalIndentation() +
                "<include refid=\"insert_column\"/>" +
                elementClearanceLineStyle() + elementInternalIndentation() +
                "values <include refid=\"insert_value\"/>"+elementClearanceLineStyle();
        element.setContent(builder);
        return element;
    }

    /**生成批量新增元素
     * 2022/10/13 0013-13:44
     * @author pengfulin
    */
    protected XmlElement doGetBatchInsertElement(MybatisInfo mybatisInfo){
        XmlElement element =  getObjectElement("insert");
        element.setId(RestMethod.ADD_BATCH.name());
        String builder = elementInternalIndentation() +
                "insert into " + mybatisInfo.getBuildBeanInfo().getName() +
                elementClearanceLineStyle() + elementInternalIndentation() +
                "<include refid=\"insert_column\"/>" +
                elementClearanceLineStyle() + elementInternalIndentation() +
                "values"+ elementClearanceLineStyle() + elementInternalIndentation()+
                "<foreach collection=\"list\" item=\"obj\" separator=\",\">"+
                elementClearanceLineStyle() + elementInternalIndentation() +
                elementExternalIndentation()+"<include refid=\"batch_insert_value\"/>"+
                elementClearanceLineStyle() + elementInternalIndentation()
                +"</foreach>"+elementClearanceLineStyle();
        element.setContent(builder);
        return element;
    }

    /**生成删除元素
     * 2022/10/13 0013-09:41
     * @author pengfulin
    */
    protected List<XmlElement> doGetDeleteElement(MybatisInfo mybatisInfo){
        return CollectionUtil.asList(doGetSingleDeleteElement(mybatisInfo),
                doGetBatchDeleteElement(mybatisInfo));
    }

    /**生成删除单个元素
     * 2022/10/13 0013-14:14
     * @author pengfulin
    */
    protected XmlElement doGetSingleDeleteElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("delete");
        element.setId(RestMethod.DELETE.name());
        String builder = elementInternalIndentation() + "delete from " + buildBeanInfo.getName() + elementClearanceLineStyle() +
                elementInternalIndentation() +"where " + String.format(valuationTemplate,
                buildBeanInfo.getName(), mybatisInfo.getEntityFields().get(buildBeanInfo.getPrimaryKey()));
        element.setContent(builder);
        return element;
    }

    /**生成批量删除元素
     * 2022/10/13 0013-14:17
     * @author pengfulin
    */
    protected XmlElement doGetBatchDeleteElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("delete");
        element.setId(RestMethod.DELETE_BATCH.name());
        String builder = elementInternalIndentation() + "delete from " + buildBeanInfo.getName() + elementClearanceLineStyle()+
                elementInternalIndentation()+"<where>"+elementClearanceLineStyle()+"<include refid=\"where_column\"/>"+ elementClearanceLineStyle()+
                elementInternalIndentation()+"</where>"+elementClearanceLineStyle();
        element.setContent(builder);
        return element;
    }

    /**生成更新元素
     * 2022/10/13 0013-09:41
     * @author pengfulin
    */
    protected List<XmlElement> doGetUpdateElement(MybatisInfo mybatisInfo){
        return CollectionUtil.asList(doGetSingleUpdateElement(mybatisInfo),
                doGetBatchUpdateElement(mybatisInfo));
    }

    /**生成更新单个元素
     * 2022/10/14 0014-09:39
     * @author pengfulin
    */
    protected XmlElement doGetSingleUpdateElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("update");
        element.setId(RestMethod.UPDATE.name());
        String builder = elementInternalIndentation() + "update " + buildBeanInfo.getName() + elementClearanceLineStyle() +
                elementInternalIndentation()+"<set>"+elementClearanceLineStyle()+
                elementInternalIndentation()+elementExternalIndentation()+"<include refid=\"update_column\"/>"+elementClearanceLineStyle()+
                elementInternalIndentation()+"</set>"+elementClearanceLineStyle()+
                elementInternalIndentation() +"where " + String.format(valuationTemplate,
                buildBeanInfo.getName(), mybatisInfo.getEntityFields().get(buildBeanInfo.getPrimaryKey()));
        element.setContent(builder);
        return element;
    }

    /**生成更新批量元素
     * 2022/10/14 0014-09:42
     * @author pengfulin
    */
    protected XmlElement doGetBatchUpdateElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("update");
        element.setId(RestMethod.UPDATE_BATCH.name());
        String builder = elementInternalIndentation() + "update " + buildBeanInfo.getName() + elementClearanceLineStyle() +
                elementInternalIndentation()+"<set>"+elementClearanceLineStyle()+
                elementInternalIndentation()+elementExternalIndentation()+"<include refid=\"update_column\"/>"+elementClearanceLineStyle()+
                elementInternalIndentation()+"</set>"+elementClearanceLineStyle()+
                elementInternalIndentation()+"<where>"+elementClearanceLineStyle()+"<include refid=\"where_column\"/>"+ elementClearanceLineStyle()+
                elementInternalIndentation()+"</where>"+elementClearanceLineStyle();
        element.setContent(builder);
        return element;
    }

    /**生成查询元素
     * 2022/10/13 0013-09:42
     * @author pengfulin
    */
    protected List<XmlElement> doGetSelectElement(MybatisInfo mybatisInfo){
        return CollectionUtil.asList(doGetSingleSelectElement(mybatisInfo),
                doGetBatchSelectElement(mybatisInfo));
    }

    /**生成查询单个元素
     * 2022/10/14 0014-09:56
     * @author pengfulin
    */
    protected XmlElement doGetSingleSelectElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("select");
        element.setAttributes(Collections.singletonMap("resultMap","result_mapper"));
        element.setId(RestMethod.DETAIL.name());
        String builder = elementInternalIndentation() + "select " +"<include refid=\"select_column\"/>" + elementClearanceLineStyle() +
                elementInternalIndentation()+"from "+buildBeanInfo.getName()+elementClearanceLineStyle()+
                elementInternalIndentation() +"where " + String.format("%s=#{%s}",
                buildBeanInfo.getName(), mybatisInfo.getEntityFields().get(buildBeanInfo.getPrimaryKey()));
        element.setContent(builder);
        return element;
    }

    /**生成查询批量元素
     * 2022/10/14 0014-10:12
     * @author pengfulin
    */
    protected XmlElement doGetBatchSelectElement(MybatisInfo mybatisInfo){
        MybatisInfo.BuildBeanInfo buildBeanInfo = mybatisInfo.getBuildBeanInfo();
        XmlElement element = getObjectElement("select");
        element.setId(RestMethod.FIND.name());
        element.setAttributes(Collections.singletonMap("resultMap","result_mapper"));
        String builder = elementInternalIndentation() + "select " +"<include refid=\"select_column\"/>" + elementClearanceLineStyle() +
                elementInternalIndentation()+"from "+buildBeanInfo.getName()+elementClearanceLineStyle()+
                elementInternalIndentation()+"<where>"+elementClearanceLineStyle()+"<include refid=\"where_column\"/>"+ elementClearanceLineStyle()+
                elementInternalIndentation()+"</where>"+elementClearanceLineStyle();
        element.setContent(builder);
        return element;
    }

    private XmlElement getObjectElement(String elementName){
        XmlElement element = new XmlElement();
        element.setName(elementName);
        return element;
    }

    private XmlElement getObjectSqlElement(){
        return getObjectElement("sql");
    }
}
