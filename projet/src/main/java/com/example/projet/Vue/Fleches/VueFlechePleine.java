package com.example.projet.Vue.Fleches;

/**
 * Classe VueFlechePleine qui permet de décorer la flèche
 */
public class VueFlechePleine extends DecorateurFleche {

    /**
     * Constructeur de la classe VueFlechePleine
     * @param coordXDepart les coordonnées en X du départ
     * @param coordYDepart les coordonnées en Y du départ
     * @param coordXArrivee les coordonnées en X de l'arrivée
     * @param coordYArrivee les coordonnées en Y de l'arrivée
     */
    public VueFlechePleine(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
        this.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
    }

}
