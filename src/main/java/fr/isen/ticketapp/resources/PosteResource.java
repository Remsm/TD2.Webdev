package fr.isen.ticketapp.resources;

import fr.isen.ticketapp.implementations.PosteServiceImpl;
import fr.isen.ticketapp.interfaces.models.PosteModel;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.PosteService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Path("/poste")
public class PosteResource {

    private PosteService posteService;

    public PosteResource() {
        this.posteService = new PosteServiceImpl();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PosteModel> getAllPostes() {
        List<PosteModel> postes = this.posteService.getJSONPostes();
        return postes;
    }

    @POST
    public PosteModel addPoste(PosteModel posteModel) {
        return this.posteService.addPoste(posteModel);
    }
}

