package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;

import javafx.scene.Group;

/**
 * Classe VueFleche qui permet de créer une flèche
 */
public class VueFleche extends Group implements Observateur
{

    /**
     * attributs de la classe VueFleche
     */
    private DecorateurFleche fleche = null;
    private DecorateurFinFleche finFleche = null;

    /**
     * Constructeur de la classe VueFleche
     * @param xd les coordonnées en X du départ
     * @param yd les coordonnées en Y du départ
     * @param xa les coordonnées en X de l'arrivée
     * @param ya les coordonnées en Y de l'arrivée
     * @param type le type de flèche
     */
    public VueFleche(int xd, int yd, int xa, int ya, int type)
    {
        if(type == 1)
        {
            fleche = new VueFlechePleine(xd, yd, xa, ya);
            finFleche = new FinFlecheVide("ArrowNonPleine.png",xd, yd, xa, ya);
        }
        else if (type == 2)
        {
            fleche = new VueFlechePointille(xd, yd, xa, ya);
            finFleche = new FinFlecheVide("ArrowNonPleine.png", xd, yd, xa, ya);
        }
        else if (type == 3)
        {
            fleche = new VueFlechePleine(xd, yd, xa, ya);
            finFleche = new FinFlecheRempli("ArrowPleine.png",xd, yd, xa, ya);
        }
        this.getChildren().addAll(fleche, finFleche);
    }

    /**
     * Methode qui permet de mettre a jour l'observateur
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s)
    {
    }

}
