package com.switchfully.eurder.api;

import com.switchfully.eurder.internals.exceptions.NoCustomerWithProvidedIdException;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.internals.exceptions.NoSuchItemFoundException;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoRightException.class)
    protected void IllegalAccess(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "You don't have the right to do this");
    }
    @ExceptionHandler({IllegalArgumentException.class, NoCustomerWithProvidedIdException.class, NoSuchOrderException.class, NoSuchItemFoundException.class})
    protected void IllegalArgumentHandler(Exception exception, HttpServletResponse response) throws IOException{
        logger.error(exception.getMessage(),exception);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong with the Id provided");

    }
    @ExceptionHandler(RuntimeException.class)
    protected void generalException(Exception exception, HttpServletResponse response) throws IOException{
        logger.error(exception.getMessage(),exception);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Oops, something went wrong");
    }
}
