package com.mounesh.root.messageConverters;

import com.mounesh.root.model.Ticket;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;


public class TicketJacksonConverter extends AbstractHttpMessageConverter<Ticket> {

    private MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();

    protected TicketJacksonConverter() {
        super();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Ticket.class == clazz;
    }

    @Override
    protected Ticket readInternal(Class<? extends Ticket> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        System.out.println("TicketJacksonConverter#readInternal");
        return  (Ticket) jackson.read(aClass, httpInputMessage);
    }

    @Override
    protected void writeInternal(Ticket ticket, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("TicketJacksonConverter#writeInternal");
        jackson.write(ticket, MediaType.APPLICATION_JSON, httpOutputMessage);
    }


}
