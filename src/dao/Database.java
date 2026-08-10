package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire centralisant l'ouverture de connexion à la base joj_dakar2026.
 * Chaque DAO ouvre sa propre connexion via getConnection() puis la referme lui-même.
 */
public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/joj_dakar2026?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
