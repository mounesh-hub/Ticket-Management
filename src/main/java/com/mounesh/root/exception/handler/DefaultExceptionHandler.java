package com.mounesh.root.exception.handler;

import com.mounesh.root.exception.GenericExceptionmodel;
import com.mounesh.root.exception.model.TicketTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<GenericExceptionmodel> handleTicketTypeNotFoundException(TicketTypeNotFoundException exception) {
        System.out.println("DefaultExceptionHandler#handleTicketTypeNotFoundException");
        GenericExceptionmodel model = new GenericExceptionmodel();
        model.setMessage(exception.getMessage());
        model.setStatus(400);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
