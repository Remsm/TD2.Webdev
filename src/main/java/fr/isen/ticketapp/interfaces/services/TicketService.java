package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.Ticket;

public interface TicketService {
    void createTicket(final Ticket ticket);

    void getAllTickets();

    void getTicket();

    void updateTicket(final int id, final Ticket ticket);

    void deleteTicket(final int id);

}
