package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe VueMethode qui permet d'afficher une méthode
 */
public class VueMethode extends HBox implements Observateur {

    /**
     * attribut de la classe VueMethode
     */
    private VBox contenueAttribut;
    private TextField nomMethode = new TextField("");
    private Button supprimerAttribut = new Button("");
    public Classe classe;
    private boolean create = false;

    /**
     * constructeur de la classe VueMethode
     * @param contenueMethode le contenu de la methode
     * @param classe le classe qui contient la methode
     */
    public VueMethode(VBox contenueMethode, Classe classe) {
        this.classe = classe;
        // on met l'icon poubelle dans le text du bouton
        this.supprimerAttribut.setText("\uf1f8");
        // on met un style pour le bouton
        this.supprimerAttribut.setStyle("-fx-background-color: none;");
        this.supprimerAttribut.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 15));
        this.supprimerAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        this.supprimerAttribut.setPrefHeight(10);
        supprimerAttribut.setVisible(false);

        this.nomMethode.setStyle("-fx-background-color: none;");
        this.nomMethode.setPrefWidth(235);
        this.nomMethode.setPrefHeight(5);
        this.nomMethode.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));

        this.contenueAttribut = contenueMethode;
        this.getChildren().add(nomMethode);
        this.getChildren().add(supprimerAttribut);

        supprimerAttribut.setOnAction(e -> {
            this.contenueAttribut.getChildren().remove(this);
        });

        // on affiche le bouton supprimer quand on passe la souris au-dessus
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

        this.nomMethode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER") && !this.create) {
                String[] methodes = { " ", " ", " ", " ", " " };
                String[] contenue = nomMethode.getText().split(" ");
                for (int i = 0; i < 3; i++) {
                    methodes[i] = contenue[i];
                }
                String acces = "public";
                // si le nom de l'attribut commence par un _, alors l'attribut est privé
                if (methodes[0].equals("-")) {
                    acces = "private";
                } else if (methodes[0].equals("#")) {
                    acces = "protected";
                }

                System.out.println(Arrays.toString(methodes));
                String nom = methodes[2].split("\\(")[0];
                String[] parametres = nomMethode.getText().split("\\(")[1].split("\\)")[0].split(",");
                System.out.println(Arrays.toString(parametres));
                System.out.println(parametres.length);
                ArrayList<String> parametresList = new ArrayList<>(Arrays.asList(parametres));
                String type = methodes[1];
                this.classe.ajouterCompositionClasse(new Methodes(acces, nom, type, " ", parametresList));
                this.create = true;
            }
        });
    }

    /**
     * methode setNom qui permet de modifier le nom de la methode
     * @param nom le nom de la methode
     */
    public void setNom(String nom) {
        this.nomMethode.setText(nom);
    }

    /**
     * methode supprimerAttribut qui permet de supprimer la methode
     */
    public void supprimerAttribut() {
        this.contenueAttribut.getChildren().remove(this);
        this.classe.suppressionCompositionClasse(this.nomMethode.getText());
    }

    /**
     * methode actualiser qui permet de mettre a jour la vue
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s) {
        // on set la couleur du text
        this.nomAttribut.setStyle("-fx-background-color: none;-fx-text-fill: " + s.getPanelCouleur()[3]+ ";");
    }

}
