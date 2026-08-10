package dao;

import model.Athlete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AthleteDAO {

    // Jointure utilisée par toutes les méthodes de lecture pour récupérer les noms lisibles
    private static final String SELECT_BASE =
            "SELECT a.*, p.nom_pays, d.nom_discipline " +
            "FROM athlete a " +
            "JOIN pays p ON a.id_pays = p.id_pays " +
            "JOIN discipline d ON a.id_discipline = d.id_discipline ";

    public void ajouter(Athlete a) throws SQLException {
        String sql = "INSERT INTO athlete (nom, prenom, sexe, date_naissance, id_pays, id_discipline) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, a);
            ps.executeUpdate();
        }
    }

    public void modifier(Athlete a) throws SQLException {
        String sql = "UPDATE athlete SET nom=?, prenom=?, sexe=?, date_naissance=?, id_pays=?, id_discipline=? " +
                "WHERE id_athlete=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            remplirParametres(ps, a);
            ps.setInt(7, a.getIdAthlete());
            ps.executeUpdate();
        }
    }

    public void supprimer(int idAthlete) throws SQLException {
        String sql = "DELETE FROM athlete WHERE id_athlete=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idAthlete);
            ps.executeUpdate();
        }
    }

    public Athlete rechercherParId(int idAthlete) throws SQLException {
        String sql = SELECT_BASE + "WHERE a.id_athlete=?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idAthlete);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapper(rs) : null;
            }
        }
    }

    public List<Athlete> rechercher(String motCle) throws SQLException {
        String sql = SELECT_BASE + "WHERE a.nom LIKE ? OR a.prenom LIKE ?";
        List<Athlete> resultat = new ArrayList<>();
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

    public List<Athlete> listerTous() throws SQLException {
        String sql = SELECT_BASE + "ORDER BY a.nom";
        List<Athlete> resultat = new ArrayList<>();
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(mapper(rs));
            }
        }
        return resultat;
    }

    private void remplirParametres(PreparedStatement ps, Athlete a) throws SQLException {
        ps.setString(1, a.getNom());
        ps.setString(2, a.getPrenom());
        ps.setString(3, a.getSexe());
        ps.setDate(4, java.sql.Date.valueOf(a.getDateNaissance()));
        ps.setInt(5, a.getIdPays());
        ps.setInt(6, a.getIdDiscipline());
    }

    private Athlete mapper(ResultSet rs) throws SQLException {
        LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate();
        Athlete a = new Athlete(
                rs.getInt("id_athlete"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("sexe"),
                dateNaissance,
                rs.getInt("id_pays"),
                rs.getInt("id_discipline")
        );
        a.setNomPays(rs.getString("nom_pays"));
        a.setNomDiscipline(rs.getString("nom_discipline"));
        return a;
    }
}
