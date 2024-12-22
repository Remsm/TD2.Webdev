package fr.isen.ticketapp;

import fr.isen.ticketapp.implementation.TicketServiceImpl;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.TicketService;
import jakarta.ws.rs.*;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketModel getTicketById(@jakarta.ws.rs.PathParam("id") int id) {
        return this.ticketService.getTicketById(id);
    }

    @POST
    public TicketModel addTicket(TicketModel ticketModel) {
        return this.ticketService.addTicket(ticketModel);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTicket(@jakarta.ws.rs.PathParam("id") int id) {
        this.ticketService.deleteTicket(id);
    }


}







