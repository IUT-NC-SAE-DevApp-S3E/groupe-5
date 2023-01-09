package com.example.projet.Vue;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class VueAttribut extends HBox {

    private VBox contenueAttribut;
    private TextField nomAttribut = new TextField("");
    private Button supprimerAttribut = new Button("");

    public VueAttribut(VBox contenueAttribut) {
        // on met l'icon poubelle dans le text du bouton
        this.supprimerAttribut.setText("\uf1f8");
        // on met un style pour le bouton
        this.supprimerAttribut.setStyle("-fx-background-color: none;");
        this.supprimerAttribut.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 15));
        this.supprimerAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        this.supprimerAttribut.setPrefHeight(10);
        supprimerAttribut.setVisible(false);

        this.nomAttribut.setStyle("-fx-background-color: none;");
        this.nomAttribut.setPrefWidth(235);
        this.nomAttribut.setPrefHeight(5);
        this.nomAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));

        this.contenueAttribut = contenueAttribut;
        this.getChildren().add(nomAttribut);
        this.getChildren().add(supprimerAttribut);

        supprimerAttribut.setOnAction(e -> {
            this.contenueAttribut.getChildren().remove(this);
        });

        // on affiche le bouton supprimer quand on passe la souris au dessus
        this.setOnMouseEntered(mouseEvent -> {
            supprimerAttribut.setVisible(true);
        });

        // on cache le bouton supprimer quand on sort la souris du bouton
        this.setOnMouseExited(mouseEvent -> {
            supprimerAttribut.setVisible(false);
        });

        this.setStyle("-fx-background-color: none;");
        this.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        this.setSpacing(0);
        this.setHeight(5);
        // on centre verticalement le contenu de la HBox
        this.setAlignment(javafx.geometry.Pos.CENTER);
    }

    /**
     * setNom
     * @param nom
     */
    public void setNom(String nom) {
        this.nomAttribut.setText(nom);
    }

    /**
     * supprimerAttribut
     */
    public void supprimerAttribut() {
        this.contenueAttribut.getChildren().remove(this);
    }


}
