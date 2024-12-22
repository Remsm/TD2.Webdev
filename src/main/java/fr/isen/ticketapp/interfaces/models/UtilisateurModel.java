package fr.isen.ticketapp.interfaces.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.isen.ticketapp.interfaces.models.enums.ACTIF;
import java.util.Date;

public class UtilisateurModel {

    @JsonProperty("id")
    private int id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mot_de_passe")
    private String mot_de_passe;

    @JsonProperty("derniere_connexion")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String derniere_connexion;

    @JsonProperty("statut")
    private ACTIF statut;

    @JsonProperty("role")
    private String role;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getDerniere_connexion() {
        return derniere_connexion;
    }

    public void setDerniere_connexion(String derniere_connexion) {
        this.derniere_connexion = derniere_connexion;
    }

    public ACTIF getStatut() {
        return statut;
    }

    public void setStatut(ACTIF statut) {
        this.statut = statut;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
