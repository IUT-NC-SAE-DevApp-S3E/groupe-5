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

public class VueDossier extends VBox implements Observateur{

    public VueDossier() {
        super();
        this.setPrefSize(250,700);
        this.setSpacing(13);

        HBox boutonHaut = new HBox();
        for(int i = 1 ; i <= 4 ; i++){
            Button bouton = new Button();
            ImageView view = new ImageView(new Image("imageBoutonHaut"+i+".png"));
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            bouton.setGraphic(view);
            boutonHaut.getChildren().add(bouton);
        }

        Text chemin = new Text("chemin");
        ScrollPane listeDossierFichier = new ScrollPane();
        listeDossierFichier.setPrefSize(250,510);

        HBox boutonafficherCacher = new HBox();
        boutonafficherCacher.setAlignment(Pos.CENTER);
        boutonafficherCacher.setSpacing(3);
        ImageView imageCacherAfficher = new ImageView(new Image("imageAfficherCacher.png"));
        imageCacherAfficher.setFitHeight(45);
        imageCacherAfficher.setPreserveRatio(true);
        boutonafficherCacher.getChildren().add(imageCacherAfficher);
        String texteAfficherCacher = "MPDA";
        for(int i = 0 ; i < 4 ; i++){
            Button bouton = new Button(texteAfficherCacher.charAt(i)+"");
            bouton.setPrefSize(45,45);
            boutonafficherCacher.getChildren().add(bouton);
        }

        this.getChildren().addAll(boutonHaut, chemin, listeDossierFichier, boutonafficherCacher);
    }

    @Override
    public void actualiser(Sujet s) {

    }

}
