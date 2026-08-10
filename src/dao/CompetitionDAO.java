package dao;

import model.Competition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDAO {

    private static final String SELECT_BASE =
            "SELECT c.*, d.nom_discipline " +
            "FROM competition c " +
            "JOIN discipline d ON c.id_discipline = d.id_discipline ";

    public void ajouter(Competition c) throws SQLException {
        String sql = "INSERT INTO competition (nom_competition, date_competition, lieu, id_discipline) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, c);
            ps.executeUpdate();
        }
    }

    public void modifier(Competition c) throws SQLException {
        String sql = "UPDATE competition SET nom_competition=?, date_competition=?, lieu=?, id_discipline=? " +
                "WHERE id_competition=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, c);
            ps.setInt(5, c.getIdCompetition());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idCompetition) throws SQLException {
        String sql = "DELETE FROM competition WHERE id_competition=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCompetition);
            ps.executeUpdate();
        }
    }

    public Competition rechercherParId(int idCompetition) throws SQLException {
        String sql = SELECT_BASE + "WHERE c.id_competition=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCompetition);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper(rs) : null;
            }
        }
    }

    public List<Competition> rechercher(String motCle) throws SQLException {
        String sql = SELECT_BASE + "WHERE c.nom_competition LIKE ? OR c.lieu LIKE ?";
        List<Competition> resultat = new ArrayList<>();
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

    public List<Competition> listerTous() throws SQLException {
        String sql = SELECT_BASE + "ORDER BY c.date_competition";
        List<Competition> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private void remplirParametres(PreparedStatement ps, Competition c) throws SQLException {
        ps.setString(1, c.getNomCompetition());
        ps.setDate(2, java.sql.Date.valueOf(c.getDateCompetition()));
        ps.setString(3, c.getLieu());
        ps.setInt(4, c.getIdDiscipline());
    }

    private Competition mapper(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate("date_competition").toLocalDate();
        Competition c = new Competition(
                rs.getInt("id_competition"),
                rs.getString("nom_competition"),
                date,
                rs.getString("lieu"),
                rs.getInt("id_discipline")
        );
        c.setNomDiscipline(rs.getString("nom_discipline"));
        return c;
    }
}
