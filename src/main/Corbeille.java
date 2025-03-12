package main;

import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Corbeille {
    private int nbPlastiques;
    private int nbVerres;
    private int nbCartons;
    private int nbMetaux;

    public Corbeille(int nbPlastiques, int nbVerres, int nbCartons, int nbMetaux){
        this.nbPlastiques = nbPlastiques;
        this.nbVerres = nbVerres;
        this.nbCartons = nbCartons;
        this.nbMetaux = nbMetaux;
    }

    public int getNbPlastiques(){return this.nbPlastiques;}
    public int getNbVerres(){return this.nbVerres;}
    public int getNbCartons(){return this.nbCartons;}
    public int getNbMetaux(){return this.nbMetaux;}

    public void setNbPlastiques(int nbPlastiques){this.nbPlastiques = nbPlastiques;}
    public void setNbVerres(int nbVerres){this.nbVerres = nbVerres;}
    public void setNbCartons(int nbCartons){this.nbCartons = nbCartons;}
    public void setNbMetaux(int nbMetaux){this.nbMetaux = nbMetaux;}

    public static void ajouterDechet(int id, int qtePlastique, int qteCarton, int qteMetaux, int qteVerre){
        String requete = "INSERT INTO <Corbeille> (qtePlastique, qteVerre, qteCarton, qteMetaux) VALUES ("+qtePlastique+", "+qteVerre+", "+qteCarton+", "+qteMetaux+";";
        requete(requete);
    }
    public static void viderCorbeille(int id){
        String requete = "UPDATE <Corbeille> SET qtePlastique=0, qteVerre=0, qteCarton=0, qteMetaux=0;";
        requete(requete);
    }


}
