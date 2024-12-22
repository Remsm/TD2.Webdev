package fr.isen.ticketapp.resources;

import fr.isen.ticketapp.implementations.UtilisateurServiceImpl;
import fr.isen.ticketapp.interfaces.models.UtilisateurModel;
import fr.isen.ticketapp.interfaces.services.UtilisateurService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
        List<UtilisateurModel> utilisateurs = new ArrayList<>(this.utilisateurService.getJSONUtilisateurs());
        List<UtilisateurModel> utilisateursFromDB = new ArrayList<>(this.utilisateurService.getUtilisateurs());
        utilisateurs.addAll(utilisateursFromDB);
        return utilisateurs;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UtilisateurModel getUtilisateurtById(@jakarta.ws.rs.PathParam("id") int id) {
        return this.utilisateurService.getUtilisateurtById(id);
    }

    @POST
    public UtilisateurModel addUtilisateur(UtilisateurModel utilisateurModel) {
        return this.utilisateurService.addUtilisateur(utilisateurModel);
    }
}
