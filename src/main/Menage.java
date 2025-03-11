package main;

import static main.outils.connexionSQL.requete;

public class Menage {
    private String nomRepresentant;
    private String prenomRepresentant;

    public Menage(String nomRepresentant, String prenomRepresentant) {
        this.nomRepresentant = nomRepresentant;
        this.prenomRepresentant = prenomRepresentant;
    }

    public String getNomRepresentant() {
        return nomRepresentant;
    }

    public String getPrenomRepresentant() {
        return prenomRepresentant;
    }

    public void setNomRepresentant(String nomRepresentant) {
        this.nomRepresentant = nomRepresentant;
    }

    public void setPrenomRepresentant(String prenomRepresentant) {
        this.prenomRepresentant = prenomRepresentant;
    }
    public static void creerCompte(int idCompte, int idMenage,int ptFidelite, int codeAcces){
        String requete = "INSERT INTO Compte (idCompte, idMenage, ptFidelite, codeAcces) VALUES ("+Integer.toString(idCompte)+","+Integer.toString(idMenage)+","+Integer.toString(ptFidelite)+","+Integer.toString(codeAcces)+");";
        requete(requete);
    }

    public static void consulterCompte(int idMenage){
        String requete = "SELECT * FROM Compte C JOIN Menage M ON C.idMenage=M.idMenage WHERE idMenage="+Integer.toString(idMenage)+");";
        requete(requete);
    }

    public static void convertirPoints(int idCommerce, int idMenage, int pourcentageRemise, int ptRequis){
        String requete = "INSERT INTO Promotion(idCommerce,idCompte,pourcentageRemise,ptRequis) VALUES ("+Integer.toString(idCommerce)+",idCompte=SELECT idCompte FROM Compte C JOIN Menage M ON C.idMenage=M.idMenage WHERE M.idMenage="+idMenage+"),"+Integer.toString(pourcentageRemise)+","+Integer.toString(ptRequis)+");";
        requete(requete);
    }
    public static void consulterHistorique(int idMenage){
        String requete = "SELECT * FROM historiqueDepot H JOIN Compte C ON H.idCorbeille=C.idCompte JOIN MENAGE M on M.idMenage=C.idMenage WHERE M.idMenage="+Integer.toString(idMenage)+");";
        requete(requete);
    }
}
