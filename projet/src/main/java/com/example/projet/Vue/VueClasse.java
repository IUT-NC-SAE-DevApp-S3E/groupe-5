
package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VueClasse extends VBox implements Observateur {
    private int startX = 0;
    private int startY = 0;
    private Classe classe;
    private TextField title = new TextField();
    private VBox Attributs = new VBox();
    private VBox Methodes = new VBox();

    public VueClasse(Classe classe) {
        super();
        StackPane Drag = new StackPane();
        this.getChildren().addAll(Drag, title, Attributs, Methodes);
        this.prefWidth(300);
        this.prefHeight(500);

        Drag.setPrefSize(300, 10);
        Drag.setStyle("-fx-background-color: grey;-fx-background-radius: 10 10 0 0;");


        Drag.setOnMousePressed(mouseEvent -> {
            this.startX = (int) mouseEvent.getSceneX();
            this.startY = (int) mouseEvent.getSceneY();
        });


        Drag.setOnMouseDragged(Mouseevent -> {
            this.setLayoutX(Mouseevent.getSceneX() - 250);
            this.setLayoutY(Mouseevent.getSceneY());
        });


        // on met une bordur de 1 px au top et au bottom du VBox Attribut
        this.Attributs.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: grey;-fx-min-height: 10px;");


        this.Methodes.setStyle("-fx-min-height: 10px;");


        // on met le background en noir
        this.setStyle("-fx-background-color: #FCF8A7;-fx-border-color: grey;-fx-border-radius: 10;");

        // on met la taille du titre a 20
        this.title.setPrefHeight(15);
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-background-color: none;");

        this.classe = classe;

        System.out.println(this.classe);

        this.title.setText(this.classe.getNom());
        for (CompositionClasse c : this.classe.getCompositionClasses()) {
            if (c instanceof Attributs) {
                TextField newAttribut = new TextField(c.toString());
                newAttribut.setPrefHeight(15);
                newAttribut.setStyle("-fx-background-color: none;");
                this.Attributs.getChildren().add(newAttribut);
            } else if (c instanceof Methodes) {
                TextField newMethode = new TextField(c.toString());
                newMethode.setPrefHeight(15);
                newMethode.setStyle("-fx-background-color: none;");
                this.Methodes.getChildren().add(newMethode);
            }
        }

    }

    @Override
    public void actualiser(Sujet s) {

    }
}