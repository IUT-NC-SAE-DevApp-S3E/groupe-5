package com.example.projet.Controleur;

import com.example.projet.Modele.Modele;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Utilitaires.Fichier;
import com.example.projet.Utilitaires.LectureFichier;
import com.example.projet.Vue.VueClasse;
import com.example.projet.Vue.VueDiagrammeClasse;
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

    private int startX = 0;
    private int startY = 0;
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

                        bouton.setOnMousePressed(mouseEvent -> {
                            this.startX = (int) mouseEvent.getSceneX();
                            this.startY = (int) mouseEvent.getSceneY();
                        });

                        bouton.setOnMouseDragged(event -> {
                            bouton.setTranslateX(event.getSceneX() - this.startX);
                            bouton.setTranslateY(event.getSceneY() - this.startY);
                        });


                        // quand on lache le bouton, on le remet a sa place
                        bouton.setOnMouseReleased((event) -> {
                            if (event.getSceneX() < 250) {
                                bouton.setTranslateX(0);
                                bouton.setTranslateY(0);
                                System.out.println("dans le scrollPane car x = " + event.getSceneX() + " < 110" );
                            } else {
                                // on créer un nouveau Dossier avec le path du bouton
                                System.out.println("le path du bouton est : " + f.getPath());
                                Dossier dossier = new Dossier(f.getPath(), "dossier");
                                // on affiche le dossier
                                try {
                                    dossier.lectureDossier();
                                } catch (Exception e) {
                                    // TODO on ne fait rien car le fichier est mauvais, domage
                                }
                                // on remet le bouton a sa place et on le rend non draggable
                                bouton.setTranslateX(0);
                                bouton.setTranslateY(0);
                                bouton.setMnemonicParsing(true);
                            }
                        });



                        // on met le controlleur sur le bouton
                        VBox bottomFile = new VBox();
                        bottomFile.setPrefWidth(this.vBox.getPrefWidth());
                        bottomFile.setSpacing(3);
                        this.vBox.getChildren().add(bottomFile);
                        bouton.setOnAction(new ControleurBoutonArborescence(f.getPath(), bottomFile, this.margin + 1));
                    } else {
                        ImageView view = new ImageView(new Image("file.png"));
                        // on ajoute un label au lieu d'un bouton
                        Label label = new Label(" | " + f.getName());
                        // on met la couleur du label en blanc
                        label.setStyle("-fx-text-fill: black;");
                        // on met un margin a gauche
                        label.setPadding(new javafx.geometry.Insets(0, 0, 0, this.margin * 20));
                        // le label est draggable
                        label.setOnMousePressed(mouseEvent -> {
                            this.startX = (int) mouseEvent.getSceneX();
                            this.startY = (int) mouseEvent.getSceneY();
                        });

                        label.setOnMouseDragged(event -> {
                            label.setTranslateX(event.getSceneX() - this.startX);
                            label.setTranslateY(event.getSceneY() - this.startY);
                        });

                        label.setOnMouseReleased((event) -> {
                            if (event.getSceneX() < 250) {
                                label.setTranslateX(0);
                                label.setTranslateY(0);
                                System.out.println("dans le scrollPane car x = " + event.getSceneX() + " < 110" );
                            } else {
                                // on créer un nouveau Dossier avec le path du bouton
                                System.out.println("le path du bouton est : " + f.getPath());
                                Dossier dossier = new Dossier(f.getPath(), "dossier");
                                // on affiche le dossier
                                try {
                                    dossier.lectureDossier();
                                } catch (Exception e) {
                                    // TODO on ne fait rien car le fichier est mauvais, domage
                                }
                                // on remet le bouton a sa place et on le rend non draggable
                                label.setTranslateX(0);
                                label.setTranslateY(0);
                                label.setMnemonicParsing(true);
                            }
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
