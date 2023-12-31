package com.sakura.common.constant;

/**
 * 公共常量
 */
public interface CommonConstant {

    /**
     * 默认页码为1
     */
    Long DEFAULT_PAGE_INDEX = 1L;

    /**
     * 默认页大小为10
     */
    Long DEFAULT_PAGE_SIZE = 10L;

    /**
     * 分页总行数名称
     */
    String PAGE_TOTAL_NAME = "total";

    /**
     * 分页数据列表名称
     */
    String PAGE_RECORDS_NAME = "records";

    /**
     * 分页当前页码名称
     */
    String PAGE_INDEX_NAME = "pageIndex";

    /**
     * 请求ID
     */
    String REQUEST_ID = "requestId";

    /**
     * 分页当前页大小名称
     */
    String PAGE_SIZE_NAME = "pageSize";

    /**
     * 登录token
     */
    String ACCESS_TOKEN = "Access-Token";

    /**
     * Feigntoken
     */
    String FEIGN_TOKEN = "Feign-Token";

    /**
     * 图片
     */
    String IMAGE = "image";

    /**
     * JPEG
     */
    String JPEG = "JPEG";

    /**
     * base64前缀
     */
    String BASE64_PREFIX = "data:image/png;base64,";

    /**
     * ..
     */
    String SPOT_SPOT = "..";

    /**
     * ../
     */
    String SPOT_SPOT_BACKSLASH = "../";

    /**
     * SpringBootAdmin登录信息
     */
    String ADMIN_LOGIN_SESSION = "adminLoginSession";

    /**
     * 用户浏览器代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * 本机地址IP
     */
    String LOCALHOST_IP = "127.0.0.1";
    /**
     * 本机地址名称
     */
    String LOCALHOST_IP_NAME = "本机地址";
    /**
     * 局域网IP
     */
    String LAN_IP = "192.168";
    /**
     * 局域网名称
     */
    String LAN_IP_NAME = "局域网";

    /**
     * Redis短信验证码key
     */
    String SMS_CODE = "sms_code_";

    /**
     * Redis短信验证码发送次数key
     */
    String SMS_SEND_NUM = "sms-send-num-";

    /**
     * 密码错误次数key
     */
    String PASSWORD_ERROR_NUM = "password-error-num-";

    /**
     * Redis保存权限url前缀
     */
    String PERMISSION_URL = "permission_url_";

    /**
     * @description: 保存用户登录token集合
     */
    String USER_TOKEN_SET = "user_token_set_";

    /**
     * Redis保存登录用户商户号前缀
     */
    String MERCHANT_NO_TOKEN = "merchant_no_token_";

    String[] EXCLUDE_PATH = {"/user/register", "/merchant/applySettled",
            "/customerUser/login", "/adminUser/login",
            "/merchantUser/login", "/merchantUser/choose",
            "/captcha/getPictureCode",
            "/encryption/getSalt", "/sms/getCode", "/feign/**", "/file/{code}",
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**",
            "/favicon.ico", "/error"};
}
