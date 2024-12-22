package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.UtilisateurModel;

import java.util.List;

public interface UtilisateurService {

    List<UtilisateurModel> getJSONUtilisateurs();

    List<UtilisateurModel> getUtilisateurs();

    UtilisateurModel getUtilisateurtById(final int id);

    UtilisateurModel addUtilisateur(final UtilisateurModel utilisateurModel);

    UtilisateurModel modifyUtilisateur(final UtilisateurModel utilisateurModel);

    void deleteUtilisateur(int id);


}
