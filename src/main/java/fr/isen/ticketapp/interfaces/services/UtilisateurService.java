package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.UtilisateurModel;

import java.util.List;

public interface UtilisateurService {

    List<UtilisateurModel> getJSONUtilisateurs();

    UtilisateurModel addUtilisateur(final UtilisateurModel utilisateurModel);

    void createUser(final UtilisateurModel user);

    void updateUser(final int id, final UtilisateurModel user);

    void deleteUser(final int id);

    void getUser();

}
