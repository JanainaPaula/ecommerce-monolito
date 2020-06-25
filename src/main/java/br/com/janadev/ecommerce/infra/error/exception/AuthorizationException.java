package br.com.janadev.ecommerce.infra.error.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String msg) {
        super(msg);
    }
}
