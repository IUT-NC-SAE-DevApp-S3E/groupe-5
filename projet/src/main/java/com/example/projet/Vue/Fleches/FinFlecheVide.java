package com.example.projet.Vue.Fleches;

import javafx.scene.paint.Color;

public class FinFlecheVide extends DecorateurFinFleche
{
    public FinFlecheVide(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);
    }
}
