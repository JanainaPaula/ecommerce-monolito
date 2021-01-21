package br.com.janadev.ecommerce.infra.error.exception;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String msg) {
        super(msg);
    }
}
