package fr.isen.ticketapp.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.models.enums.ETAT;
import fr.isen.ticketapp.interfaces.models.enums.IMPACT;
import fr.isen.ticketapp.interfaces.services.TicketService;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    @Override
    public List<TicketModel> getJSONTickets() {
        return getTicketFromJsonFile(TicketModel[].class, "ticket.json");
    }

    @Override
    public List<TicketModel> getTickets() {
        List<TicketModel> tickets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM ticket";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TicketModel ticket = new TicketModel();
                ticket.setId(rs.getInt("id"));
                ticket.setTitre(rs.getString("titre"));
                ticket.setDescription(rs.getString("description"));
                ticket.setImpact(IMPACT.valueOf(rs.getString("impact")));
                ticket.setDate_creation(rs.getTimestamp("date_creation"));
                ticket.setDate_modification(rs.getString("date_modification"));
                ticket.setEtat(ETAT.valueOf(rs.getString("etat")));
                ticket.setUtilisateur_createur(rs.getString("utilisateur_createur"));
                ticket.setPoste_informatique(rs.getInt("poste_informatique"));
                ticket.setType_demande(rs.getString("type_demande"));

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des tickets", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
            }
        }

        return tickets;
    }


    @Override
    public TicketModel getTicketById(int id) {
        List<TicketModel> tickets = new ArrayList<>(this.getJSONTickets());
        List<TicketModel> ticketsFromDB = new ArrayList<>(this.getTickets());
        tickets.addAll(ticketsFromDB);
        return tickets.stream()
                .filter(ticket -> ticket.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public TicketModel addTicket(TicketModel ticketModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();

            String sql = "INSERT INTO ticket (titre, description, impact, date_creation, date_modification, etat, utilisateur_createur, poste_informatique, type_demande) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Paramètres de la requête SQL
            stmt.setString(1, ticketModel.getTitre());  // titre
            stmt.setString(2, ticketModel.getDescription());  // description
            stmt.setString(3, ticketModel.getImpact().toString());  // impact
            stmt.setTimestamp(4, new Timestamp(ticketModel.getDate_creation().getTime()));  // date_creation
            stmt.setTimestamp(5, ticketModel.getDate_modification() != null ?
                    new Timestamp(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(ticketModel.getDate_modification()).getTime()) : null);  // date_modification
            stmt.setString(6, ticketModel.getEtat().toString());  // etat
            stmt.setString(7, ticketModel.getUtilisateur_createur());  // utilisateur_createur
            stmt.setInt(8, ticketModel.getPoste_informatique());  // poste_informatique
            stmt.setString(9, ticketModel.getType_demande());  // type_demande

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ticketModel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException("Error adding ticket", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return ticketModel;
    }

    @Override
    public void deleteTicket(int id) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ticket WHERE id = ?");
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Ticket avec l'ID " + id + " introuvable.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du ticket avec l'ID " + id, e);
        }
    }

    @Override
    public TicketModel modifyTicket(TicketModel ticketModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();

            String sql = "UPDATE ticket SET titre = ?, description = ?, impact = ?, date_modification = ?, etat = ?, utilisateur_createur = ?, poste_informatique = ?, type_demande = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            // Paramètres de la requête SQL
            stmt.setString(1, ticketModel.getTitre());  // titre
            stmt.setString(2, ticketModel.getDescription());  // description
            stmt.setString(3, ticketModel.getImpact().toString());  // impact
            stmt.setTimestamp(4, ticketModel.getDate_modification() != null ?
                    new Timestamp(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(ticketModel.getDate_modification()).getTime()) : null);  // date_modification
            stmt.setString(5, ticketModel.getEtat().toString());  // etat
            stmt.setString(6, ticketModel.getUtilisateur_createur());  // utilisateur_createur
            stmt.setInt(7, ticketModel.getPoste_informatique());  // poste_informatique
            stmt.setString(8, ticketModel.getType_demande());  // type_demande
            stmt.setInt(9, ticketModel.getId());  // ID du ticket à modifier

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Ticket avec l'ID " + ticketModel.getId() + " introuvable.");
            }

        } catch (SQLException | ParseException e) {
            throw new RuntimeException("Erreur lors de la modification du ticket avec l'ID " + ticketModel.getId(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
            }
        }
        return ticketModel;
    }


    // Méthode générique pour charger le fichier JSON et le convertir en liste d'objets
    public <T> List<T> getTicketFromJsonFile(Class<T[]> cls, String filepath) {
        List<T> elements = new ArrayList<>();
        try {
            // Créer un ObjectMapper pour désérialiser le JSON
            ObjectMapper mapper = new ObjectMapper();

            // Charger le fichier JSON à partir des ressources
            URL res = getClass().getClassLoader().getResource(filepath);
            File file = new File(res.getFile());

            // Désérialiser le JSON en tableau, puis le convertir en liste
            T[] array = mapper.readValue(file, cls);  // Désérialisation directe en tableau
            elements = Arrays.asList(array);  // Convertir le tableau en liste

        } catch (Exception ex) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + ex.getMessage());
            ex.printStackTrace();
        }

        return elements;
    }
}
