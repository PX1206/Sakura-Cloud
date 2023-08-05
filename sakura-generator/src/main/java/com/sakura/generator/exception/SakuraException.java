package com.sakura.generator.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SakuraException extends RuntimeException{

    private static final long serialVersionUID = -2470461654663264392L;

    private Integer errorCode;
    private String message;

    public SakuraException() {
        super();
    }

    public SakuraException(String message) {
        super(message);
        this.message = message;
    }

    public SakuraException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public SakuraException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public SakuraException(String message, Throwable cause) {
        super(message, cause);
    }

    public SakuraException(Throwable cause) {
        super(cause);
    }

}
