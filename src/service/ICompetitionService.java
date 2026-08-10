package service;

import model.Competition;

import java.sql.SQLException;
import java.util.List;

public interface ICompetitionService {

    void ajouter(Competition c) throws SQLException;

    void modifier(Competition c) throws SQLException;

    void supprimer(int idCompetition) throws SQLException;

    Competition rechercherParId(int idCompetition) throws SQLException;

    List<Competition> rechercher(String motCle) throws SQLException;

    List<Competition> listerTous() throws SQLException;
}
