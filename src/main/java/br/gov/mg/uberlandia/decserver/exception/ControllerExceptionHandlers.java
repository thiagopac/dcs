package br.gov.mg.uberlandia.decserver.exception;

import br.gov.mg.uberlandia.decserver.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ControllerAdvice
public class ControllerExceptionHandlers implements WebMvcConfigurer {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage handleNotFoundException(NotFoundException e) {
        return new ErrorMessage(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleBadRequestException(BadRequestException e) {
        return new ErrorMessage(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessage handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorMessage(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorMessage handleBusinessException(BusinessException e) {
        return new ErrorMessage(e.getCode(), e.getMessage());
    }

}
