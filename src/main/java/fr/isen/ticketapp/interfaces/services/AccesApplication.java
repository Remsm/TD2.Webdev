package fr.isen.ticketapp.interfaces.services;

public interface AccesApplication {

    void connexionApplication(final String email, final String mot_de_passe);

    void deconnexionApplication();

}
