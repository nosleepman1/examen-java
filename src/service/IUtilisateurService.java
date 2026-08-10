package service;

import model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface IUtilisateurService {

    void ajouter(Utilisateur u) throws SQLException;

    void modifier(Utilisateur u) throws SQLException;

    void supprimer(int idUtilisateur) throws SQLException;

    List<Utilisateur> rechercher(String motCle) throws SQLException;

    List<Utilisateur> listerTous() throws SQLException;
}
