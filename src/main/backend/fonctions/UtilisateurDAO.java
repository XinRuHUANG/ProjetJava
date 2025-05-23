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
                        "INSERT INTO utilisateur (nom, prenom, pointsFidelite) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getPrenom());
            stmt.setFloat(3, u.getPointsFidelite());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    u.setIdUtilisateur(generatedKeys.getInt(1));
                } else {
                    throw new IllegalStateException("Erreur lors de la récupération de l'ID utilisateur !");
                }
            }
        }
    }


    public static void actualiserUtilisateurBDD(Utilisateur u, String... champs) throws Exception {
        for (var champ : champs) {
            String sql = "UPDATE utilisateur SET ";
            switch (champ) {
                case "nom" -> sql += "nom = '" + u.getNom() + "'";
                case "prenom" -> sql += "prenom = '" + u.getPrenom() + "'";
                case "pointsFidelite" -> sql += "pointsFidelite = " + u.getPointsFidelite();
                default -> throw new IllegalArgumentException("Champ inconnu : " + champ);
            }
            sql += " WHERE identifiantUtilisateur = " + u.getIdUtilisateur() + ";";
            requete(sql);
        }
    }

    public static void supprimerUtilisateurBDD(Utilisateur u) throws Exception {
        requete(
                "DELETE FROM utilisateur WHERE identifiantUtilisateur = "
                        + u.getIdUtilisateur()
                        + ";"
        );
    }

    public static Utilisateur lireUtilisateurBDD(int id) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT nom, prenom, pointsFidelite FROM utilisateur WHERE identifiantUtilisateur = "
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
                "SELECT identifiantUtilisateur FROM utiliser WHERE identifiantPromotion = " + idPromo + ";",
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
                        "FROM utilisateur u " +
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

    public static void utiliserPoints(Utilisateur utilisateur, Promotion promotion) throws Exception {
        if (utilisateur.getPointsFidelite() < promotion.getPointsRequis()) {
            throw new IllegalStateException("Pas assez de points pour utiliser cette promotion !");
        }

        // Déduire les points
        utilisateur.setPointsFidelite(utilisateur.getPointsFidelite() - promotion.getPointsRequis());

        // Mettre à jour en base
        requete(
                "UPDATE utilisateur SET pointsFidelite = " + utilisateur.getPointsFidelite()
                        + " WHERE identifiantUtilisateur = " + utilisateur.getIdUtilisateur() + ";"
        );

        // Ajouter dans la table d'association "utiliser"
        requete(
                "INSERT INTO utiliser (identifiantUtilisateur, identifiantPromotion) VALUES ("
                        + utilisateur.getIdUtilisateur() + ", " + promotion.getIdPromotion() + ");"
        );
    }

    public static Set<Promotion> consulterHistorique(Utilisateur utilisateur) throws Exception {
        var rows = requeteAvecAffichage(
                "SELECT identifiantPromotion FROM utiliser WHERE identifiantUtilisateur = " + utilisateur.getIdUtilisateur() + ";",
                new ArrayList<>(Arrays.asList("identifiantPromotion"))
        );

        Set<Promotion> historique = new HashSet<>();
        for (var row : rows) {
            int idPromo = Integer.parseInt(row.get("identifiantPromotion"));
            Promotion promo = PromotionDAO.lirePromotionBDD(idPromo);
            if (promo != null) {
                historique.add(promo);
            }
        }
        return historique;
    }

}
