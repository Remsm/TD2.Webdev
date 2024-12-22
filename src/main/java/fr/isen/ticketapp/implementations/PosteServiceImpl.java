package fr.isen.ticketapp.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.PosteModel;
import fr.isen.ticketapp.interfaces.models.TicketModel;
import fr.isen.ticketapp.interfaces.services.PosteService;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosteServiceImpl implements PosteService {

    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    @Override
    public List<PosteModel> getJSONPostes() {
        return getPosteFromJsonFile(PosteModel[].class, "poste_informatique.json");
    }

    @Override
    public PosteModel addPoste(PosteModel posteModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO Poste (etat2, configuration, utilisateur_affecte) "
                    + "VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, posteModel.getEtat2().toString());
            stmt.setString(2, posteModel.getConfiguration());
            stmt.setString(3, posteModel.getUtilisateur_affecte());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    posteModel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding poste", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
        return posteModel;

    }

    @Override
    public void createPoste(PosteModel poste) {

    }

    @Override
    public void updatePoste(int id, PosteModel poste) {

    }

    @Override
    public void deletePoste(int id) {

    }

    @Override
    public void getPoste() {

    }

    // Méthode générique pour charger le fichier JSON et le convertir en liste d'objets
    public <T> List<T> getPosteFromJsonFile(Class<T[]> cls, String filepath) {
        List<T> elements = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            URL res = getClass().getClassLoader().getResource(filepath);
            File file = new File(res.getFile());

            T[] array = mapper.readValue(file, cls);
            elements = Arrays.asList(array);

        } catch (Exception ex) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + ex.getMessage());
            ex.printStackTrace();
        }

        return elements;
    }
}
