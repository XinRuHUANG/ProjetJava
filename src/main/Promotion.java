package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Promotion {
    private int identifiantPromotion;
    private float pourcentageRemise;
    private float pointsRequis;

    //Constructeur
    public Promotion(){
        this.identifiantPromotion = 0;
        this.pourcentageRemise = 0;
        this.pointsRequis = 0;
    }


    public Promotion(int identifiantPromotion, float pourcentageRemise, float pointsRequis){
        this.identifiantPromotion = identifiantPromotion;
        this.pourcentageRemise = pourcentageRemise;
        this.pointsRequis = pointsRequis;
    }


    //Getter et Setter
    public int getIdentifiantPromotion() {
        return identifiantPromotion;
    }

    public void setIdentifiantPromotion(int identifiantPromotion) {
        this.identifiantPromotion = identifiantPromotion;
    }

    public float getPourcentageRemise() {
        return pourcentageRemise;
    }

    public void setPourcentageRemise(float pourcentageRemise) {
        this.pourcentageRemise = pourcentageRemise;
    }

    public float getPointsRequis() {
        return pointsRequis;
    }

    public void setPointsRequis(float pointsRequis) {
        this.pointsRequis = pointsRequis;
    }


    //Association avec Utilisateur : utiliser
    private Set<Utilisateur> utiliser;

    //Association avec Contrat : definir
    private Contrat definir;

    //Association avec CategorieDeOroduits : concerner
    private CategorieDeProduits concerner;

    public CategorieDeProduits getConcerner() {
        return concerner;
    }

    public void setConcerner(CategorieDeProduits concerner) {
        this.concerner = concerner;
    }

    public Contrat getDefinir() {
        return definir;
    }

    public void setDefinir(Contrat definir) {
        this.definir = definir;
    }

    public Set<Utilisateur> getUtiliser() {
        return utiliser;
    }

    public void setUtiliser(Set<Utilisateur> utiliser) {
        this.utiliser = utiliser;
    }

    public float getPointsRequis() {
        return pointsRequis;
    }

    public void setPointsRequis(float pointsRequis) {
        this.pointsRequis = pointsRequis;
    }

    public float getPourcentageRemise() {
        return pourcentageRemise;
    }

    public void setPourcentageRemise(float pourcentageRemise) {
        this.pourcentageRemise = pourcentageRemise;
    }

    public int getIdentifiantPromotion() {
        return identifiantPromotion;
    }

    public void setIdentifiantPromotion(int identifiantPromotion) {
        this.identifiantPromotion = identifiantPromotion;
    }

    public Promotion(int identifiantPromotion, float pourcentageRemise, float pointsRequis) {
        this.identifiantPromotion = identifiantPromotion;
        this.pourcentageRemise = pourcentageRemise;
        this.pointsRequis = pointsRequis;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "identifiantPromotion=" + identifiantPromotion +
                ", pourcentageRemise=" + pourcentageRemise +
                ", pointsRequis=" + pointsRequis +
                ", utiliser=" + utiliser +
                ", definir=" + definir +
                ", concerner=" + concerner +
                '}';
    }

    public static Promotion ajouterPromotion(float pourcentageRemise, float pointsRequis){
        String requete = "SELECT MAX(identifiantPromotion) FROM Promotion;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantPromotion");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete, attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantPromotion")) + 1;

        Promotion promotion = new Promotion(id, pourcentageRemise, pointsRequis);
        //Création dans la base de données
        requete = "INSERT INTO Promotion(identifiantPromotion, pourcentageRemise, pointsRequis) VALUES (" + Integer.toString(id) + "," + Float.toString(pourcentageRemise) + "," + Float.toString(pointsRequis) + ");";
        requete(requete);
        return promotion;
    }

    public void retirerPromotion(){
        int identifiantPromotion = this.identifiantPromotion;
        String requete = "DELETE FROM Promotion WHERE identifiant = " + Integer.toString(identifiantPromotion) + ";";
    }
}
