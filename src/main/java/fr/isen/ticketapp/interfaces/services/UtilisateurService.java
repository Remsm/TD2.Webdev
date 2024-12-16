package fr.isen.ticketapp.interfaces.services;

import fr.isen.ticketapp.interfaces.models.Utilisateurs;

public interface UtilisateurService {
    void createUser(final Utilisateurs user);

    void updateUser(final int id, final Utilisateurs user);

    void deleteUser(final int id);

    void getUser();

}
