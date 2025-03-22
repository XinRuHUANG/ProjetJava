package main;

public class TypeDéchetEnumEtConstantes {

    //Constantes de poids (évoquées dans le sujet)
    public static final int pp = 14;
    public static final int pv = 16;
    public static final int pc = 19;
    public static final int pm = 32;

    //Constantes pour les points (arbitraires)
    public static final int ptsP = 7;
    public static final int ptsV = 8;
    public static final int ptsC = 9;
    public static final int ptsM = 16;

    public enum TypeDechet {
        verre, carton, plastique, metal
    }
}
