package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.TicketModel;

import java.util.List;

public interface TicketService {

    List<TicketModel> getJSONTickets();

    List<TicketModel> getTickets();

    TicketModel getTicketById(final int id);

    TicketModel addTicket(final TicketModel ticketModel);

    TicketModel modifyTicket(final TicketModel ticketModel);

    void deleteTicket(int id);
}
