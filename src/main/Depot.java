package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static main.outils.connexionSQL.requete;
import static main.TypeDéchetEnumEtConstantes.TypeDechet;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Depot {
    private int identifiantDepot;
    private static LocalDate date;
    private LocalTime heure;
    private float points;

    //modélisation des associations
    private Set<PoubelleIntelligente> jeter;
    private Utilisateur posseder;
    private List<Dechet> contenir;

    public Depot(int identifiantDepot, LocalDate date, LocalTime heure, float points) {
        this.identifiantDepot = identifiantDepot;
        this.date = date;
        this.heure = heure;
        this.points = points;
    }
    public Depot(){}

    public int getIdentifiantDepot() {
        return identifiantDepot;
    }

    public void setIdentifiantDepot(int identifiantDepot) {
        this.identifiantDepot = identifiantDepot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public Set<PoubelleIntelligente> getJeter() {
        return jeter;
    }

    public void setJeter(Set<PoubelleIntelligente> jeter) {
        this.jeter = jeter;
    }

    public Utilisateur getPosseder() {
        return posseder;
    }

    public void setPosseder(Utilisateur posseder) {
        this.posseder = posseder;
    }

    public List<Dechet> getContenir() {
        return contenir;
    }

    public void setContenir(List<Dechet> contenir) {
        this.contenir = contenir;
    }

    public static Depot creerDepot(Utilisateur util, List<Dechet> dechets){

        //récupération du dernier identifiant dans la BDD
        String requete = "SELECT MAX(identifiantDepot) FROM Depot;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDepot");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int identifiantDepot = Integer.parseInt(infos.getFirst().get("identifiantDepot")) + 1;
        LocalDate Date = LocalDate.now();
        LocalTime heure = LocalTime.now();
        Depot depot = new Depot(identifiantDepot, Date, heure, 0);
        //Création dans la base de données
        requete = "INSERT INTO Depot(identifiantDepot, date, heure, points) VALUES ("+Integer.toString(identifiantDepot)+","+date.toString()+","+heure.toString()+");";
        requete(requete);

        return depot;
    }

    public void ajouterDechet(Dechet dechet){
        this.contenir.add(dechet);
        int identifiantDechet = dechet.getIdentifiantDechet();
        int identifiantDepot = this.identifiantDepot;
        //ajout en parallèle sur SQL
        String requete = "INSERT INTO contenir(identifiantDechet, identifiantDepot) VALUES ("+Integer.toString(identifiantDechet)+","+Integer.toString(identifiantDepot)+");";
    }

    /*Dans le prochain programme, il faut fournir une liste de poubelles "poubelles "et une liste de listes d'indices "indices_dechets".
    La présence de l'indice i dans la jème liste de "indices_déchets" signifie que le ième déchet de "this.contenir" est jeté dans la jème poubelle de la liste de poubelles.
    */

    public static boolean verifierNature(Dechet dechet, PoubelleIntelligente poubelle){
        TypeDechet typeDechet = dechet.getType();
        TypeDechet typePoubelle = poubelle.getType();
        return typeDechet.equals(typePoubelle);
    }

    public void viderDepot(List<PoubelleIntelligente> poubelles, List<Set<Integer>> indices_dechets){
        /*Transfère des déchets du dépot vers la poubelle avec calcul des points en parallèle*/
        List<Dechet> dechets = this.contenir; //Liste des déchets dans le dépôt courant
        float points = 0;
        int n = poubelles.size();
        for (int k=0; k<n; k++){ //On parcourt la liste poubelles
            PoubelleIntelligente poubelle = poubelles.get(k);
            int identifiantPoubelle = poubelle.getIdentifiantPoubelle();
            int identifiantDechet = 0;
            String requete = "";
            int m = indices_dechets.get(k).size();
            for (int l=0; l<m; l++){ //On parcourt la liste d'indices concernés par la poubelle et donc indirectement les déchets concernés pas la poubelle
                Dechet dechet = dechets.get(l);
                if (verifierNature(dechet,poubelle)){
                    points += 7;
                } else {
                    points -= 5;
                }
                identifiantDechet = dechet.getIdentifiantDechet();
                requete = "INSERT INTO stocker(identifiantPoubelle, identifiantDechet) VALUES("+Integer.toString(identifiantPoubelle)+","+Integer.toString(identifiantDechet)+");";
                requete(requete);
            }
        }
        /*Ajout des points à l'utilisateur*/
        //Récupération du nombre de points
        int identifiantUtilisateur = posseder.getIdentifiantUtilisateur();
        String requete = "SELECT pointsFidelite FROM Utilisateur WHERE identifiantUtilisateur = " + Integer.toString(identifiantUtilisateur)+";";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantUtilisateur");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        float pointsFidelite = Integer.parseInt(infos.getFirst().get("identifiantUtilisateur"));
        //Calcul du nombre de points
        pointsFidelite += points;
        //Changement du nombre de points
        requete = "UPDATE Utilisateur SET pointsFidelite = " + Float.toString(pointsFidelite)+ " WHERE identifiantUtilisateur = " + Integer.toString(identifiantUtilisateur) + ";";
        requete(requete);
    }
}
