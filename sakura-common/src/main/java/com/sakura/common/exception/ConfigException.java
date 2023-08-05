package com.sakura.common.exception;

import com.sakura.common.api.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * spring-boot-plus配置异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigException extends BaseException {

    private static final long serialVersionUID = 8952028631871769425L;

    private Integer errorCode;
    private String message;

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
        this.message = message;
    }

    public ConfigException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ConfigException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

}
