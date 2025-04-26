package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CommerceDAO extends Commerce {

    public CommerceDAO(int identifiantCommerce, String nom, List<CentreDeTri> commercer, List<Contrat> contrats, Set<CategorieDeProduits> proposer) {
        super(identifiantCommerce, nom, commercer, contrats, proposer);
    }

    public static void ajouterCommerceBDD(Commerce commerce) throws SQLException {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCommerce) FROM commerce;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCommerce");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantCommerce");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        // Mise à jour de l'identifiant dans l'objet Java
        commerce.setIdentifiantCommerce(id);
        String nom = commerce.getNom();
        List<CentreDeTri> commercer = commerce.getCommercer();
        Set<CategorieDeProduits> proposer = commerce.getProposer();
        List<Contrat> contrats = commerce.getContrat();

        // Insertion dans la BDD
        requete = "INSERT INTO commerce(identifiantCommerce, nom) " +
                "VALUES (" + id + ", '" + nom + "');";
        requete(requete);

        if (commercer != null && contrats != null) {
            int n = Math.min(commercer.size(), contrats.size());
            for (int k = 0; k < n; k++) {
                requete = "INSERT INTO commercer(identifiantCommerce, identifiantCentreDeTri, identifiantContrat) " +
                        "VALUES (" + id + ", " +
                        commercer.get(k).getIdCentreDeTri() + ", " +
                        contrats.get(k).getIdContrat() + ");";
                requete(requete);
            }
        }
        if (commerce.getProposer() != null) {
            for (CategorieDeProduits cat : commerce.getProposer()) {
                requete = "INSERT INTO proposer(identifiantCategorieDeProduits, identifiantCommerce) " +
                        "VALUES (" + cat.getIdentifiantCategorie() + ", " + id + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerCommerceBDD(Commerce commerce) {
        int id = commerce.getIdentifiantCommerce();
        String requete = "DELETE FROM commercer WHERE identifiantCommerce = " + id + ";";
        requete(requete);
        requete = "DELETE FROM proposer WHERE identifiantCommerce = " + id + ";";
        requete(requete);
        requete = "DELETE FROM commerce WHERE identifiantCommerce = " + id + ";";
        requete(requete);
    }

    public static void actualiserCommerceBDD(Commerce commerce, String instruction) {
        int id = commerce.getIdentifiantCommerce();
        String requete;
        if (instruction.equals("nom")) {
            requete = "UPDATE commerce SET nom = '" + commerce.getNom() + "' WHERE identifiantCommerce = " + id + ";";
            requete(requete);
        }
        if (instruction.equals("commercer") || instruction.equals("contrats")) {
            List<CentreDeTri> commercer = commerce.getCommercer();
            List<Contrat> contrats = commerce.getContrat();
            requete = "DELETE FROM commercer WHERE identifiantCommerce = " + id + ";";
            requete(requete);
            int n = commercer.size();
            for (int k = 0; k < n; k++) {
                requete = "INSERT INTO commercer(identifiantCommerce, identifiantCentreDeTri, identifiantContrat) VALUES (" +
                        id + ", " + commercer.get(k).getIdCentreDeTri() + ", " + contrats.get(k).getIdContrat() + ");";
                requete(requete);
            }
        }
        if (instruction.equals("proposer")) {
            Set<CategorieDeProduits> proposer = commerce.getProposer();
            requete = "DELETE FROM proposer WHERE identifiantCommerce = " + id + ";";
            requete(requete);
            for (CategorieDeProduits categorieDeProduits : proposer) {
                requete = "INSERT INTO proposer(identifiantCommerce, identifiantCategorieDeProduits) VALUES (" +
                        id + ", " + categorieDeProduits.getIdentifiantCategorie() + ");";
                requete(requete);
            }
        }
    }
}
