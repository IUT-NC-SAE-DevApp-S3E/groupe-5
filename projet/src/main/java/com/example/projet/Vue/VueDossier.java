package com.example.projet.Vue;

import com.example.projet.Controleur.ControleurBoutonArborescence;
import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Dossier;
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

import java.io.File;

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
        VBox vBox = new VBox();
        listeDossierFichier.setContent(vBox);
        // le vbox prend la taille du scrollpane
        vBox.setPrefWidth(listeDossierFichier.getPrefWidth());
        listeDossierFichier.setPrefSize(250, 510);
        // on met dans le listeDossierFichier l'arborecence des dossiers et fichiers

        File file = new File("C:\\Users\\");

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                ImageView view = new ImageView(new Image("folder.png"));
                Button bouton = new Button(" > "+f.getName());

                // on ajoute l'image a gauche du bouton
                bouton.setGraphic(view);
                // la taille de l'image est de 20x20
                view.setFitHeight(20);
                view.setPreserveRatio(true);

                vBox.getChildren().add(bouton);
                // la width du bouton est la width du scrollpane
                bouton.setPrefWidth(listeDossierFichier.getPrefWidth());
                // on cole le text a gauche
                bouton.setAlignment(Pos.BASELINE_LEFT);
                // on rend le bouton transparent
                bouton.setStyle("-fx-background-color: transparent;");

                // on met le controlleur sur le bouton
                VBox bottomFile = new VBox();
                vBox.getChildren().add(bottomFile);
                // la width du vbox est la width du scrollpane
                bottomFile.setPrefWidth(listeDossierFichier.getPrefWidth());
                bouton.setOnAction(new ControleurBoutonArborescence(f.getPath(), bottomFile,  1));
            }
        }



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

    public static void afficherContenuDossier(File f) {
        try {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    System.out.print(" | ");
                    afficherContenuDossier(file);
                }
            } else {
                System.out.println(" > " + f.getName());
            }
        } catch (Exception e) {

        }
    }

}
