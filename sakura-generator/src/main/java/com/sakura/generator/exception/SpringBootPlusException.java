package com.sakura.generator.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpringBootPlusException extends RuntimeException{

    private static final long serialVersionUID = -2470461654663264392L;

    private Integer errorCode;
    private String message;

    public SpringBootPlusException() {
        super();
    }

    public SpringBootPlusException(String message) {
        super(message);
        this.message = message;
    }

    public SpringBootPlusException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public SpringBootPlusException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public SpringBootPlusException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringBootPlusException(Throwable cause) {
        super(cause);
    }

}
