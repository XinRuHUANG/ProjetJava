package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class UtilisateurDAO extends Utilisateur {

    public UtilisateurDAO(int idUtilisateur, String nom, String prenom, float pointsFidelite) {
        super(idUtilisateur, nom, prenom, pointsFidelite);
    }

    public static void ajouterUtilisateurBDD(Utilisateur utilisateur) throws SQLException {
        String requete = "SELECT MAX(identifiantUtilisateur) FROM utilisateur;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantUtilisateur");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantUtilisateur");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        utilisateur.setIdUtilisateur(id);
        String nom = utilisateur.getNom();
        String prenom = utilisateur.getPrenom();
        float pointsFidelite = utilisateur.getPointsFidelite();

        requete = "INSERT INTO utilisateur(identifiantUtilisateur, nom, prenom, pointsFidelite) VALUES (" +
                id + ", '" + nom + "', '" + prenom + "', " + pointsFidelite + ");";
        requete(requete);

        if (utilisateur.getPosseder() != null) {
            for (Depot d : utilisateur.getPosseder()) {
                requete = "INSERT INTO posseder(identifiantUtilisateur, identifiantDepot) VALUES (" +
                        id + "," + d.getIdentifiantDepot() + ");";
                requete(requete);
            }
        }

        if (utilisateur.getUtiliser() != null) {
            for (Promotion p : utilisateur.getUtiliser()) {
                requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES (" +
                        p.getIdPromotion() + "," + id + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerUtilisateurBDD(Utilisateur utilisateur) {
        int id = utilisateur.getIdUtilisateur();
        String requete = "DELETE FROM utiliser WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
        requete = "DELETE FROM posseder WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
        requete = "DELETE FROM utilisateur WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
    }

    public static void actualiserUtilisateurBDD(Utilisateur utilisateur, String instruction) {
        int id = utilisateur.getIdUtilisateur();
        String requete;
        if (instruction.equals("nom")) {
            requete = "UPDATE utilisateur SET nom = '" + utilisateur.getNom() + "' WHERE identifiantUtilisateur = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("prenom")) {
            requete = "UPDATE utilisateur SET prenom = '" + utilisateur.getPrenom() + "' WHERE identifiantUtilisateur = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("pointsFidelite")) {
            requete = "UPDATE utilisateur SET pointsFidelite = " + utilisateur.getPointsFidelite() + " WHERE identifiantUtilisateur = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("posseder")) {
            requete = "DELETE FROM posseder WHERE identifiantUtilisateur = " + id + ";";
            requete(requete);
            List<Depot> posseder = utilisateur.getPosseder();
            for (Depot depot : posseder) {
                requete = "INSERT INTO posseder(identifiantUtilisateur, identifiantDepot) VALUES (" + id + "," + depot.getIdentifiantDepot() + ");";
                requete(requete);
            }
        }
        if (instruction.equals("utiliser")) {
            requete = "DELETE FROM utiliser WHERE identifiantUtilisateur = " + id + ";";
            requete(requete);
            Set<Promotion> utiliser = utilisateur.getUtiliser();
            for (Promotion promotion : utiliser) {
                requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES (" + promotion.getIdPromotion() + "," + id + ");";
                requete(requete);
            }
        }
    }
}
