package main;

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

    public static void ajouterDechet(){}
    public static void viderCorbeille(){}

}
