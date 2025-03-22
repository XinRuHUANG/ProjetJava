package main;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

public class Depot {
    private int identifiant;
    private Date date;
    private LocalTime heure;
    private float points;

    //modélisation des associations
    private Set<PoubelleIntelligente> jeter;
    private Utilisateur posseder;
    private Set<Dechet> contenir;

}
