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

        double imageMiddleX = this.getBoundsInLocal().getWidth() / 2;
        double imageMiddleY = this.getBoundsInLocal().getHeight() / 2;

        // Calculer l'angle de rotation de l'image pour qu'elle soit dirigée vers le point d'arrivée
        double angle = Math.atan2(coordYArrivee - coordYDepart, coordXArrivee - coordXDepart);

        // Translater le point de départ pour que le milieu gauche de l'image soit sur les coordonnées données
        double x = coordXDepart - imageMiddleX;
        double y = coordYDepart - imageMiddleY;

        // Appliquer la rotation et la translation à l'ImageView
        this.setRotate(Math.toDegrees(angle));
        this.setTranslateX(x);
        this.setTranslateY(y);

    }


}
