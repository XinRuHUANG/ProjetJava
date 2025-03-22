package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.TypeDéchetEnum.TypeDechet;
import static main.outils.connexionSQL.requete;
import static main.outils.connexionSQL.requeteAvecAffichage;

public class Dechet {
    private int identifiantDechet;
    private TypeDechet type;

    //modélisation des associations
    private Depot contenir;

    public Dechet(int identifiantDechet, TypeDechet type) {
        this.identifiantDechet = identifiantDechet;
        this.type = type;
    }

    public int getIdentifiantDechet() {
        return identifiantDechet;
    }

    public void setIdentifiantDechet(int identifiantDechet) {
        this.identifiantDechet = identifiantDechet;
    }

    public TypeDechet getType() {
        return type;
    }

    public void setType(TypeDechet type) {
        this.type = type;
    }

    public Depot getContenir() {
        return contenir;
    }

    public void setContenir(Depot contenir) {
        this.contenir = contenir;
    }

    public static void ajouterDechet(TypeDechet type){
        String requete = "SELECT MAX(identifiantDechet) FROM Dechet;";
        ArrayList<String> attributs = new ArrayList<>();
        attributs.add("identifiantDechet");
        List<HashMap<String, String>> infos = requeteAvecAffichage(requete,attributs);
        int id = Integer.parseInt(infos.getFirst().get("identifiantDechet"));

        Dechet poubelle = new Dechet(id,type);
        requete = "INSERT INTO PoubelleIntelligente(identifiantDechet,type) VALUES ("+Integer.toString(id)+","+type.toString()+");";
        requete(requete);
    }

    public void viderDepot(Dechet dechet){

        requete(requete);
    }
}
