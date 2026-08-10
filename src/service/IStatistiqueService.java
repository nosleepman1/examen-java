package service;

import java.sql.SQLException;

public interface IStatistiqueService {

    int nombrePays() throws SQLException;

    int nombreAthletes() throws SQLException;

    int nombreDisciplines() throws SQLException;

    int nombreCompetitions() throws SQLException;

    int nombreResultats() throws SQLException;
}
