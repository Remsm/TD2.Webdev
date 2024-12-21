package fr.isen.ticketapp.services;

import fr.isen.ticketapp.models.Utilisateurs;

public interface UtilisateurService {
    void createUser(final Utilisateurs user);

    void updateUser(final int id, final Utilisateurs user);

    void deleteUser(final int id);

    void getUser();

}
