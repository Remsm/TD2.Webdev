package fr.isen.ticketapp.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.TicketModel;
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
    public List<TicketModel> getTickets() {
        return getTicketFromJsonFile(TicketModel[].class, "ticket.json");
    }

    @Override
    public TicketModel getTicketById(int id) {
        List<TicketModel> tickets = getTickets();
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
