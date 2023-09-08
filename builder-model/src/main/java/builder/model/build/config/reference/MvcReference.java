package builder.model.build.config.reference;


import lombok.Data;

/**
 * mvc引用
 * author: pengshuaifeng
 * 2023/9/3
 */
@Data
public abstract class MvcReference {
    //控制器引用
    private String controller;
    //服务引用
    private String service;
    //服务实现引用
    private String serviceImpl;
}
