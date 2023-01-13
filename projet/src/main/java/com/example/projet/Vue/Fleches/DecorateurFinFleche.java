package com.example.projet.Vue.Fleches;

import javafx.scene.image.ImageView;

/**
 * Classe DecorateurFinFleche qui permet de décorer la fin de la flèche
 */
public class DecorateurFinFleche extends ImageView {

    /**
     * Constructeur de la classe DecorateurFinFleche
     * @param image l'image
     * @param coordXDepart les coordonnées en X du départ
     * @param coordYDepart les coordonnées en Y du départ
     * @param coordXArrivee les coordonnées en X de l'arrivée
     * @param coordYArrivee les coordonnées en Y de l'arrivée
     */
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
