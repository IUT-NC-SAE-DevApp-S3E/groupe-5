package com.example.projet.Vue;

import com.example.projet.Controleur.*;
import com.example.projet.Modele.Modele;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.TrouverCheminOS;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.Serializable;

/**
 * classe VueDossier qui permet d'afficher les dossiers sur le cote de la page
 */
public class VueDossier extends VBox implements Observateur {

    /**
     * sujet, le sujet
     * listeDossierFichier la liste des dossiers et fichiers
     */
    private Sujet sujet;
    private ScrollPane listeDossierFichier = new ScrollPane();

    /**
     * constructeur de la classe VueDossier
     * @param s le sujet
     * @param chemin le chemin de la liste de dossier
     */
    public VueDossier(Sujet s, String chemin) {
        super();
        this.sujet = s;
        this.setPrefSize(250, 700);
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #f3f3f3");


        HBox boutonHaut = new HBox();
        boutonHaut.setSpacing(10);
        // on met un marginTop de 10px
        boutonHaut.setStyle("-fx-padding: 10 0 0 0");
        for (int i = 1; i <= 6; i++) {
            // on créer un bouton avec l'icon folder de font awesome
            Button button = new Button();
            // on met dans le bouton l'icon folder
            switch (i) {
                case 1:
                    // ajout de fichier
                    button.setText("\uf0c7");
                    button.setOnAction(new ControleurBoutonEnregistrer(this.sujet));
                    break;
                case 2:
                    // enregistrement
                    button.setText("\uF093");
                    break;
                case 3:
                    // folder open
                    button.setText("\uf07c");
                    button.setOnAction(new ControleurBoutonOuvrirDossier(this.sujet));
                    break;
                case 4:
                    // export icon
                    button.setText("\uf15b");
                    button.setOnAction(new ControleurBoutonOpenFile(this.sujet));
                    break;
                case 5:
                    // icon fichier
                    button.setText("\uf0fe");
                    button.setOnAction(new ControleurNewClasse(this.sujet));
                    break;
                case 6:
                    // icon poubelle
                    button.setText("\uf1f8");
                    button.setOnAction(new ControleurBoutonClear(this.sujet));
                    break;
            }
            // on met la taille du bouton à 35
            // on met la police du bouton à fontawesome
            // on met que la souris est sur le bouton la font devient gris sinon elle reste en #3333
            button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: darkgrey;-fx-background-color: transparent;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: black;-fx-background-color: transparent;"));
            // on met la police du bouton en font awesome
            button.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 30));
            // on met le background du bouton en transparent
            button.setStyle("-fx-background-color: transparent;-fx-text-fill: black;");
            button.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
            boutonHaut.getChildren().add(button);

        }
        // on met un margin a gauche de 10 px
        boutonHaut.setMargin(boutonHaut.getChildren().get(0), new javafx.geometry.Insets(0, 0, 0, 10));


        ScrollPane listeDossierFichier = new ScrollPane();
        VBox vBox = new VBox();
        listeDossierFichier.setContent(vBox);
        // le vbox prend la taille du scrollpane
        vBox.setPrefWidth(listeDossierFichier.getPrefWidth());
        listeDossierFichier.setPrefSize(250, 510);
        // on enlève les bordures du scrollpane
        listeDossierFichier.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        // on cache la scrollbar
        listeDossierFichier.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listeDossierFichier.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        File file = new File(TrouverCheminOS.getChemin());

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                ImageView view = new ImageView(new Image("folder.png"));
                Button bouton = new Button(" > " + f.getName());

                // on ajoute l'image a gauche du bouton
                bouton.setGraphic(view);
                // la taille de l'image est de 20x20
                view.setFitHeight(20);
                view.setPreserveRatio(true);

                vBox.getChildren().add(bouton);
                // la width du bouton est la width du scrollpane
                bouton.setPrefWidth(listeDossierFichier.getPrefWidth());
                // on cole le text a gauche
                bouton.setAlignment(Pos.BASELINE_LEFT);
                // on rend le bouton transparent
                bouton.setStyle("-fx-background-color: transparent;");

                // on met le controlleur sur le bouton
                VBox bottomFile = new VBox();
                // on met un spacing de 3
                bottomFile.setSpacing(3);

                vBox.getChildren().add(bottomFile);
                // la width du vbox est la width du scrollpane
                bottomFile.setPrefWidth(listeDossierFichier.getPrefWidth());
                ControleurBoutonArborescence controleurBoutonArborescence = new ControleurBoutonArborescence(f.getPath(), bottomFile, 1, this.sujet);
                bouton.setOnAction(controleurBoutonArborescence);
            }
        }
        afficherDossier(chemin);


        HBox boutonafficherCacher = new HBox();
        boutonafficherCacher.setAlignment(Pos.CENTER);
        boutonafficherCacher.setSpacing(2);
        Button bouton = new Button();
        bouton.setText("\uf06e");
        bouton.setOnAction(new ControleurBoutonAffichageOeil(this.sujet, bouton));
        bouton.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 30));

        // on met le background du bouton en transparent
        bouton.setStyle("-fx-background-color: transparent;");
        boutonafficherCacher.getChildren().add(bouton);
        String texteAfficherCacher = "MPDA";
        for (int i = 0; i < 4; i++) {
            Button boutonTxt = new Button(texteAfficherCacher.charAt(i) + "");
            boutonTxt.setPrefSize(45, 45);
            // on met le background du bouton en transparent
            boutonTxt.setStyle("-fx-text-fill: black;-fx-background-color: transparent;-fx-font-size: 25px;");
            // on met la taille du texte du bouton à 20
            boutonafficherCacher.getChildren().add(boutonTxt);
            // on enlève les padding du bouton
            boutonTxt.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
            // On ajoute l'action du bouton
            boutonTxt.setOnAction(new ControleurBoutonAffichage(this.sujet, boutonTxt));

        }

        this.getChildren().addAll(boutonHaut, this.listeDossierFichier, boutonafficherCacher);
    }

    /**
     * methode actualiser qui permet d'actualiser la liste de dossier lorsqu'une modifications a ete faites
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s) {
        afficherDossier(s.getCheminArborescence());
    }

    /**
     * methode afficherDossier qui permet d'afficher la liste de fichier et dossier
     * @param chemin le chemin ou l'on cherche les dossiers
     */
    public void afficherDossier(String chemin)
    {
        VBox vBox = new VBox();
        listeDossierFichier.setContent(vBox);
        // le vbox prend la taille du scrollpane
        vBox.setPrefWidth(listeDossierFichier.getPrefWidth());
        listeDossierFichier.setPrefSize(250, 600);
        // on enlève les bordures du scrollpane
        listeDossierFichier.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        // on cache la scrollbar
        listeDossierFichier.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listeDossierFichier.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        File file = new File(chemin);

        for (File f : file.listFiles()) {
            if (!f.getName().contains("$") && f.getName().charAt(0) != '.') {
                if (f.isDirectory()) {
                    ImageView view = new ImageView(new Image("folder.png"));
                    Button bouton = new Button(" > " + f.getName());

                    // on ajoute l'image a gauche du bouton
                    bouton.setGraphic(view);
                    // la taille de l'image est de 20x20
                    view.setFitHeight(20);
                    view.setPreserveRatio(true);

                    vBox.getChildren().add(bouton);
                    // la width du bouton est la width du scrollpane
                    bouton.setPrefWidth(listeDossierFichier.getPrefWidth());
                    // on cole le text a gauche
                    bouton.setAlignment(Pos.BASELINE_LEFT);
                    // on rend le bouton transparent
                    bouton.setStyle("-fx-background-color: transparent;");

                    // on met le controlleur sur le bouton
                    VBox bottomFile = new VBox();
                    // on met un spacing de 5
                    bottomFile.setSpacing(3);

                    vBox.getChildren().add(bottomFile);
                    // la width du vbox est la width du scrollpane
                    bottomFile.setPrefWidth(listeDossierFichier.getPrefWidth());
                    ControleurBoutonArborescence controleurBoutonArborescence = new ControleurBoutonArborescence(f.getPath(), bottomFile, 1, this.sujet);
                    bouton.setOnAction(controleurBoutonArborescence);
                }
            }
        }
    }

}
