package fr.isen.ticketapp.resources;

import fr.isen.ticketapp.implementations.PosteServiceImpl;
import fr.isen.ticketapp.interfaces.models.PosteModel;
import fr.isen.ticketapp.interfaces.services.PosteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

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
        System.out.println("GET /poste: Récupération de tous les postes");
        List<PosteModel> postes = new ArrayList<>(this.posteService.getJSONPostes());
        List<PosteModel> postesFromDB = new ArrayList<>(this.posteService.getPostes());
        postes.addAll(postesFromDB);
        return postes;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PosteModel getPosteById(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("GET /poste/" + id + ": Récupération du poste avec ID " + id);
        return this.posteService.getPosteById(id);
    }

    @POST
    public PosteModel addPoste(PosteModel posteModel) {
        System.out.println("POST /poste: Ajout d'un nouveau poste");
        return this.posteService.addPoste(posteModel);
    }

    @DELETE
    @Path("/{id}")
    public void deletePoste(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("DELETE /poste/" + id + ": Suppression du poste avec ID " + id);
        this.posteService.deletePoste(id);
    }

    @PUT
    public PosteModel modifyPoste(PosteModel posteModel) {
        System.out.println("PUT /poste: Modification d'un poste");
        return this.posteService.modifyPoste(posteModel);
    }
}

