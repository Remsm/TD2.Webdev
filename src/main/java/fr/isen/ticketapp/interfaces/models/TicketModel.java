package fr.isen.ticketapp.interfaces.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import fr.isen.ticketapp.interfaces.models.enums.ETAT;
import fr.isen.ticketapp.interfaces.models.enums.IMPACT;

import java.util.Date;

public class TicketModel {

    @JsonProperty("id")  // Mappe le champ JSON "id"
    private int id;

    @JsonProperty("impact")  // Mappe le champ JSON "impact"
    public IMPACT impact;

    @JsonProperty("titre")  // Mappe le champ JSON "titre"
    private String titre;

    @JsonProperty("description")  // Mappe le champ JSON "description"
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")  // Format de date pour le champ date_creation
    @JsonProperty("date_creation")  // Mappe le champ JSON "date_creation"
    private Date date_creation;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")  // Format de date pour le champ date_modification
    @JsonProperty("date_modifiication")  // Notez la faute d'orthographe "date_modifiication" dans le JSON
    private String date_modification;

    @JsonProperty("etat")  // Mappe le champ JSON "etat"
    public ETAT etat;

    @JsonProperty("utilisateur_createur")  // Mappe le champ JSON "utilisateur_createur"
    private String utilisateur_createur;

    @JsonProperty("poste_informatique")  // Mappe le champ JSON "poste_informatique"
    private int poste_informatique;

    @JsonProperty("type_demande")  // Mappe le champ JSON "type_demande"
    private String type_demande;

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IMPACT getImpact() {
        return impact;
    }

    public void setImpact(IMPACT impact) {
        this.impact = impact;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(String date_modification) {
        this.date_modification = date_modification;
    }

    public ETAT getEtat() {
        return etat;
    }

    public void setEtat(ETAT etat) {
        this.etat = etat;
    }

    public String getUtilisateur_createur() {
        return utilisateur_createur;
    }

    public void setUtilisateur_createur(String utilisateur_createur) {
        this.utilisateur_createur = utilisateur_createur;
    }

    public int getPoste_informatique() {
        return poste_informatique;
    }

    public void setPoste_informatique(int poste_informatique) {
        this.poste_informatique = poste_informatique;
    }

    public String getType_demande() {
        return type_demande;
    }

    public void setType_demande(String type_demande) {
        this.type_demande = type_demande;
    }
}
