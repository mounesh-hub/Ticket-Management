package com.mounesh.root.Dao;

import com.mounesh.root.model.Ticket;
import com.mounesh.root.model.TicketWrapper;

import java.util.List;

public interface TicketDao {
    Ticket addTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket);
    Ticket deleteTicket(String id);
    Ticket getTicket(String id);
    List<Ticket> getAllTickets(String type);
    TicketWrapper bulkInsert(TicketWrapper model);
}
