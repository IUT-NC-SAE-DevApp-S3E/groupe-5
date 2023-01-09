package com.example.projet.Vue.Fleches;

public class VueFlechePleine extends DecorateurFleche {
    public VueFlechePleine(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
    }

}
