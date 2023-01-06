package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.VueClasse;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ControleurCliqueDroitClasse implements EventHandler<MouseEvent> {


    private final Sujet sujet;
    private final Pane pane;
    private VBox menu;
    private VBox menuDependance = new VBox();

    private final VueClasse actualVBox;
    private boolean clicked = false;

    public ControleurCliqueDroitClasse(Sujet sujet, Pane pane, VueClasse actualVBox) {
        this.sujet = sujet;
        this.pane = pane;
        this.actualVBox = actualVBox;

        this.pane.setOnMousePressed(mouseEvent -> {
            // quand un clique droit est effectué on récupère les coordonnées du clique
            // pour pouvoir le menu ensuite au bon endroit
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
                            // si on appuie sur le bouton ajouter un attribut dans le vbox des attributs
                            TextField newAttribut = new TextField(" new ");
                            newAttribut.setPrefHeight(5);
                            newAttribut.setStyle("-fx-background-color: none;");
                            newAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                            // on ajoute aussi l'attribut dans la classe pour quelle soit dans le modèle
                            this.actualVBox.ajouterAttribut(newAttribut);
                        });
                        break;
                    case 1:
                        bouton.setOnAction(event1 -> {
                            // si on appuie sur le bouton ajouter une methode dans le vbox des methodes
                            TextField newMethode = new TextField(" new ");
                            newMethode.setPrefHeight(5);
                            newMethode.setStyle("-fx-background-color: none;");
                            newMethode.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                            // on ajoute aussi la methode dans la classe pour quelle soit dans le modèle
                            this.actualVBox.ajouterMethode(newMethode);
                        });
                        break;
                    case 2:
                        bouton.setOnAction(event1 -> {
                            VBox menuListeDépendance = this.actualVBox.afficherMenuDependance();
                            this.pane.getChildren().add(menuListeDépendance);
                            menuListeDépendance.setLayoutX(menu.getLayoutX()+menu.getWidth());
                            this.menuDependance = menuListeDépendance;
                        });
                        break;
                    case 3:
                        bouton.setOnAction(event2 -> {
                            // on supprime la classe
                            this.pane.getChildren().remove(this.actualVBox);
                            // on remove aussi la classe du modèle
                            this.sujet.supprimerFichier(this.actualVBox.getClasse());
                            this.pane.getChildren().remove(this.menu);
                            this.pane.getChildren().remove(this.menuDependance);
                        });
                        break;
                }
            }
            // on ajoute le visuel du menu au pane
            this.pane.getChildren().add(menu);
            menu.setPrefSize(200, 100);
            // on met le menu a la position du clique
            System.out.println("coordonnées du clique : " + event.getScreenX() + " " + event.getScreenY());
            // on place le menu a la position du clique
            menu.setLayoutX(this.sujet.getStartX() - 10);
            menu.setLayoutY(this.sujet.getStartY() - 10);
            // on met 0 padding au menu
            menu.setPadding(new javafx.geometry.Insets(1, 1, 1, 1));
            menu.setStyle("-fx-background-color: rgba(169,165,165,0.28);");
            // si la souris n'est pas au dessus du menu on le supprrime

            /**
             * si on a déjà fait un clique droit
             */
        } else {
            // on supprime le menu
            // on met le boolean cliquer a false pour pouvoir refaire un clique droit
            this.clicked = false;
            this.pane.getChildren().remove(this.menu);
            this.pane.getChildren().remove(this.menuDependance);
        }
    }

}
