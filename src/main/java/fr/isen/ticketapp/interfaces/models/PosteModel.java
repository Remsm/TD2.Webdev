package fr.isen.ticketapp.interfaces.models;

import fr.isen.ticketapp.interfaces.models.enums.ETAT2;

public class PosteModel {
    private int id;

    public ETAT2 etat2;

    private String configuration;

    public UtilisateurModel utilisateur_affecte;

}
