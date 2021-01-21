package br.com.janadev.ecommerce.infra.error.exception;

public class FileException extends RuntimeException {
    public FileException(String msg) {
        super(msg);
    }
}
