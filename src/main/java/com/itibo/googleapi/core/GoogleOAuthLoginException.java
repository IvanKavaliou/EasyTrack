package com.itibo.googleapi.core;

public class GoogleOAuthLoginException extends Exception {

    public GoogleOAuthLoginException() {
    }

    public GoogleOAuthLoginException(String message) {
        super(message);
    }

    public GoogleOAuthLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleOAuthLoginException(Throwable cause) {
        super(cause);
    }

}
