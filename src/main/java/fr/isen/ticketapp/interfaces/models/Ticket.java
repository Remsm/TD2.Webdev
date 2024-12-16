package fr.isen.ticketapp.interfaces.models;

import java.util.Date;
import fr.isen.ticketapp.interfaces.models.enums.ETAT;
import fr.isen.ticketapp.interfaces.models.enums.IMPACT;

public class Ticket {
    private int id;

    public IMPACT impact;

    private String title;

    private String description;

    private Date date_creation;

    private String date_modification;

    public ETAT etat;

    public Utilisateurs utilisateur_createur;

    public Poste poste__informatique;

    private String type_demande;

}
