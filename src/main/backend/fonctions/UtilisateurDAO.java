// UtilisateurDAO.java
package main.backend.fonctions;

import main.outils.connexionSQL;

import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class UtilisateurDAO {
    public static void ajouterUtilisateurBDD(Utilisateur u) throws Exception {
        requete(
                "INSERT INTO utilisateur (nom, prenom, pointsFidelite) VALUES ('"
                        + u.getNom() + "', '"
                        + u.getPrenom() + "', "
                        + u.getPointsFidelite()
                        + ");"
        );
        var rows = requeteAvecAffichage(
                "SELECT LAST_INSERT_ID() AS id;",
                new ArrayList<>(Arrays.asList("id"))
        );
        u.setIdUtilisateur(Integer.parseInt(rows.get(0).get("id")));
    }

    public static void actualiserUtilisateurBDD(Utilisateur u, String... champs) throws Exception {
        for (var champ : champs) {
            switch (champ) {
                case "pointsFidelite" -> requete(
                        "UPDATE utilisateur SET pointsFidelite = "
                                + u.getPointsFidelite()
                                + " WHERE identifiantUtilisateur = " + u.getIdUtilisateur() + ";"
                );
                default -> throw new IllegalArgumentException("Champ inconnu : " + champ);
            }
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

}
