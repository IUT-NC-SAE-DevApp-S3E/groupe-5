package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class DecorateurFinFleche extends Polygon implements Observateur {
    public DecorateurFinFleche(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(0, 0, -10, 15, 10, 15);

        Double coord1 = (double) (coordYDepart- coordYArrivee);
        Double coord2 = (double) (coordXDepart - coordXArrivee);

        double angle2 = Math.atan2(coord2, coord1);
        //Créer une matrice de rotation
        Rotate rotate2 = new Rotate(Math.toDegrees(angle2), 0, 0);
        //Appliquer la rotation à la tête de la flèche
        System.out.println(rotate2.getAngle());
        this.setRotate(Math.abs(rotate2.getAngle()));

        // Déplacer la tête de la flèche pour qu'elle soit située à l'extrémité du segment de droite
        this.setTranslateX(coordXArrivee + 5);
        this.setTranslateY(coordYArrivee - 5);

    }

    @Override
    public void actualiser(Sujet s) {

    }
}
