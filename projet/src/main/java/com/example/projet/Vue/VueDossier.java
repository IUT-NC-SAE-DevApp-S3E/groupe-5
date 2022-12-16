package com.example.projet.Vue;

import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VueDossier extends VBox implements Observateur {

    public VueDossier() {
        super();
        this.setPrefSize(250, 700);
        this.setSpacing(13);

        HBox boutonHaut = new HBox();
        for (int i = 1; i <= 4; i++) {
            Button bouton = new Button();
            ImageView view = new ImageView(new Image("imageBoutonHaut" + i + ".png"));
            view.setFitHeight(35);
            view.setPreserveRatio(true);
            bouton.setGraphic(view);
            boutonHaut.getChildren().add(bouton);
            // on met le background du bouton en transparent
            bouton.setStyle("-fx-background-color: transparent;");
        }

        Text chemin = new Text("chemin");

        ScrollPane listeDossierFichier = new ScrollPane();
        listeDossierFichier.setPrefSize(250, 510);

        HBox boutonafficherCacher = new HBox();
        boutonafficherCacher.setAlignment(Pos.CENTER);
        boutonafficherCacher.setSpacing(3);
        Button bouton = new Button();
        ImageView imageCacherAfficher = new ImageView(new Image("imageAfficherCacher.png"));
        imageCacherAfficher.setFitHeight(20);
        imageCacherAfficher.setPreserveRatio(true);
        bouton.setGraphic(imageCacherAfficher);
        imageCacherAfficher.setFitHeight(40);
        // on met le background du bouton en transparent
        bouton.setStyle("-fx-background-color: transparent;");
        boutonafficherCacher.getChildren().add(bouton);
        String texteAfficherCacher = "MPDA";
        for (int i = 0; i < 4; i++) {
            Button boutonTxt = new Button(texteAfficherCacher.charAt(i) + "");
            boutonTxt.setPrefSize(45, 45);
            // on met le background du bouton en transparent
            boutonTxt.setStyle("-fx-background-color: transparent;");
            // on met la taille du texte du bouton à 20
            boutonTxt.setStyle("-fx-font-size: 20;");
            boutonafficherCacher.getChildren().add(boutonTxt);
        }

        this.getChildren().addAll(boutonHaut, chemin, listeDossierFichier, boutonafficherCacher);
    }

    @Override
    public void actualiser(Sujet s) {

    }

}
