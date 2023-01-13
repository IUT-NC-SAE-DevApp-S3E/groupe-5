package com.example.projet.Vue.Fleches;

import javafx.scene.Group;

public class VueFleche extends Group
{
    public VueFleche(int xd, int yd, int xa, int ya, int type)
    {
        DecorateurFleche fleche = null;
        DecorateurFinFleche finFleche = null;
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
}
