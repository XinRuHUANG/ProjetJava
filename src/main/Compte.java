package main;

import static main.outils.connexionSQL.requete;

public class Compte {
    private int pointsFidelite;
    private int codeAcces;

    public Compte(int pointsFidelite, int codeAcces) {
        this.pointsFidelite = pointsFidelite;
        this.codeAcces = codeAcces;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public int getCodeAcces() {
        return codeAcces;
    }

    public void setCodeAcces(int codeAcces) {
        this.codeAcces = codeAcces;
    }

    public static void afficherHistorique(int idCompte) {
        String requete = "SELECT idCorbeille,idPoubelle FROM historiqueDepot WHERE idCompte=" + Integer.toString(idCompte) + ";";
        requete(requete);
    }

    public static void afficherPoints(int idCompte) {
        String requete = "SELECT ptFidelite FROM Compte WHERE idCompte=" + Integer.toString(idCompte) + ";";
        requete(requete);
    }

    public static void afficherPromotions(int idCompte) {
        String requete = "SELECT nomCommerce,pourcentageRemise FROM Promotion P JOIN Compte C on P.idCompte=C.idCompte WHERE C.idCompte=" + Integer.toString(idCompte) + " AND C.ptFidelite > P.ptRequis ;";
        requete(requete);
    }
}
