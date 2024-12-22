package fr.isen.ticketapp.interfaces.models;

import java.util.Date;
import fr.isen.ticketapp.interfaces.models.enums.ACTIF;

public class UtilisateurModel {
    private int id;

    private String nom;

    private String email;

    private String mot_de_passe;

    private Date derniere_connexion;

    public ACTIF statut;

}
