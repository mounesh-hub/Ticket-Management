package com.mounesh.root.exception.model;

public class TicketTypeNotFoundException extends RuntimeException {
    public TicketTypeNotFoundException(){

    }
    public TicketTypeNotFoundException(String error){
        super(error);
    }
}
