package service;

import dao.AthleteDAO;
import dao.CompetitionDAO;
import dao.DisciplineDAO;
import dao.PaysDAO;
import dao.ResultatDAO;

import java.sql.SQLException;

public class IStatistiqueServiceImple implements IStatistiqueService {

    private final PaysDAO paysDAO = new PaysDAO();
    private final AthleteDAO athleteDAO = new AthleteDAO();
    private final DisciplineDAO disciplineDAO = new DisciplineDAO();
    private final CompetitionDAO competitionDAO = new CompetitionDAO();
    private final ResultatDAO resultatDAO = new ResultatDAO();

    @Override
    public int nombrePays() throws SQLException {
        return (int) paysDAO.listerTous().stream().count();
    }

    @Override
    public int nombreAthletes() throws SQLException {
        return (int) athleteDAO.listerTous().stream().count();
    }

    @Override
    public int nombreDisciplines() throws SQLException {
        return (int) disciplineDAO.listerTous().stream().count();
    }

    @Override
    public int nombreCompetitions() throws SQLException {
        return (int) competitionDAO.listerTous().stream().count();
    }

    @Override
    public int nombreResultats() throws SQLException {
        return (int) resultatDAO.listerTous().stream().count();
    }
}
