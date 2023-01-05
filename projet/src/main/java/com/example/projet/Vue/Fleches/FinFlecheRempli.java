package com.example.projet.Vue.Fleches;

import javafx.scene.paint.Color;

public class FinFlecheRempli extends DecorateurFinFleche
{
    public FinFlecheRempli(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.setFill(Color.BLACK);
    }
}
