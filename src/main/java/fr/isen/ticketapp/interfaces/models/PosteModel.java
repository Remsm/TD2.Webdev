package fr.isen.ticketapp.interfaces.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.isen.ticketapp.interfaces.models.enums.ETAT2;

public class PosteModel {

    @JsonProperty("id")
    private int id;

    @JsonProperty("etat2")
    private ETAT2 etat2;

    @JsonProperty("configuration")
    private String configuration;

    @JsonProperty("utilisateur_affecte")
    private String utilisateur_affecte;

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour etat2
    public ETAT2 getEtat2() {
        return etat2;
    }

    public void setEtat2(ETAT2 etat2) {
        this.etat2 = etat2;
    }

    // Getter et Setter pour configuration
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    // Getter et Setter pour utilisateur_affecte
    public String getUtilisateur_affecte() {
        return utilisateur_affecte;
    }

    public void setUtilisateur_affecte(String utilisateur_affecte) {
        this.utilisateur_affecte = utilisateur_affecte;
    }
}
