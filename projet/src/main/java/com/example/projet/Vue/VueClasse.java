
package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Controleur.ControleurCliqueDroit;
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

    /**
     * Constructeur de la classe VueClasse
     * une vueClasse représente une classe dans le diagramme de classe
     * une VueClasse est un VBox qui contiert:
     * - un StackPane qui permet de déplacer (drag) la classe
     * - un TextField qui représente le nom de la classe
     * - un VBox qui représente les attributs de la classe
     * - un VBox qui représente les méthodes de la classe
     *
     * @param classe
     */
    public VueClasse(Classe classe) {
        super();

        // stackPane permet de déplacer la classe
        StackPane Drag = new StackPane();
        // on dit qu'on peut éditer le titre de la classe
        this.title.setEditable(true);
        // on ajoute les éléments a la vue
        this.getChildren().addAll(Drag, title, Attributs, Methodes);
        this.prefWidth(200);
        this.prefHeight(400);

        // stylisation
        Drag.setPrefSize(200, 20);
        Drag.setStyle("-fx-background-color: rgba(168,163,163,0.66);-fx-background-radius: 10 10 0 0;");

        /**
         *
         */
        Drag.setOnMousePressed(mouseEvent -> {
            this.startX = (int) mouseEvent.getSceneX();
            this.startY = (int) mouseEvent.getSceneY();
        });


        Drag.setOnMouseDragged(Mouseevent -> {
            if (Mouseevent.getButton().toString().equals("PRIMARY")) {
                this.setLayoutX(Mouseevent.getSceneX() - 250);
                this.setLayoutY(Mouseevent.getSceneY());
            }
        });


        // on met une bordur de 1 px au top et au bottom du VBox Attribut
        this.Attributs.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: grey;-fx-min-height: 8px;");


        this.Methodes.setStyle("-fx-min-height: 8px;");


        // on met le background en noir
        this.setStyle("-fx-background-color: #FCF8A7;-fx-border-color: grey;-fx-border-radius: 10;");

        // on met la taille du titre a 20
        this.title.setPrefHeight(10);
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-background-color: none;");

        this.classe = classe;


        this.title.setText(this.classe.getNom());
        // on met le titre en gras
        this.title.setFont(javafx.scene.text.Font.font("System", 20));
        for (CompositionClasse c : this.classe.getCompositionClasses()) {
            if (c instanceof Attributs) {
                TextField newAttribut = new TextField(c.toString());
                newAttribut.setPrefHeight(5);
                newAttribut.setStyle("-fx-background-color: none;");
                newAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                this.Attributs.getChildren().add(newAttribut);
            } else if (c instanceof Methodes) {
                TextField newMethode = new TextField(c.toString());
                newMethode.setPrefHeight(5);
                newMethode.setStyle("-fx-background-color: none;");
                newMethode.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                this.Methodes.getChildren().add(newMethode);
            }
        }


    }

    @Override
    public void actualiser(Sujet s) {

    }


    /**
     * getter des vbox
     */
    public VBox getAttributs() {
        return this.Attributs;
    }

    public VBox getMethodes() {
        return this.Methodes;
    }

    /**
     * méthode ajouterAttribut
     *
     * @param attribut ajoute un attribut a la classe
     */
    public void ajouterAttribut(TextField attribut) {
        this.Attributs.getChildren().add(attribut);
    }

    /**
     * méthode ajouterMethode
     *
     * @param methode ajoute une methode a la classe
     */
    public void ajouterMethode(TextField methode) {
        this.Methodes.getChildren().add(methode);
    }

    /**
     * méthode getClasse
     *
     * @return la classe
     */
    public Classe getClasse() {
        return this.classe;
    }
}