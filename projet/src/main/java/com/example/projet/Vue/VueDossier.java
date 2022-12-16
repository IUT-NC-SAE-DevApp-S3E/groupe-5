package com.example.projet.Vue;

import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import javafx.beans.Observable;
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

        HBox boutonHaut = new HBox();
        for(int i = 1 ; i <= 4 ; i++){
            Button bouton = new Button();
            ImageView view = new ImageView(new Image("imageBoutonHaut"+i+".png"));
            view.setFitHeight(80);
            view.setPreserveRatio(true);
            bouton.setPrefSize(80, 80);
            bouton.setGraphic(view);
            boutonHaut.getChildren().add(bouton);
        }

        TextField chemin = new TextField("chemin");
        ScrollPane listeDossierFichier = new ScrollPane();

        HBox boutonafficherCacher = new HBox();
        Image imageAfficherCacher = new Image("imageAfficherCacher.png");

        String texteAfficherCacher = "MPDA";
        for(int i = 0 ; i < 4 ; i++){
            boutonafficherCacher.getChildren().add(new Button(texteAfficherCacher.charAt(i)+""));
        }

        this.getChildren().addAll(boutonHaut, chemin, listeDossierFichier, boutonafficherCacher);
    }

    @Override
    public void actualiser(Sujet s) {

    }

}
