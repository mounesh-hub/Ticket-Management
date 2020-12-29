package com.mounesh.root.service;

import com.mounesh.root.Dao.TicketDao;
import com.mounesh.root.model.Ticket;
import com.mounesh.root.model.TicketWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TicketDaoImpl implements TicketDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Ticket addTicket(Ticket ticket) {
        final String query = "insert into tickets values(?,?,?,?,?,?)";
        jdbc.update(query, ticket.getName(), ticket.getId(), ticket.getResolutionDate(), ticket.getTicketType().toLowerCase(), ticket.getReporter(), ticket.getAssignee());
        return ticket;
    }

    @Override
    public List<Ticket> getAllTickets(String type) {
        type = type.toLowerCase();
        final String query = "select * from tickets where ticket_type = ?";
        return jdbc.query(query, new Object[]{type}, new TicketRowMapper());
    }

    @Override
    public Ticket deleteTicket(String id) {
        Ticket ticket = getTicket(id);
        final String query = "delete from tickets where ticket_id = ?";
        jdbc.update(query, id);
        return ticket;
    }

    @Override
    public Ticket getTicket(String id) {
        final String query = "select * from tickets where ticket_id = ?";
        return jdbc.queryForObject(query, new Object[]{id}, new TicketRowMapper());
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        final String query = "update tickets set ticket_name = ?, ticket_type = ?,  ticket_resolution_date= ?, reporter = ?, ticket_assignee = ? where ticket_id = ?";
        jdbc.update(query, ticket.getName(), ticket.getTicketType().toLowerCase(), ticket.getResolutionDate(), ticket.getReporter(), ticket.getAssignee(), ticket.getId());
        return ticket;
    }

    @Override
    public TicketWrapper bulkInsert(TicketWrapper model) {
        final String query = "insert into tickets (ticket_name, ticket_id, ticket_resolution_date, ticket_type, reporter, ticket_assignee) " +
                "values(?,?,?,?,?,?)";
        jdbc.batchUpdate(query, model.getValidTickets(), model.getValidTickets().size(), new PrepareStatementSetter());
        return model;
    }

    private class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(resultSet.getString(2));
            ticket.setName(resultSet.getString(1));
            ticket.setResolutionDate(resultSet.getString(3));
            ticket.setTicketType(resultSet.getString(4));
            ticket.setReporter(resultSet.getString(5));
            ticket.setAssignee(resultSet.getString(6));
            return ticket;
        }
    }

    private class PrepareStatementSetter implements ParameterizedPreparedStatementSetter<Ticket> {
        @Override
        public void setValues(PreparedStatement preparedStatement, Ticket o) throws SQLException {
            preparedStatement.setString(1, o.getName());
            preparedStatement.setString(2, o.getId());
            preparedStatement.setString(3, o.getResolutionDate());
            preparedStatement.setString(4, o.getTicketType());
            preparedStatement.setString(5, o.getReporter());
            preparedStatement.setString(6, o.getAssignee());
        }
    }
}
