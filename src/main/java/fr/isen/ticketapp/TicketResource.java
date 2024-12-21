package fr.isen.ticketapp;

import fr.isen.ticketapp.implementation.TicketServiceImpl;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.TicketService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/ticket")
public class TicketResource {

    private TicketService ticketService;

    public TicketResource() {
        this.ticketService = new TicketServiceImpl();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TicketModel> getAllTickets() {
        List<TicketModel> tickets = this.ticketService.getTickets();
        return tickets;
    }
}







