package com.example.projet.Vue.Fleches;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class DecorateurFinFleche extends Polygon {
    public DecorateurFinFleche(int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee, int rot) {
        super(0, 0, -15, 15, 15, 15);

        Double coord1 = (double) (coordYDepart- coordYArrivee);
        Double coord2 = (double) (coordXDepart - coordXArrivee);

        double angle2 = Math.atan2(coord2, coord1);
        //Créer une matrice de rotation
        Rotate rotate2 = new Rotate(Math.toDegrees(angle2), 0, 0);
        //Appliquer la rotation à la tête de la flèche
        this.getTransforms().add(rotate2);

        // Déplacer la tête de la flèche pour qu'elle soit située à l'extrémité du segment de droite
        this.setTranslateX(coordXArrivee);
        this.setTranslateY(coordYArrivee);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);

    }
}
