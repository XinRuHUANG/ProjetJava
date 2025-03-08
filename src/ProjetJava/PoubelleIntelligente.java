package ProjetJava;

public class PoubelleIntelligente {
    private int identifiant;
    public String emplacement;
    public int capaciteMaximale;

    public PoubelleIntelligente() {
        this.identifiant = 0;
        this.emplacement = null;
        this.capaciteMaximale = 0;
    }

    public PoubelleIntelligente(int identifiant, String emplacement, int capaciteMaximale){
        this.identifiant = identifiant;
        this.emplacement = emplacement;
        this.capaciteMaximale = capaciteMaximale;
    }
}
