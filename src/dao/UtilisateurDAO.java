package dao;

import model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public void ajouter(Utilisateur u) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom_complet, login, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getNomComplet());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getMotDePasse());
            ps.setString(4, u.getRole());
            ps.executeUpdate();
        }
    }

    public void modifier(Utilisateur u) throws SQLException {
        String sql = "UPDATE utilisateur SET nom_complet=?, login=?, mot_de_passe=?, role=? WHERE id_utilisateur=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getNomComplet());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getMotDePasse());
            ps.setString(4, u.getRole());
            ps.setInt(5, u.getIdUtilisateur());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idUtilisateur) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE id_utilisateur=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();
        }
    }

    public Utilisateur rechercherParLogin(String login, String motDePasse) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE login=? AND mot_de_passe=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, motDePasse);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper(rs) : null;
            }
        }
    }

    public List<Utilisateur> rechercher(String motCle) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE nom_complet LIKE ? OR login LIKE ?";
        List<Utilisateur> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            String motif = "%" + motCle + "%";
            ps.setString(1, motif);
            ps.setString(2, motif);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.add(mapper(rs));
                }
            }
        }
        return resultat;
    }

    public List<Utilisateur> listerTous() throws SQLException {
        String sql = "SELECT * FROM utilisateur ORDER BY id_utilisateur";
        List<Utilisateur> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private Utilisateur mapper(ResultSet rs) throws SQLException {
        return new Utilisateur(
                rs.getInt("id_utilisateur"),
                rs.getString("nom_complet"),
                rs.getString("login"),
                rs.getString("mot_de_passe"),
                rs.getString("role")
        );
    }
}
