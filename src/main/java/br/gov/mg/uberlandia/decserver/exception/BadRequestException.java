package br.gov.mg.uberlandia.decserver.exception;

public class BadRequestException extends RuntimeException {

    private String code;

    private String message;

    public BadRequestException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BadRequestException(String message) {
        this("BAD_REQUEST", message);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
