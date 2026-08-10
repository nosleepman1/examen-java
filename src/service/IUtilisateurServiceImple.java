package service;

import dao.UtilisateurDAO;
import model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public class IUtilisateurServiceImple implements IUtilisateurService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    public void ajouter(Utilisateur u) throws SQLException {
        utilisateurDAO.ajouter(u);
    }

    @Override
    public void modifier(Utilisateur u) throws SQLException {
        utilisateurDAO.modifier(u);
    }

    @Override
    public void supprimer(int idUtilisateur) throws SQLException {
        utilisateurDAO.supprimer(idUtilisateur);
    }

    @Override
    public List<Utilisateur> rechercher(String motCle) throws SQLException {
        return utilisateurDAO.rechercher(motCle);
    }

    @Override
    public List<Utilisateur> listerTous() throws SQLException {
        return utilisateurDAO.listerTous();
    }
}
