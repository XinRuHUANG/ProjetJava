package main.backend.fonctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class CentreDeTriDAO extends CentreDeTri {

    public CentreDeTriDAO(int idCentreDeTri, String nom, String adresse, Set<PoubelleIntelligente> poubelles, List<Commerce> commerce, List<Contrat> contrats) {
        super(idCentreDeTri, nom, adresse, poubelles, commerce, contrats);
    }

    public static void ajouterCentreBDD(CentreDeTri centreDeTri) throws SQLException {
        // Création de l'identifiant
        String requete = "SELECT MAX(identifiantCentreDeTri) FROM centredetri;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantCentreDeTri");

        List<HashMap<String,String>> infos =
                requeteAvecAffichage(requete, attributs);
        String maxStr = infos.get(0).get("identifiantCentreDeTri");
        int previousMax = (maxStr != null) ? Integer.parseInt(maxStr) : 0;
        int id = previousMax + 1;

        // Récupération des infos
        centreDeTri.setIdCentreDeTri(id);
        String nom = centreDeTri.getNom();
        String adresse = centreDeTri.getAdresse();
        Set<PoubelleIntelligente> poubelleIntelligente = centreDeTri.getPoubelles();
        List<Commerce> commerces = centreDeTri.getCommerce();
        List<Contrat> contrats = centreDeTri.getContrats();

        // Ajout du nouveau centre dans la table CentreDeTri
        requete = "INSERT INTO centredetri(identifiantCentreDeTri, nom, adresse) " +
                "VALUES (" + id + ", '" + nom + "', '" + adresse + "');";
        requete(requete);

        // Ajout des liaisons entre le centre de tri et les commerces
        if (commerces != null && contrats != null) {
            int n = Math.min(commerces.size(), contrats.size());
            for (int k = 0; k < n; k++) {
                requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                        "VALUES (" + id + ", " +
                        commerces.get(k).getIdentifiantCommerce() + ", " +
                        contrats.get(k).getIdContrat() + ");";
                requete(requete);
            }
        }

        // Ajout des liaisons entre les poubelles et le centre de tri
        if (centreDeTri.getPoubelles() != null) {
            for (PoubelleIntelligente p : centreDeTri.getPoubelles()) {
                requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                        "VALUES (" + id + ", " + p.getIdentifiantPoubelle() + ");";
                requete(requete);
            }
        }
    }

    public static void actualiserCentreBDD(CentreDeTri centredetri, String instruction) {
        int identifiantCentreDeTri = centredetri.getIdCentreDeTri();
        String requete;
        if (instruction.equals("nom")) {
            requete = "UPDATE centredetri SET nom = '" + centredetri.getNom() + "' " +
                    "WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
            requete(requete);
        }
        if (instruction.equals("adresse")) {
            requete = "UPDATE centredetri SET adresse = '" + centredetri.getAdresse() + "' " +
                    "WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
            requete(requete);
        }
        if (instruction.equals("poubelles")) {
            requete = "DELETE FROM gerer WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
            requete(requete);
            Set<PoubelleIntelligente> poubelles = centredetri.getPoubelles();
            for (PoubelleIntelligente poubelle : poubelles) {
                int identifiantPoubelleIntelligente = poubelle.getIdentifiantPoubelle();
                requete = "INSERT INTO gerer(identifiantCentreDeTri, identifiantPoubelleIntelligente) " +
                        "VALUES (" + identifiantCentreDeTri + ", " + identifiantPoubelleIntelligente + ");";
                requete(requete);
            }
        }
        if (instruction.equals("commerces") || instruction.equals("contrats")) {
            requete = "DELETE FROM commercer WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
            requete(requete);
            List<Commerce> commerce = centredetri.getCommerce();
            List<Contrat> contrats = centredetri.getContrats();
            int n = commerce.size();
            for (int k = 0; k < n; k++) {
                int identifiantCommerce = commerce.get(k).getIdentifiantCommerce();
                int identifiantContrat = contrats.get(k).getIdContrat();
                requete = "INSERT INTO commercer(identifiantCentreDeTri, identifiantCommerce, identifiantContrat) " +
                        "VALUES (" + identifiantCentreDeTri + ", " + identifiantCommerce + ", " + identifiantContrat + ");";
                requete(requete);
            }
        }
    }

    public static void supprimerCentreBDD(CentreDeTri centredetri) {
        int identifiantCentreDeTri = centredetri.getIdCentreDeTri();
        String requete = "DELETE FROM commercer WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
        requete(requete);
        requete = "DELETE FROM gerer WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
        requete(requete);
        requete = "DELETE FROM centredetri WHERE identifiantCentreDeTri = " + identifiantCentreDeTri + ";";
        requete(requete);
    }
}
