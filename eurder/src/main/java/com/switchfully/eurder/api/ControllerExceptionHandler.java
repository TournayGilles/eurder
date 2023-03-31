package com.switchfully.eurder.api;

import com.switchfully.eurder.internals.exceptions.NoRightException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoRightException.class)
    protected void IllegalAccess(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "You don't have the right to do this");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    protected void IllegalArgumentHandler(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong with the Id provided");

    }
    @ExceptionHandler(RuntimeException.class)
    protected void generalException(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Oops, something went wrong");
    }
}
