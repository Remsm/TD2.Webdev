package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.TicketModel;

import java.util.List;

public interface TicketService {

    List<TicketModel> getTickets();

    TicketModel getTicketById(final int id);

    void removeTicket(final int id);
}
