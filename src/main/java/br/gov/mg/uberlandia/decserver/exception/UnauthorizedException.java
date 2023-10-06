package br.gov.mg.uberlandia.decserver.exception;

public class UnauthorizedException extends RuntimeException {

    private String code;

    private String message;

    public UnauthorizedException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public UnauthorizedException(String message) {
        this("UNAUTHORIZED", message);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
