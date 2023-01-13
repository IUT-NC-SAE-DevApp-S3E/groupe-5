package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;
import javafx.scene.shape.Line;

/**
 * Classe DecorateurFleche qui permet de décorer une flèche
 */
public class DecorateurFleche extends Line implements Observateur {

    /**
     * Constructeur de la classe DecorateurFleche
     * @param coordXDepart les coordonnées en X du départ
     * @param coordYDepart les coordonnées en Y du départ
     * @param coordXArrivee les coordonnées en X de l'arrivée
     * @param coordYArrivee les coordonnées en Y de l'arrivée
     */
    public DecorateurFleche(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee)
    {
        super(coordXDepart, coordYDepart, coordXArrivee, coordYArrivee);
    }

    /**
     * Methode qui permet de mettre a jour l'observateur
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s) {

    }
}
