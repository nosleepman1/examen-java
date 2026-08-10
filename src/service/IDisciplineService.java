package service;

import model.Discipline;

import java.sql.SQLException;
import java.util.List;

public interface IDisciplineService {

    void ajouter(Discipline d) throws SQLException;

    void modifier(Discipline d) throws SQLException;

    void supprimer(int idDiscipline) throws SQLException;

    Discipline rechercherParId(int idDiscipline) throws SQLException;

    List<Discipline> rechercher(String motCle) throws SQLException;

    List<Discipline> listerTous() throws SQLException;
}
