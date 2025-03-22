package main;

import java.util.Date;
import java.util.Set;

public class Contrat {
    private int identifiantContrat;
    private Date dateDebut;
    private Date dateFin;
    private String clauses;
    //Modélisation des associations
    private Set<Promotion> definir;
}
