package service;

import dao.CompetitionDAO;
import model.Competition;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ICompetitionServiceImple implements ICompetitionService {

    private static final List<String> LIEUX_VALIDES = Arrays.asList("Dakar", "Diamniadio", "Saly");

    private final CompetitionDAO competitionDAO = new CompetitionDAO();

    @Override
    public void ajouter(Competition c) throws SQLException {
        validerLieu(c.getLieu());
        competitionDAO.ajouter(c);
    }

    @Override
    public void modifier(Competition c) throws SQLException {
        validerLieu(c.getLieu());
        competitionDAO.modifier(c);
    }

    @Override
    public void supprimer(int idCompetition) throws SQLException {
        competitionDAO.supprimer(idCompetition);
    }

    @Override
    public Competition rechercherParId(int idCompetition) throws SQLException {
        return competitionDAO.rechercherParId(idCompetition);
    }

    @Override
    public List<Competition> rechercher(String motCle) throws SQLException {
        return competitionDAO.rechercher(motCle);
    }

    @Override
    public List<Competition> listerTous() throws SQLException {
        return competitionDAO.listerTous();
    }

    /**
     * Le lieu doit obligatoirement être Dakar, Diamniadio ou Saly (section 9 du cahier des charges).
     */
    private void validerLieu(String lieu) {
        boolean valide = LIEUX_VALIDES.stream().anyMatch(l -> l.equalsIgnoreCase(lieu));
        if (!valide) {
            throw new IllegalArgumentException("Lieu invalide. Choix possibles : " + LIEUX_VALIDES);
        }
    }
}
