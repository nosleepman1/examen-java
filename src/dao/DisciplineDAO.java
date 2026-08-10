package dao;

import model.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO {

    public void ajouter(Discipline d) throws SQLException {
        String sql = "INSERT INTO discipline (nom_discipline, description) VALUES (?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, d.getNomDiscipline());
            ps.setString(2, d.getDescription());
            ps.executeUpdate();
        }
    }

    public void modifier(Discipline d) throws SQLException {
        String sql = "UPDATE discipline SET nom_discipline=?, description=? WHERE id_discipline=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, d.getNomDiscipline());
            ps.setString(2, d.getDescription());
            ps.setInt(3, d.getIdDiscipline());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idDiscipline) throws SQLException {
        String sql = "DELETE FROM discipline WHERE id_discipline=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idDiscipline);
            ps.executeUpdate();
        }
    }

    public Discipline rechercherParId(int idDiscipline) throws SQLException {
        String sql = "SELECT * FROM discipline WHERE id_discipline=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idDiscipline);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper(rs) : null;
            }
        }
    }

    public List<Discipline> rechercher(String motCle) throws SQLException {
        String sql = "SELECT * FROM discipline WHERE nom_discipline LIKE ?";
        List<Discipline> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + motCle + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.add(mapper(rs));
                }
            }
        }
        return resultat;
    }

    public List<Discipline> listerTous() throws SQLException {
        String sql = "SELECT * FROM discipline ORDER BY nom_discipline";
        List<Discipline> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private Discipline mapper(ResultSet rs) throws SQLException {
        return new Discipline(rs.getInt("id_discipline"), rs.getString("nom_discipline"), rs.getString("description"));
    }
}
