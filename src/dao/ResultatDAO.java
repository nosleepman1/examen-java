package dao;

import model.Resultat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultatDAO {

    private static final String SELECT_BASE =
            "SELECT r.*, a.nom AS nom_athlete, a.prenom AS prenom_athlete, p.nom_pays, c.nom_competition " +
            "FROM resultat r " +
            "JOIN athlete a ON r.id_athlete = a.id_athlete " +
            "JOIN pays p ON a.id_pays = p.id_pays " +
            "JOIN competition c ON r.id_competition = c.id_competition ";

    public void ajouter(Resultat r) throws SQLException {
        String sql = "INSERT INTO resultat (id_athlete, id_competition, score, rang) VALUES (?, ?, ?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, r);
            ps.executeUpdate();
        }
    }

    public void modifier(Resultat r) throws SQLException {
        String sql = "UPDATE resultat SET id_athlete=?, id_competition=?, score=?, rang=? WHERE id_resultat=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, r);
            ps.setInt(5, r.getIdResultat());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idResultat) throws SQLException {
        String sql = "DELETE FROM resultat WHERE id_resultat=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idResultat);
            ps.executeUpdate();
        }
    }

    public List<Resultat> listerParCompetition(int idCompetition) throws SQLException {
        String sql = SELECT_BASE + "WHERE r.id_competition=?";
        List<Resultat> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCompetition);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.add(mapper(rs));
                }
            }
        }
        return resultat;
    }

    public List<Resultat> listerTous() throws SQLException {
        String sql = SELECT_BASE + "ORDER BY r.id_resultat";
        List<Resultat> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private void remplirParametres(PreparedStatement ps, Resultat r) throws SQLException {
        ps.setInt(1, r.getIdAthlete());
        ps.setInt(2, r.getIdCompetition());
        ps.setDouble(3, r.getScore());
        ps.setInt(4, r.getRang());
    }

    private Resultat mapper(ResultSet rs) throws SQLException {
        Resultat r = new Resultat(
                rs.getInt("id_resultat"),
                rs.getInt("id_athlete"),
                rs.getInt("id_competition"),
                rs.getDouble("score"),
                rs.getInt("rang")
        );
        r.setNomAthlete(rs.getString("prenom_athlete") + " " + rs.getString("nom_athlete"));
        r.setNomPaysAthlete(rs.getString("nom_pays"));
        r.setNomCompetition(rs.getString("nom_competition"));
        return r;
    }
}
