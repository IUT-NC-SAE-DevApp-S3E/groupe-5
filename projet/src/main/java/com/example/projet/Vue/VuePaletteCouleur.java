package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VuePaletteCouleur extends VBox {

    private Sujet sujet;
    private Pane choixCouleur = new Pane();

    private int valueAChanger = 0;

    public VuePaletteCouleur(Sujet s) {
        this.sujet = s;

        String[] color = {"#000000", "#ffffff", "#ff0000", "#00ff00", "#0000ff", "#ffff00", "#00ffff", "#ff00ff"};
        HBox choixCouleur = new HBox();
        for (String c : color) {
            Button button = new Button(c);
            button.setStyle("-fx-background-color: " + c + ";");
            button.setPrefWidth(50);
            button.setPrefHeight(50);
            button.setOnAction(actionEvent -> {
                String[] newPalette = this.sujet.getPanelCouleur();
                newPalette[this.valueAChanger] = c;
                this.sujet.setPanelCouleur(newPalette);
                try {
                    this.sujet.notifierObservateur();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                }
            });
            choixCouleur.getChildren().add(button);
        }





        HBox menu = new HBox();
        // on met une bordure grise de 1px en bas du menu
        menu.setStyle("-fx-border-color: #ababab; -fx-border-width: 0 0 1px 0;");
        this.getChildren().addAll(menu, choixCouleur);
        String[] titreBoutonsMenu = {"Couleur de fond", "Couleur Drag", "Couleur fond classe", "Couleur Texte"};
        for (int i = 0; i < this.sujet.getPanelCouleur().length; i++) {
            Button bouton = new Button(titreBoutonsMenu[i]);
            bouton.setStyle("-fx-background-color: none;");
            bouton.setPrefWidth(200);
            bouton.setPrefHeight(50);
            bouton.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
            menu.getChildren().add(bouton);
            bouton.setOnMouseEntered(mouseEvent -> {
                bouton.setStyle("-fx-background-color: #ababab;");
            });
            bouton.setOnMouseExited(mouseEvent -> {
                bouton.setStyle("-fx-background-color: none;");
            });
            switch (i) {
                case 0:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 0;
                        bouton.setStyle("-fx-background-color: #ababab;");
                    });
                    break;
                case 1:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 1;
                        bouton.setStyle("-fx-background-color: #ababab;");
                    });
                    break;
                case 2:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 2;
                        bouton.setStyle("-fx-background-color: #ababab;");
                    });
                    break;
                case 3:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 3;
                        bouton.setStyle("-fx-background-color: #ababab;");
                    });
                    break;
            }
        }
    }
}
