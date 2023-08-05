package com.sakura.common.exception;

import com.sakura.common.api.ApiCode;

/**
 * DAO异常
 */
public class DaoException extends BaseException {
	private static final long serialVersionUID = -6912618737345878854L;

	public DaoException(String message) {
        super(message);
    }

    public DaoException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public DaoException(ApiCode apiCode) {
        super(apiCode);
    }
}
