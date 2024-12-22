package fr.isen.ticketapp.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.UtilisateurModel;
import fr.isen.ticketapp.interfaces.services.UtilisateurService;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    @Override
    public List<UtilisateurModel> getJSONUtilisateurs() {
        return getUtilisateurFromJsonFile(UtilisateurModel[].class, "utilisateur.json");
    }

    @Override
    public UtilisateurModel addUtilisateur(UtilisateurModel utilisateurModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();

            String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, derniere_connexion, statut, role) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, utilisateurModel.getNom());
            stmt.setString(2, utilisateurModel.getEmail());
            stmt.setString(3, utilisateurModel.getMot_de_passe());
            stmt.setString(4, utilisateurModel.getDerniere_connexion());
            stmt.setString(5, utilisateurModel.getStatut().toString());
            stmt.setString(6, utilisateurModel.getRole());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    utilisateurModel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
            }
        }
        return utilisateurModel;
    }

    @Override
    public void createUser(UtilisateurModel user) {

    }

    @Override
    public void updateUser(int id, UtilisateurModel user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void getUser() {

    }

    // Méthode générique pour charger le fichier JSON et le convertir en liste d'objets
    public <T> List<T> getUtilisateurFromJsonFile(Class<T[]> cls, String filepath) {
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
