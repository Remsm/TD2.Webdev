package fr.isen.ticketapp.resources;

import fr.isen.ticketapp.implementations.UtilisateurServiceImpl;
import fr.isen.ticketapp.interfaces.models.UtilisateurModel;
import fr.isen.ticketapp.interfaces.services.UtilisateurService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/utilisateur")
public class UtilisateurResource {

    private UtilisateurService utilisateurService;

    public UtilisateurResource() {
        this.utilisateurService = new UtilisateurServiceImpl();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UtilisateurModel> getAllTickets() {
        System.out.println("GET /utilisateur: Récupération de tous les utilisateurs");
        List<UtilisateurModel> utilisateurs = new ArrayList<>(this.utilisateurService.getJSONUtilisateurs());
        List<UtilisateurModel> utilisateursFromDB = new ArrayList<>(this.utilisateurService.getUtilisateurs());
        utilisateurs.addAll(utilisateursFromDB);
        return utilisateurs;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UtilisateurModel getUtilisateurtById(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("GET /utilisateur/" + id + ": Récupération de l'utilisateur avec ID " + id);
        return this.utilisateurService.getUtilisateurtById(id);
    }

    @POST
    public UtilisateurModel addUtilisateur(UtilisateurModel utilisateurModel) {
        System.out.println("POST /utilisateur: Ajout d'un nouvel utilisateur");
        return this.utilisateurService.addUtilisateur(utilisateurModel);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUtilisateur(@jakarta.ws.rs.PathParam("id") int id) {
        System.out.println("DELETE /utilisateur/" + id + ": Suppression de l'utilisateur avec ID " + id);
        this.utilisateurService.deleteUtilisateur(id);
    }

    @PUT
    public UtilisateurModel modifyUtilisateur(UtilisateurModel utilisateurModel) {
        System.out.println("PUT /utilisateur: Modification d'un utilisateur");
        return this.utilisateurService.modifyUtilisateur(utilisateurModel);
    }
}
