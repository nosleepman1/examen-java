package service;

import dao.DisciplineDAO;
import model.Discipline;

import java.sql.SQLException;
import java.util.List;

public class IDisciplineServiceImple implements IDisciplineService {

    private final DisciplineDAO disciplineDAO = new DisciplineDAO();

    @Override
    public void ajouter(Discipline d) throws SQLException {
        disciplineDAO.ajouter(d);
    }

    @Override
    public void modifier(Discipline d) throws SQLException {
        disciplineDAO.modifier(d);
    }

    @Override
    public void supprimer(int idDiscipline) throws SQLException {
        disciplineDAO.supprimer(idDiscipline);
    }

    @Override
    public Discipline rechercherParId(int idDiscipline) throws SQLException {
        return disciplineDAO.rechercherParId(idDiscipline);
    }

    @Override
    public List<Discipline> rechercher(String motCle) throws SQLException {
        return disciplineDAO.rechercher(motCle);
    }

    @Override
    public List<Discipline> listerTous() throws SQLException {
        return disciplineDAO.listerTous();
    }
}
