package com.sakura.generator.constant;

import java.io.File;

public interface GeneratorConstant {

    /**
     * 用户目录
     */
    String USER_DIR = "user.dir";
    /**
     * java目录 src/main/java
     */
    String JAVA_DIR = File.separator + "src" + File.separator + "main" + File.separator + "java";
    /**
     * resources目录 src/main/resources
     */
    String RESOURCES_DIR = File.separator + "src" + File.separator + "main" + File.separator + "resources";
    /**
     * mapper目录 src/main/resources/mapper
     */
    String MAPPER_DIR = RESOURCES_DIR + File.separator + "mapper";
    /**
     * 参数
     */
    String PARAM = "param";
    /**
     * 分页参数
     */
    String PAGE_PARAM = "PageParam";
    /**
     * VO
     */
    String VO = "vo";
    /**
     * 查询VO
     */
    String QUERY_VO = "QueryVo";
    /**
     * Service
     */
    String SERVICE = "Service";
    /**
     * Mapper
     */
    String MAPPER = "Mapper";
    /**
     * Mapper XML template路径
     */
    String MAPPER_XML_TEMPLATE_PATH = File.separator + "templates" + File.separator + "mapper.xml.vm";
    /**
     * 分页参数 template路径
     */
    String PAGE_PARAM_TEMPLATE_PATH = File.separator + "templates" + File.separator + "pageParam.java.vm";
    /**
     * 查询VO template路径
     */
    String QUERY_VO_TEMPLATE_PATH = File.separator + "templates" + File.separator + "queryVo.java.vm";
    /**
     * 乐观锁属性名称
     */
    String VERSION = "version";
    /**
     * 逻辑删除属性名称
     */
    String DELETED = "deleted";
    /**
     * Service名称
     */
    String SERVICE_NAME = "%sService";
    /**
     * 自定义继承的Entity类全称，带包名
     */
    String SUPER_ENTITY_CLASS = "com.sakura.common.base.BaseEntity";
    /**
     * 自定义继承的Controller类全称，带包名
     */
    String SUPER_CONTROLLER_CLASS = "com.sakura.common.base.BaseController";
    /**
     * 自定义继承的Service类全称，带包名
     */
    String SUPER_SERVICE_CLASS = "com.sakura.common.base.BaseService";
    /**
     * 自定义继承的ServiceImpl类全称，带包名
     */
    String SUPER_SERVICE_IMPL_CLASS = "com.sakura.common.base.BaseServiceImpl";
    /**
     * 分页参数父类全称，带包名
     */
    String SUPER_PAGE_PARAM_CLASS = "com.sakura.common.pagination.BasePageParam";
    /**
     * 分页排序参数父类全称，带包名
     */
    String SUPER_PAGE_ORDER_PARAM_CLASS = "com.sakura.common.pagination.BasePageOrderParam";
    /**
     * 公共id参数类全称，带包名
     */
    String ID_PARAM_CLASS = "com.sakura.common.api.IdParam";
    /**
     * 分页对象类全称，带包名
     */
    String PAGING_CLASS = "com.sakura.common.pagination.Paging";
    /**
     * 分页信息类全称，带包名
     */
    String PAGE_INFO_CLASS = "com.sakura.common.pagination.PageInfo";
    /**
     * 公共结果类全称，带包名
     */
    String API_RESULT_CLASS = "com.sakura.common.api.ApiResult";
    /**
     * 分组验证Add.class类路径
     */
    String VALIDATOR_ADD_PACKAGE = "com.sakura.common.api.Add";
    /**
     * 分组验证Update.class类路径
     */
    String VALIDATOR_UPDATE_PACKAGE = "com.sakura.common.api.Update";
}
