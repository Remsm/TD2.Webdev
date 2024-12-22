package fr.isen.ticketapp.resources;

import fr.isen.ticketapp.implementations.TicketServiceImpl;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.TicketService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
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
        List<TicketModel> tickets = new ArrayList<>(this.ticketService.getJSONTickets());
        List<TicketModel> ticketsFromDB = new ArrayList<>(this.ticketService.getTickets());
        tickets.addAll(ticketsFromDB);
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

    @PUT
    public TicketModel modifyTicket(TicketModel ticketModel) {
        return this.ticketService.modifyTicket(ticketModel);
    }


}







