package build.bus.model.simple;

import build.builder.meta.BuildCoder;
import build.builder.meta.codes.java.classes.bean.LombokBeanBuilder;
import build.builder.meta.codes.java.classes.bean.SimpleBeanBuilder;
import build.builder.util.CollectionUtil;
import build.bus.meta.BuildBus;
import java.util.List;
/**
 * 简单的构建总线
 *
 * @author peng_fu_lin
 * 2022-09-06 16:11
 */
public class SimpleBuildBus extends BuildBus {

    @Override
    protected final void init() {
        this.buildCoders.addAll(initBuildCoder());
        super.init();
    }

    /**初始化构建器
     * 2022/9/21 0021-16:25
     * @author pengfulin
    */
    protected List<BuildCoder<?>> initBuildCoder(){
        BuildCoder<?>[] buildCoders = {new SimpleBeanBuilder(), new LombokBeanBuilder()};
        return CollectionUtil.asList(buildCoders);
    }
}