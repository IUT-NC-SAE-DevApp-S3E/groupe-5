package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;
import javafx.scene.shape.Line;

public class DecorateurFleche extends Line implements Observateur {
    public DecorateurFleche(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee)
    {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
