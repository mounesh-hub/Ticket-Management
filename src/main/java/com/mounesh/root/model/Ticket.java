package com.mounesh.root.model;

import java.sql.Date;

public class Ticket {
    private String id;
    private String name;
    private String resolutionDate;
    private String ticketType;
    private String reporter;
    private String assignee;

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String toString() {
        return "id = " + id + "\n"
                + "name = " + name + "\n"
                + "resolutionDate = " + resolutionDate + "\n"
                + "ticketType = " + ticketType + "\n"
                + "assignee = " + assignee;
    }
}
