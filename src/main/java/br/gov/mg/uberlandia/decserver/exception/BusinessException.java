package br.gov.mg.uberlandia.decserver.exception;

public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(String code, String message) {
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
