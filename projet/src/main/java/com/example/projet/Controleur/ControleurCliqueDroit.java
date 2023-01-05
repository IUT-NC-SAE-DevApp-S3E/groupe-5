package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.VueClasse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ControleurCliqueDroit implements EventHandler<MouseEvent> {


    private int startX = 0;
    private int startY = 0;
    private Sujet sujet;
    private Pane pane;
    private VBox menu = new VBox();

    private VueClasse actualVBox;
    private boolean clicked = false;

    public ControleurCliqueDroit(Sujet sujet, Pane pane, VueClasse actualVBox) {
        this.sujet = sujet;
        this.pane = pane;
        this.actualVBox = actualVBox;

        this.pane.setOnMousePressed(mouseEvent -> {
            this.sujet.setStartX((int)mouseEvent.getX());
            this.sujet.setStartY((int)mouseEvent.getY());
        });
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getButton().toString().equals("SECONDARY") && !this.clicked) {
            this.clicked = true;
            VBox menu = new VBox();
            this.menu = menu;

            String[] titreBoutons = {"+ Ajouter un attribut", "+ Ajouter une méthode", "+ Ajouter une dépendance", "- Supprimer"};
            for (int i = 0; i < titreBoutons.length; i++) {
                Button bouton = new Button(titreBoutons[i]);
                bouton.setStyle("-fx-background-color: #f3f3f3;");
                bouton.setPrefWidth(200);
                bouton.setPrefHeight(25);
                // on met un hover aux boutons pour qu'ils soient plus visibles
                bouton.setOnMouseEntered(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #ababab;");
                });
                bouton.setOnMouseExited(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #f3f3f3;");
                });
                menu.getChildren().add(bouton);
                switch (i) {
                    case 0:
                        bouton.setOnAction(event1 -> {
                            TextField newAttribut = new TextField(" new ");
                            newAttribut.setPrefHeight(5);
                            newAttribut.setStyle("-fx-background-color: none;");
                            newAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                            this.actualVBox.ajouterAttribut(newAttribut);
                        });
                        break;
                    case 3:
                        bouton.setOnAction(event2 -> {
                            // on supprime la classe
                            this.pane.getChildren().remove(this.actualVBox);
                            this.pane.getChildren().remove(this.menu);
                        });
                        break;
                }
            }





            this.pane.getChildren().add(menu);
            menu.setPrefSize(200, 100);
            // on met le menu a la position du clique
            System.out.println("coordonnées du clique : " + event.getScreenX() + " " + event.getScreenY());
            menu.setLayoutX(this.sujet.getStartX() - 10);
            menu.setLayoutY(this.sujet.getStartY() - 10);
            // on met 0 padding au menu
            menu.setPadding(new javafx.geometry.Insets(1, 1, 1, 1));
            menu.setStyle("-fx-background-color: rgba(169,165,165,0.28);");
            // si la souris n'est pas au dessus du menu on le supprrime

        } else {
            this.clicked = false;
            this.pane.getChildren().remove(this.menu);
        }
    }

}
