package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.TrouverCheminOS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControleurBoutonOpenFile implements Observateur, EventHandler<ActionEvent> {

        @Override
        public void actualiser(Sujet s) {
            // TODO
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Stage choixDeFichier = new Stage();
            choixDeFichier.setTitle("Choix de fichier");
            choixDeFichier.setHeight(600);
            choixDeFichier.setWidth(250);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir un fichier");
            fileChooser.setInitialDirectory(new File(TrouverCheminOS.getChemin()));
            File file = fileChooser.showOpenDialog(choixDeFichier);
            /**
            VBox vBox = new VBox();
            // le vbox prend la taille du scrollpane
            File file = new File(TrouverCheminOS.getChemin());
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
                    // on cole le text a gauche
                    bouton.setAlignment(Pos.BASELINE_LEFT);
                    // on rend le bouton transparent
                    bouton.setStyle("-fx-background-color: transparent;");

                    // on met le controlleur sur le bouton
                    VBox bottomFile = new VBox();
                    // on met un spacing de 5
                    bottomFile.setSpacing(3);

                    vBox.getChildren().add(bottomFile);
                    // la width du vbox est la width du scrollpane
                    ControleurBoutonArborescence controleurBoutonArborescence = new ControleurBoutonArborescence(f.getPath(), bottomFile, 1);
                    bouton.setOnAction(controleurBoutonArborescence);
                }
            }
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);
            Scene scene = new Scene(scrollPane);
            choixDeFichier.setScene(scene);
            choixDeFichier.show();
            */
        }
}
