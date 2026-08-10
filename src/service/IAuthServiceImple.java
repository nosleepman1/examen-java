package service;

import dao.UtilisateurDAO;
import model.Utilisateur;

import java.sql.SQLException;

public class IAuthServiceImple implements IAuthService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    public Utilisateur seConnecter(String login, String motDePasse) throws SQLException {
        return utilisateurDAO.rechercherParLogin(login, motDePasse);
    }
}
