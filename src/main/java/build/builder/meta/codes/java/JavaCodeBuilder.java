package build.builder.meta.codes.java;

import build.builder.data.classes.enums.CommentType;
import build.builder.data.classes.enums.MethodType;
import build.builder.data.classes.meta.*;
import build.builder.meta.codes.CodeBuilder;
import build.builder.util.ClassUtil;
import build.builder.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Java代码构建
 *
 * @author peng_fu_lin
 * 2022-09-09 10:20
 */
@Getter
@Setter
public abstract class JavaCodeBuilder<T> extends CodeBuilder<T> {

    /**包声明配置*/
    protected String packageStatement;

    /**生成包声明
     * 2022/9/8 0008-15:29
     * @author pengfulin
     */
    protected String doGetPackageStatement(String packageStatement){
        String template="package %s\n";
        return String.format(template,packageStatement);
    }
    
    /**生成类导入
     * 2022/9/9 0009-10:26
     * @author pengfulin
    */
    protected String doGetClassImports(Set<Class<?>> classImports){
        String template="import %s; \n";
        StringBuilder builder = new StringBuilder();
        for (Class<?> classImport : classImports)
            builder.append(String.format(template, classImport.getName()));
        return builder.toString();
    }

    /**生成声明
     * 2022/9/9 0009-11:49
     * @author pengfulin
    */
    protected String doGetClassMetaStatement(ClassMetaStatement metaStatement){
        StringBuilder builder = new StringBuilder();
        //注释
        CommentMeta classComment = metaStatement.getClassComment();
        if(classComment!=null)
            builder.append(doGetComment(classComment,0));
        //权限
        builder.append(metaStatement.getClassPermission().value).append(codeSpaceStyle())
                //名称
                .append(metaStatement.getClassName()).append(codeSpaceStyle());
        ExtendsMeta classExtends = metaStatement.getClassExtends();
        //继承
        if(classExtends!=null)
            builder.append("extends").append(codeSpaceStyle())
                    .append(doGetExtends(classExtends)).append(codeSpaceStyle());
        //实现
        Map<Class<?>, ImplementsMeta> classImplements = metaStatement.getClassImplements();
        if(classImplements!=null){
            StringBuilder implBuilder = new StringBuilder();
            classImplements.forEach((key,value)->{
                implBuilder.append(doGetImpl(value))
                        .append(",");
            });
            builder.append(StringUtil.clearChar(implBuilder.toString(),',', StringUtil.ClearCharType.END,-1))
                    .append(codeSpaceStyle());
        }
        builder.append("{");
        return builder.toString();
    }

    /**生成类方法
     * 2022/9/14 0014-15:19
     * @author pengfulin
    */
    protected String doGetMethod(MethodMeta methodMeta){
        StringBuilder builder = new StringBuilder();
        //注释
        CommentMeta methodComment = methodMeta.getMethodComment();
        if(methodComment!=null)
            builder.append(doGetComment(methodComment, codeBuildStyle.structureExternalSpace));
        //权限
        builder.append(structureExternalIndentation())
                .append(methodMeta.getMethodPermission()).append(codeSpaceStyle());
        //类型
        builder.append(methodMeta.getMethodType())
                .append(codeSpaceStyle());
        //返回值
        Class<?> methodReturn = methodMeta.getMethodReturn();
        if(methodReturn==void.class)
            builder.append("void");
        else
            builder.append(methodReturn.getSimpleName());
        builder.append(codeSpaceStyle());
        //名称
        builder.append(methodMeta.getMethodName());
        //参数列表
        builder.append("(");
        List<ParamMeta> methodParams = methodMeta.getMethodParams();
        if(methodParams!=null){
            StringBuilder paramsBuilder = new StringBuilder();
            methodParams.forEach(value->{
                paramsBuilder.append(resolveGeneric(value.getParamType(),value.getGenericParams()))
                        .append(codeSpaceStyle())
                        .append(value.getParamName()).append(",");
            });
            builder.append(StringUtil.clearChar(paramsBuilder.toString(),',',
                    StringUtil.ClearCharType.END,-1));
        }
        builder.append(")");
        //抽象方法结束
        if(methodMeta.getMethodType()== MethodType.ABSTRACT)
           return builder.append(";").append("\n").toString();
        //方法内容
        String methodContent = methodMeta.getMethodContent();
        if(methodContent==null) //方法内容为空，结束
            return builder.append("{}").append("\n").toString();
        builder.append("{\n");
        builder.append(methodContent);
        //结束
        builder.append(structureExternalIndentation()).append("}").append("\n");
        return builder.toString();
    }

    protected String doGetMethods( Map<String, MethodMeta>  methodMetas){
        StringBuilder builder = new StringBuilder();
        methodMetas.forEach((key,value)->{
            builder.append(doGetMethod(value))
                    .append(structureClearanceLineStyle());
        });
        return builder.toString();
    }

    protected String doGetAttributes(Map<String, FieldMeta> fieldMetas){
        StringBuilder builder = new StringBuilder();
        fieldMetas.forEach((key,value)->{
            builder.append(doGetAttribute(value))
                    .append(structureClearanceLineStyle());
        });
        return builder.toString();
    }

    /**生成类属性
     * 2022/9/14 0014-15:34
     * @author pengfulin
     */
    protected String doGetAttribute(FieldMeta fieldMeta){
        StringBuilder builder = new StringBuilder();
        //注释
        CommentMeta fieldComment = fieldMeta.getFieldComment();
        if(fieldComment!=null)
            builder.append(doGetComment(fieldComment, codeBuildStyle.structureExternalSpace));
        builder.append(structureExternalIndentation());
        //权限
        builder.append(fieldMeta.getFieldPermission())
                .append(codeSpaceStyle());
        //是否静态
        if(fieldMeta.isStatic())
            builder.append("static").append(codeSpaceStyle());
        //类型
        builder.append(fieldMeta.getFieldType())
                .append(codeSpaceStyle());
        //名称
        builder.append(fieldMeta.getFieldName());
        //赋值
        String fieldValue = fieldMeta.getFieldValue();
        if(fieldValue!=null)
            builder.append(codeSpaceStyle()).append("=").append(codeSpaceStyle()).append(fieldValue);
        builder.append(";").append("\n");
        return  builder.toString();
    }

    /**代码间隙风格
     * 2022/9/9 0009-16:26
     * @author pengfulin
    */
    protected String codeSpaceStyle(){
        return indentationStyle(codeBuildStyle.space);
    }

    /**代码模块间行风格
     * 2022/9/9 0009-16:20
     * @author pengfulin
     */
    protected String modelClearanceLineStyle(){
        return clearanceLineStyle(codeBuildStyle.modelAfterLine);
    }

    /**代码结构间行风格
     * 2022/9/14 0014-15:52
     * @author pengfulin
    */
    protected String structureClearanceLineStyle(){
        return clearanceLineStyle(codeBuildStyle.structureSuffixLine);
    }

    /**代码结构区域外部缩进
     * 2022/9/14 0014-16:03
     * @author pengfulin
    */
    protected String structureExternalIndentation(){
        return indentationStyle(codeBuildStyle.structureExternalSpace);
    }

    /**代码结构区域内部缩进
     * 2022/9/14 0014-16:12
     * @author pengfulin
    */
    protected String structureInternalIndentation(){
        return indentationStyle(codeBuildStyle.structureInternalSpace);
    }

    /**生成注释
     * 2022/9/9 0009-14:13
     * @author pengfulin
    */
    protected String doGetComment(CommentMeta commentMeta,int spaceNum){
        if(commentMeta.getCommentType()== CommentType.ONE)
            return getOneComment(commentMeta,spaceNum);
        return getManyComment(commentMeta,spaceNum);
    }

    /**获取单行注释
     * 2022/9/9 0009-15:02
     * @author pengfulin
    */
    protected String getOneComment(CommentMeta commentMeta,int spaceNum){
        String template="%s//%s\n";
        return String.format(template,indentationStyle(spaceNum),commentMeta.getDescription());
    }

    /**获取多行注释
     * 2022/9/9 0009-15:02
     * @author pengfulin
    */
    protected String getManyComment(CommentMeta commentMeta, int spaceNum){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s/**%s\n",indentationStyle(spaceNum),StringUtil.clearChar(
                commentMeta.getDescription(),'\n', StringUtil.ClearCharType.ALL,-1)));
        String template="%s * %s %s\n";
        Map<String, String> labels = commentMeta.getLabels();
        if(labels!=null){
            commentMeta.getLabels().forEach((key,value)->{
                builder.append(String.format(
                        template,indentationStyle(spaceNum),key,value)
                );}
            );
        }
        builder.append(String.format("%s*/\n",indentationStyle(spaceNum)));
        return builder.toString();
    }
    
    /**获取继承
     * 2022/9/9 0009-16:01
     * @author pengfulin
    */
    protected String doGetExtends(ExtendsMeta classExtends){
        return resolveGeneric( classExtends.getExtendsType(),classExtends.getGenericParams());
    }

    /**获取实现
     * 2022/9/9 0009-16:07
     * @author pengfulin
    */
    protected String doGetImpl(ImplementsMeta implementsMeta){
        return resolveGeneric( implementsMeta.getImplementsType(),implementsMeta.getGenericParams());
    }

    /**解析泛型
     * 2022/9/9 0009-16:13
     * @author pengfulin
    */
    private String resolveGeneric(Class<?> classType,List<Class<?>> paramTypes){
        if (paramTypes != null) {
            String template = "%s<%s>";
            if (paramTypes.size() < 2)
                return String.format(template, paramTypes.get(0).getSimpleName());
            StringBuilder builder = new StringBuilder();
            paramTypes.forEach(value -> {
                builder.append(value.getSimpleName()).append(",");
            });
            String paramValue = builder.toString();
            paramValue= StringUtil.clearChar(paramValue,',',StringUtil.ClearCharType.END, -1);
            return String.format(template, classType.getSimpleName(), paramValue);
        }
        return classType.getSimpleName();
    }



    /**类声明中的类导入信息
     * 2022/9/19 0019-14:52
     * @author pengfulin
    */
    protected Set<Class<?>> resolveClassStatementImports(ClassMetaStatement classMetaStatement){
        Set<Class<?>> classImports = new LinkedHashSet<>();
        ExtendsMeta classExtends = classMetaStatement.getClassExtends();
        if(classExtends!=null){
            Class<?> extendsType = classExtends.getExtendsType();
            if(!ClassUtil.ignoreImportClass(extendsType))
                classImports.add(extendsType);
        }
        Map<Class<?>, ImplementsMeta> classImplements = classMetaStatement.getClassImplements();
        if(classImplements!=null){
            classImplements.forEach((key,value)->{
                if (!ClassUtil.ignoreImportClass(key)) {
                    classImports.add(key);
                }
            });
        }
        return classImports;
    }

    /**属性中的类导入信息
     * 2022/9/19 0019-14:57
     * @author pengfulin
    */
    protected Set<Class<?>> resolveAttributeImports(Map<String, FieldMeta> attributes){
        Set<Class<?>> classImports = new LinkedHashSet<>();
        attributes.forEach((key,value)->{
            Class<?> java = value.getFieldType().java;
            if (!ClassUtil.ignoreImportClass(java)) {
                classImports.add(java);
            }
        });
        return classImports;
    }
}