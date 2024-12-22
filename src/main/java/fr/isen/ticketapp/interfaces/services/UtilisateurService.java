package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.UtilisateurModel;

public interface UtilisateurService {
    void createUser(final UtilisateurModel user);

    void updateUser(final int id, final UtilisateurModel user);

    void deleteUser(final int id);

    void getUser();

}
