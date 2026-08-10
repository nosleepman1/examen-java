package service;

import model.Utilisateur;

import java.sql.SQLException;

public interface IAuthService {

    /**
     * Retourne l'utilisateur si login/mot de passe correspondent, sinon null.
     */
    Utilisateur seConnecter(String login, String motDePasse) throws SQLException;
}
