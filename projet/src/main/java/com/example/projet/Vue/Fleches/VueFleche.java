package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;

import javafx.scene.Group;

public class VueFleche extends Group implements Observateur
{

    private DecorateurFleche fleche = null;
    private DecorateurFinFleche finFleche = null;

    public VueFleche(int xd, int yd, int xa, int ya, int type)
    {
        if(type == 1)
        {
            fleche = new VueFlechePleine(xd, yd, xa, ya);
            finFleche = new FinFlecheVide("ArrowVide.png",xd, yd, xa, ya);
        }
        else if (type == 2)
        {
            fleche = new VueFlechePointille(xd, yd, xa, ya);
            finFleche = new FinFlecheVide("ArrowVide.png", xd, yd, xa, ya);
        }
        else if (type == 3)
        {
            fleche = new VueFlechePleine(xd, yd, xa, ya);
            finFleche = new FinFlecheRempli("ArrowFill.png",xd, yd, xa, ya);
        }
        this.getChildren().addAll(fleche, finFleche);
    }

    @Override
    public void actualiser(Sujet s)
    {
        // Oui
    }

}
