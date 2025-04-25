package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class UtilisateurDAO extends Utilisateur{

    public UtilisateurDAO(int idUtilisateur, String nom, String prenom, float pointsFidelite) {
        super(idUtilisateur, nom, prenom, pointsFidelite);
    }

    //Ajouter
    public static void creerUtilisateurBDD(Utilisateur utilisateur) {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantUtilisateur) FROM Utilisateur";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantUtilisateur");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantUtilisateur")) + 1;

        //Récupération des infos
        utilisateur.setIdUtilisateur(id);
        String nom = utilisateur.getNom();
        String prenom = utilisateur.getPrenom();
        float pointsFidelite = utilisateur.getPointsFidelite();

        //Ajout
        requete = "INSERT INTO Utilisateur(identifiantUtilisateur, nom, prenom, pointsFidelite) VALUES (" +
                id + "," + nom + "," + prenom + "," + pointsFidelite + ");";
        requete(requete);
        List<Depot> posseder = utilisateur.getPosseder();
        for (Depot depot : posseder) {
            requete = "INSERT INTO posseder(identifiantUtilisateur, identifiantDepot) VALUES (" + utilisateur.getIdUtilisateur() + "," + depot.getIdentifiantDepot() + ");";
            requete(requete);
        }
        Set<Promotion> utiliser = utilisateur.getUtiliser();
        for (Promotion promotion : utiliser) {
            requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) VALUES (" + promotion.getIdPromotion() + "," + utilisateur.getIdUtilisateur() + ");";
            requete(requete);
        }
    }

    // Supprimer un utilisateur
    public static void supprimerUtilisateurBDD(Utilisateur utilisateur) {
        int id = utilisateur.getIdUtilisateur();
        String requete = "DELETE FROM utiliser WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
        requete = "DELETE FROM posseder WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
        requete = "DELETE FROM Utilisateur WHERE identifiantUtilisateur = " + id + ";";
        requete(requete);
    }

    public static void actualiserUtilisateurBDD(Utilisateur utilisateur, String instruction){
        int identifiantUtilisateur = utilisateur.getIdUtilisateur();
        String requete;
        if (instruction=="nom"){
            requete = "UPDATE Utilisateur SET nom = " + utilisateur.getNom() + ";";
            requete(requete);
        }
        if (instruction=="prenom"){
            requete = "UPDATE Utilisateur SET prenom = " + utilisateur.getPrenom() + ";";
            requete(requete);
        }
        if (instruction=="pointsFidelite"){
            requete = "UPDATE Utilisateur SET pointsFidelite = " + utilisateur.getPointsFidelite() + ";";
            requete(requete);
        }
        if (instruction=="posseder"){
            List<Depot> posseder = utilisateur.getPosseder();
            for (Depot depot : posseder){
                requete = "INSERT INTO posseder(identifiantDepot, identifiantUtilisateur) " +
                        "VALUES (" + depot.getIdentifiantDepot() + "," + identifiantUtilisateur + ");";
                requete(requete);
            }
        }
        if (instruction=="utiliser"){
            Set<Promotion> utiliser = utilisateur.getUtiliser();
            for (Promotion promotion : utiliser){
                requete = "INSERT INTO utiliser(identifiantPromotion, identifiantUtilisateur) " +
                        "VALUES (" + promotion.getIdPromotion() + "," + identifiantUtilisateur + ");";
                requete(requete);
            }
        }
    }
}
