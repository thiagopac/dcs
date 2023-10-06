package br.gov.mg.uberlandia.decserver.exception;

public class NotFoundException extends RuntimeException {

    private String code;

    private String message;

    public NotFoundException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
