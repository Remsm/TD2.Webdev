package fr.isen.ticketapp.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.ticketapp.interfaces.models.UtilisateurModel;
import fr.isen.ticketapp.interfaces.models.enums.ACTIF;
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
    public List<UtilisateurModel> getUtilisateurs() {
        List<UtilisateurModel> utilisateurs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT * FROM utilisateur";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UtilisateurModel utilisateur = new UtilisateurModel();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));
                utilisateur.setDerniere_connexion(rs.getString("derniere_connexion"));
                utilisateur.setStatut(ACTIF.valueOf(rs.getString("statut")));
                utilisateur.setRole(rs.getString("role"));

                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources", e);
            }
        }

        return utilisateurs;
    }

    @Override
    public UtilisateurModel getUtilisateurtById(int id) {
        List<UtilisateurModel> utilisateurs = new ArrayList<>(this.getJSONUtilisateurs());
        List<UtilisateurModel> utilisateursFromDB = new ArrayList<>(this.getUtilisateurs());
        utilisateurs.addAll(utilisateursFromDB);
        return utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getId() == id)
                .findFirst()
                .orElse(null);
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
    public UtilisateurModel modifyUtilisateur(UtilisateurModel utilisateurModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();

            String sql = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ?, derniere_connexion = ?, statut = ?, role = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, utilisateurModel.getNom());
            stmt.setString(2, utilisateurModel.getEmail());
            stmt.setString(3, utilisateurModel.getMot_de_passe());
            stmt.setString(4, utilisateurModel.getDerniere_connexion());
            stmt.setString(5, utilisateurModel.getStatut().toString());
            stmt.setString(6, utilisateurModel.getRole());
            stmt.setInt(7, utilisateurModel.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Utilisateur avec l'ID " + utilisateurModel.getId() + " introuvable.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur avec l'ID " + utilisateurModel.getId(), e);
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
    public void deleteUtilisateur(int id) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM utilisateur WHERE id = ?");
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Utilisateur avec l'ID " + id + " introuvable.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur avec l'ID " + id, e);
        }
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
