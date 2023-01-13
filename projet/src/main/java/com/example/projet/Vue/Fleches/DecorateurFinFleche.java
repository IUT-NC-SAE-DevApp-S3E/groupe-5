package com.example.projet.Vue.Fleches;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.Observateur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class DecorateurFinFleche extends ImageView {
    public DecorateurFinFleche(String image, int coordXDepart, int coordYDepart, int coordXArrivee, int coordYArrivee) {
        super(image);

        this.setPreserveRatio(true);
        this.setFitHeight(20);

        double angle = Math.atan2(coordYDepart - coordYArrivee, coordXDepart - coordXArrivee);
        double rotate = Math.toDegrees(angle);
        this.setRotate(rotate);
        double halfWidth = 10;
        System.out.println(halfWidth);
        this.setLayoutX(coordXArrivee - halfWidth * Math.cos(angle) - halfWidth * Math.sin(angle));
        this.setLayoutY(coordYArrivee - halfWidth * Math.sin(angle) + halfWidth * Math.cos(angle));
    }


}
