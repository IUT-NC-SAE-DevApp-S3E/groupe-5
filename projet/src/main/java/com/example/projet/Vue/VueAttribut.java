package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.Serializable;

/**
 * Classe VueAttribut qui permet d'afficher les attributs d'une classe
 */
public class VueAttribut extends HBox implements Observateur
{

    /**
     * attribut de la classe VueAttribut
     * contenueAttribut une VBox
     * nomAttribut qui représente le nom de l'attribut
     * supprimerAttribut qui est un bouton permettant de supprimer un attribut
     * et classe, la classe qui contient l'attribut
     */
    private VBox contenueAttribut;
    private TextField nomAttribut = new TextField("");
    private Button supprimerAttribut = new Button("");
    public Classe classe;
    private boolean create = false;

    /**
     * constructeur de la classe VueAttribut
     * @param contenueAttribut la VBox qui contient l'attribut
     * @param classe la classe qui contient l'attribut
     */
    public VueAttribut(VBox contenueAttribut, Classe classe) {
        this.classe = classe;
        // on met l'icône poubelle dans le text du bouton
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

        // on affiche le bouton supprimé quand on passe la souris au-dessus
        this.setOnMouseEntered(mouseEvent -> {
            supprimerAttribut.setVisible(true);
        });

        // on cache le bouton supprimé quand on sort la souris du bouton
        this.setOnMouseExited(mouseEvent -> {
            supprimerAttribut.setVisible(false);
        });

        this.setStyle("-fx-background-color: none;");
        this.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        this.setSpacing(0);
        this.setHeight(5);
        // on centre verticalement le contenu de la HBox
        this.setAlignment(javafx.geometry.Pos.CENTER);


        // on met un controleur sur le nom de l'attribut quand on appuie sur entrer
        this.nomAttribut.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER") && !this.create) {
                String[] attribut = { " ", " ", " ", " " };
                String[] contenue = nomAttribut.getText().split(" ");
                for (int i = 0; i < contenue.length; i++) {
                    attribut[i] = contenue[i];
                }
                String acces = "public";
                // si le nom de l'attribut commence par un _, alors l'attribut est privé
                if (attribut[0].equals("-")) {
                    acces = "private";
                } else if (attribut[0].equals("#")) {
                    acces = "protected";
                }
                String nom = attribut[2];
                String type = attribut[1];
                this.classe.ajouterCompositionClasse(new Attributs(acces, type, nom, " "));
                this.create = true;
            }
        });
    }

    /**
     * methode setNom, qui permet de modifier le nom
     * @param nom le nom de l'attribut
     */
    public void setNom(String nom) {
        this.nomAttribut.setText(nom);
    }

    /**
     * methode supprimerAttribut qui permet de supprimer un attribut
     */
    public void supprimerAttribut() {
        this.contenueAttribut.getChildren().remove(this);
        this.classe.suppressionCompositionClasse(this.nomAttribut.getText());
    }

    /**
     * methode actiualiser qui permet de mettre à jour l'affichage
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s) {
        this.nomAttribut.setStyle("-fx-background-color: none;-fx-text-fill: " + s.getPanelCouleur()[3]+ ";");
    }

    /**
     * getTextField
     */
    public TextField getTextField() {
        return this.nomAttribut;
    }
}
