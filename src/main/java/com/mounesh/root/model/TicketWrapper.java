package com.mounesh.root.model;

import java.util.ArrayList;
import java.util.List;

public class TicketWrapper {
    private List<Ticket> totalTickets = new ArrayList();
    private List<Ticket> validTickets = new ArrayList();
    private List<Ticket> inValidTickets = new ArrayList();

    public List<Ticket> getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(List<Ticket> totalTickets) {
        this.totalTickets = totalTickets;
    }

    public List<Ticket> getValidTickets() {
        return validTickets;
    }

    public void setValidTickets(List<Ticket> validTickets) {
        this.validTickets = validTickets;
    }

    public List<Ticket> getInValidTickets() {
        return inValidTickets;
    }

    public void setInValidTickets(List<Ticket> inValidTickets) {
        this.inValidTickets = inValidTickets;
    }
}
