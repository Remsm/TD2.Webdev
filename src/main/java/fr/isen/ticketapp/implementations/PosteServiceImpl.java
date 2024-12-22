package fr.isen.ticketapp.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.PosteModel;
import fr.isen.ticketapp.interfaces.models.enums.ETAT2;
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
    public List<PosteModel> getPostes() {
            List<PosteModel> postes = new ArrayList<>();
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = dataSource.getConnection();
                String sql = "SELECT * FROM Poste";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    PosteModel poste = new PosteModel();
                    poste.setId(rs.getInt("id"));
                    poste.setEtat2(ETAT2.valueOf(rs.getString("etat2")));
                    poste.setConfiguration(rs.getString("configuration"));
                    poste.setUtilisateur_affecte(rs.getString("utilisateur_affecte"));

                    postes.add(poste);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la récupération des postes", e);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
                }
            }

            return postes;
        }

    @Override
    public PosteModel getPosteById(int id) {
        List<PosteModel> postes = new ArrayList<>(this.getJSONPostes());
        List<PosteModel> posteFromDB = new ArrayList<>(this.getPostes());
        postes.addAll(posteFromDB);
        return postes.stream()
                .filter(poste -> poste.getId() == id)
                .findFirst()
                .orElse(null);
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
    public PosteModel modifyPoste(PosteModel posteModel) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();

            String sql = "UPDATE Poste SET etat2 = ?, configuration = ?, utilisateur_affecte = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, posteModel.getEtat2().toString());
            stmt.setString(2, posteModel.getConfiguration());
            stmt.setString(3, posteModel.getUtilisateur_affecte());
            stmt.setInt(4, posteModel.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Poste avec l'ID " + posteModel.getId() + " introuvable.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du poste avec l'ID " + posteModel.getId(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
            }
        }

        return posteModel;
    }

    @Override
    public void deletePoste(int id) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Poste WHERE id = ?");
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("PosteInformatique avec l'ID " + id + " introuvable.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du PosteInformatique avec l'ID " + id, e);
        }
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
