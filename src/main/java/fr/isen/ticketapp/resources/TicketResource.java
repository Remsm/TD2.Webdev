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
        System.out.println("GET /ticket: Récupération de tous les tickets");
        List<TicketModel> tickets = new ArrayList<>(this.ticketService.getJSONTickets());
        List<TicketModel> ticketsFromDB = new ArrayList<>(this.ticketService.getTickets());
        tickets.addAll(ticketsFromDB);
        return tickets;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketModel getTicketById(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("GET /ticket/" + id + ": Récupération du ticket avec ID " + id);
        return this.ticketService.getTicketById(id);
    }

    @POST
    public TicketModel addTicket(TicketModel ticketModel) {
        System.out.println("POST /ticket: Ajout d'un nouveau ticket");
        return this.ticketService.addTicket(ticketModel);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTicket(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("DELETE /ticket/" + id + ": Suppression du ticket avec ID " + id);
        this.ticketService.deleteTicket(id);
    }

    @PUT
    public TicketModel modifyTicket(TicketModel ticketModel) {
        System.out.println("PUT /ticket: Modification d'un ticket");
        return this.ticketService.modifyTicket(ticketModel);
    }


}







