package com.example.projet.Vue.Fleches;

/**
 * Classe VueFlechePointille qui permet de décorer la flèche
 */
public class VueFlechePointille extends DecorateurFleche {

    /**
     * Constructeur de la classe VueFlechePointille
     * @param coordXDepart les coordonnées en X du départ
     * @param coordYDepart les coordonnées en Y du départ
     * @param coordXArrivee les coordonnées en X de l'arrivée
     * @param coordYArrivee les coordonnées en Y de l'arrivée
     */
    public VueFlechePointille(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.getStrokeDashArray().addAll(5d, 5d);
    }
}
