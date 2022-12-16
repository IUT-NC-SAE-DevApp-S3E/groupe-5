package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

public class ControleurBoutonArborescence implements Observateur, EventHandler<ActionEvent> {

    private String path;
    private VBox vBox;
    private boolean isClicked = false;

    private int margin = 1;

    public ControleurBoutonArborescence(String path, VBox vBox, int margin) {
        this.path = path;
        this.vBox = vBox;
        this.margin = margin;
    }

    @Override
    public void actualiser(Sujet s) {

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // on ajoute les boutons dans le scrollPane correspondant aux nombres de fichiers et dossiers
        // dans le dossier
        File file = new File(this.path);
        if (file.isDirectory() && !isClicked) {
            isClicked = true;
            for (File f : file.listFiles()) {
                try {
                    if (f.isDirectory()) {
                        ImageView view = new ImageView(new Image("folder.png"));
                        Button bouton = new Button(f.getName());

                        // on ajoute l'image a gauche du bouton
                        bouton.setGraphic(view);
                        // la taille de l'image est de 20x20
                        view.setFitHeight(20);
                        view.setPreserveRatio(true);

                        this.vBox.getChildren().add(bouton);
                        // la width du bouton est la width du scrollpane
                        bouton.setPrefWidth(this.vBox.getPrefWidth());
                        // on cole le text a gauche
                        bouton.setAlignment(Pos.BASELINE_LEFT);
                        // on rend le bouton transparent
                        bouton.setStyle("-fx-background-color: transparent;");
                        // on met un margin a gauche
                        bouton.setPadding(new javafx.geometry.Insets(0, 0, 0, this.margin * 20));
                        // le bouton est draggable
                        bouton.setMnemonicParsing(false);
                        bouton.setOnMouseDragged((event) -> {
                            bouton.setTranslateX(event.getX());
                            bouton.setTranslateY(event.getY());
                        });
                        // quand on lache le bouton, on le remet a sa place
                        bouton.setOnMouseReleased((event) -> {
                            bouton.setTranslateX(0);
                            bouton.setTranslateY(0);
                        });


                        // on met le controlleur sur le bouton
                        VBox bottomFile = new VBox();
                        bottomFile.setPrefWidth(this.vBox.getPrefWidth());
                        this.vBox.getChildren().add(bottomFile);
                        bouton.setOnAction(new ControleurBoutonArborescence(f.getPath(), bottomFile, this.margin + 1));
                    } else {
                        ImageView view = new ImageView(new Image("file.png"));
                        // on ajoute un label au lieu d'un bouton
                        Label label = new Label(" | " + f.getName());
                        // on met un margin a gauche
                        label.setPadding(new javafx.geometry.Insets(0, 0, 0, this.margin * 20));
                        // le label est draggable
                        label.setMnemonicParsing(false);
                        label.setOnMouseDragged((event) -> {
                            label.setTranslateX(event.getX());
                            label.setTranslateY(event.getY());
                        });
                        // quand on lache le label, on le remet a sa place
                        label.setOnMouseReleased((event) -> {
                            label.setTranslateX(0);
                            label.setTranslateY(0);
                        });

                        // on ajoute l'image a gauche du label
                        label.setGraphic(view);
                        // la taille de l'image est de 20x20
                        view.setFitHeight(20);
                        view.setPreserveRatio(true);

                        this.vBox.getChildren().add(label);
                        // la width du label est la width du scrollpane
                        label.setPrefWidth(this.vBox.getPrefWidth());
                    }
                } catch (Exception e) {
                    System.out.println("erreur");
                }
            }
        } else if (file.isDirectory() && isClicked) {
            isClicked = false;
            // on supprime tout dans le vBox
            this.vBox.getChildren().clear();
        }
    }
}
