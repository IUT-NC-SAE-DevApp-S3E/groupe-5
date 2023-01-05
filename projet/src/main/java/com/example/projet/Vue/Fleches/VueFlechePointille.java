package com.example.projet.Vue.Fleches;

public class VueFlechePointille extends DecorateurFleche{
    public VueFlechePointille(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.getStrokeDashArray().addAll(5d, 5d);
    }
}
