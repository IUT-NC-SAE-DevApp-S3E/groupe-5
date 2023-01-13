package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Classe VuePaletteCouleur qui permet d'afficher la palette de couleur
 */
public class VuePaletteCouleur extends VBox {

    /**
     * attribut de la classe VuePaletteCouleur
     */
    private Sujet sujet;
    private Pane choixCouleur = new Pane();
    private Button bouton1 = new Button();
    private Button bouton2 = new Button();
    private Button bouton3 = new Button();
    private Button bouton4 = new Button();

    private int valueAChanger = 0;

    /**
     * constructeur de la classe VuePaletteCouleur
     * @param s le sujet
     */
    public VuePaletteCouleur(Sujet s) {
        this.sujet = s;

        String[] color = {"#000000", "#f3f3f3f3", "#ff0000", "#00ff00", "#0000ff", "#ffff00", "#00ffff", "#ff00ff"};
        HBox choixCouleur = new HBox();
        choixCouleur.setAlignment(javafx.geometry.Pos.CENTER);
        choixCouleur.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));
        for (String c : color) {
            Button button = new Button("");
            button.setStyle("-fx-background-color: " + c + ";-fx-border-color: black;-fx-border-width: 1px;");
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

        choixCouleur.setSpacing(10);

        HBox menu = new HBox();
        menu.setAlignment(javafx.geometry.Pos.CENTER);
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
            switch (i) {
                case 0:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 0;
                        this.mettreEnBlanc();
                        bouton.setStyle("-fx-background-color: darkgrey;");
                    });
                    this.bouton1 = bouton;
                    break;
                case 1:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 1;
                        this.mettreEnBlanc();
                        bouton.setStyle("-fx-background-color: darkgrey;");
                    });
                    this.bouton2 = bouton;
                    break;
                case 2:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 2;
                        this.mettreEnBlanc();
                        bouton.setStyle("-fx-background-color: darkgrey;");
                    });
                    this.bouton3 = bouton;
                    break;
                case 3:
                    bouton.setOnAction(actionEvent -> {
                        this.valueAChanger = 3;
                        this.mettreEnBlanc();
                        bouton.setStyle("-fx-background-color: darkgrey;");
                    });
                    this.bouton4 = bouton;
                    break;
            }
        }
    }

    /**
     * méthode qui permet de mettre en blanc les boutons
     */
    public void mettreEnBlanc() {
        this.bouton1.setStyle("-fx-background-color: none;");
        this.bouton2.setStyle("-fx-background-color: none;");
        this.bouton3.setStyle("-fx-background-color: none;");
        this.bouton4.setStyle("-fx-background-color: none;");
    }
}
