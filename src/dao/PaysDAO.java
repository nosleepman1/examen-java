package dao;

import model.Pays;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaysDAO {

    public void ajouter(Pays p) throws SQLException {
        String sql = "INSERT INTO pays (nom_pays, continent) VALUES (?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNomPays());
            ps.setString(2, p.getContinent());
            ps.executeUpdate();
        }
    }

    public void modifier(Pays p) throws SQLException {
        String sql = "UPDATE pays SET nom_pays=?, continent=? WHERE id_pays=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNomPays());
            ps.setString(2, p.getContinent());
            ps.setInt(3, p.getIdPays());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idPays) throws SQLException {
        String sql = "DELETE FROM pays WHERE id_pays=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPays);
            ps.executeUpdate();
        }
    }

    public Pays rechercherParId(int idPays) throws SQLException {
        String sql = "SELECT * FROM pays WHERE id_pays=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPays);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper(rs) : null;
            }
        }
    }

    public List<Pays> rechercher(String motCle) throws SQLException {
        String sql = "SELECT * FROM pays WHERE nom_pays LIKE ? OR continent LIKE ?";
        List<Pays> resultat = new ArrayList<>();
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

    public List<Pays> listerTous() throws SQLException {
        String sql = "SELECT * FROM pays ORDER BY nom_pays";
        List<Pays> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private Pays mapper(ResultSet rs) throws SQLException {
        return new Pays(rs.getInt("id_pays"), rs.getString("nom_pays"), rs.getString("continent"));
    }
}
