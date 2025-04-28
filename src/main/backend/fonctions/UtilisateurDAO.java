// UtilisateurDAO.java
package main.backend.fonctions;

import main.outils.connexionSQL;

import java.sql.*;
import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class UtilisateurDAO {
    public static void ajouterUtilisateurBDD(Utilisateur u) throws Exception {
        try (
                Connection conn = DriverManager.getConnection(connexionSQL.url, connexionSQL.user, connexionSQL.password);
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO Utilisateur (nom, prenom, pointsFidelite) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getPrenom());
            stmt.setFloat(3, u.getPointsFidelite());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setIdentifiantUtilisateur(generatedKeys.getInt(1));
                } else {
                    throw new IllegalStateException("Erreur lors de la récupération de l'ID utilisateur !");
                }
            }
        }
    }


    public static void actualiserUtilisateurBDD(Utilisateur u, String... champs) throws Exception {
        for (var champ : champs) {
            switch (champ) {
                case "pointsFidelite" -> requete(
                        "UPDATE Utilisateur SET pointsFidelite = "
                                + u.getPointsFidelite()
                                + " WHERE identifiantUtilisateur = " + u.getIdentifiantUtilisateur() + ";"
                );
                default -> throw new IllegalArgumentException("Champ inconnu : " + champ);
            }
        }
    }

    public static void supprimerUtilisateurBDD(Utilisateur u) throws Exception {
        requete(
                "DELETE FROM Utilisateur WHERE identifiantUtilisateur = "
                        + u.getIdentifiantUtilisateur()
                        + ";"
        );
    }

    public static Utilisateur lireUtilisateurBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT nom, prenom, pointsFidelite FROM Utilisateur WHERE identifiantUtilisateur = "
                        + id + ";",
                new ArrayList<>(Arrays.asList("nom", "prenom", "pointsFidelite"))
        );
        if (rows.isEmpty()) return null;
        var r = rows.get(0);
        Utilisateur u = new Utilisateur(
                id,
                r.get("nom"),
                r.get("prenom"),
                Float.parseFloat(r.get("pointsFidelite"))
        );
        // associations à charger si besoin…
        return u;
    }

    // méthodes d’association…
    public static Set<Utilisateur> lireUtilisateursParPromotion(int idPromo) throws Exception {
        // on récupère simplement les IDs depuis la table d’association 'utiliser'
        var rows = connexionSQL.requeteAvecAffichage(
                "SELECT identifiantUtilisateur FROM Utiliser WHERE identifiantPromotion = " + idPromo + ";",
                new ArrayList<>(Arrays.asList("identifiantUtilisateur"))
        );

        Set<Utilisateur> result = new HashSet<>();
        for (var row : rows) {
            int idU = Integer.parseInt(row.get("identifiantUtilisateur"));
            // on récupère l’utilisateur complet via la DAO
            Utilisateur u = lireUtilisateurBDD(idU);
            if (u != null) {
                result.add(u);
            }
        }
        return result;
    }

    public static Utilisateur lireUtilisateurParDepot(int idDepot) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT u.identifiantUtilisateur, u.nom, u.prenom, u.pointsFidelite " +
                        "FROM Utilisateur u " +
                        "JOIN depot d ON u.identifiantUtilisateur = d.identifiantUtilisateur " +
                        "WHERE d.identifiantDepot = " + idDepot + ";",
                new ArrayList<String>(Arrays.asList(
                        "identifiantUtilisateur",
                        "nom",
                        "prenom",
                        "pointsFidelite"
                ))
        );
        if (rows.isEmpty()) return null;
        var row = rows.get(0);
        int    id  = Integer.parseInt(row.get("identifiantUtilisateur"));
        String nom = row.get("nom");
        String pre = row.get("prenom");
        float  pts = Float.parseFloat(row.get("pointsFidelite"));
        var u = new Utilisateur(id, nom, pre, pts);
        u.setPosseder(new ArrayList<>());
        u.setUtiliser(new HashSet<>());
        return u;
    }

}
