package com.example.projet.Vue.Fleches;

import javafx.scene.paint.Color;

/**
 * Classe FinFlecheRempli qui permet de décorer la fin de la flèche
 */
public class FinFlecheRempli extends DecorateurFinFleche
{

    /**
     * Constructeur de la classe FinFlecheRempli
     * @param image l'image
     * @param coordXDepart les coordonnées en X du départ
     * @param coordYDepart les coordonnées en Y du départ
     * @param coordXArrivee les coordonnées en X de l'arrivée
     * @param coordYArrivee les coordonnées en Y de l'arrivée
     */
    public FinFlecheRempli(String image, int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(image, coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
    }
}
