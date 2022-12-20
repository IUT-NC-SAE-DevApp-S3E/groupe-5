package com.example.projet.Vue;

import com.example.projet.Controleur.ControleurBoutonArborescence;
import com.example.projet.Controleur.ControleurBoutonOpenFile;
import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

public class VueDossier extends VBox implements Observateur {


    public VueDossier() {
        super();
        this.setPrefSize(250, 700);
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #f3f3f3");


        HBox boutonHaut = new HBox();
        boutonHaut.setSpacing(10);
        // on met un marginTop de 10px
        boutonHaut.setStyle("-fx-padding: 10 0 0 0");
        for (int i = 1; i <= 4; i++) {
            /**
            Button bouton = new Button();
            ImageView view = new ImageView(new Image("imageBoutonHaut" + i + ".png"));
            view.setFitHeight(35);
            view.setPreserveRatio(true);
            bouton.setGraphic(view);
            boutonHaut.getChildren().add(bouton);
            // on met le background du bouton en transparent
            bouton.setStyle("-fx-background-color: transparent;");
             **/
            /**
             * CETTE PARTIE EST FAIT AVEC LES VRAIES FONT DE FONT AWESEOME
             * A LA PLACE DE SCREEN SHOT DE LA PAGE WEB
             */
            // on créer un bouton avec l'icon folder dedans de font awesome
            Button button = new Button();
            // on met dans le bouton l'icon folder
            switch (i) {
                case 1:
                    // ajout de fichier
                    button.setText("\uf0fe");
                    break;
                case 2:
                    // enregistrement
                    button.setText("\uf0c7");
                    break;
                case 3:
                    // folder open
                    button.setText("\uf07c");
                    button.setOnAction(new ControleurBoutonOpenFile());
                    break;
                case 4:
                    // export icon
                    button.setText("\uF093");
                    break;
            }
            // on met la taille du bouton à 35
            // on met la police du bouton à font awesome
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
        // on enleve les bordure du scrollpane
        listeDossierFichier.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        // on cache la scrollbar
        listeDossierFichier.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listeDossierFichier.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        File file = new File("C:\\Users\\");

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                ImageView view = new ImageView(new Image("folder.png"));
                Button bouton = new Button(" > "+f.getName());

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
                ControleurBoutonArborescence controleurBoutonArborescence = new ControleurBoutonArborescence(f.getPath(), bottomFile, 1);
                bouton.setOnAction(controleurBoutonArborescence);
            }
        }



        HBox boutonafficherCacher = new HBox();
        boutonafficherCacher.setAlignment(Pos.CENTER);
        boutonafficherCacher.setSpacing(2);
        Button bouton = new Button();
        // ImageView imageCacherAfficher = new ImageView(new Image("imageAfficherCacher.png"));
        // imageCacherAfficher.setFitHeight(20);
        // imageCacherAfficher.setPreserveRatio(true);
        // bouton.setGraphic(imageCacherAfficher);
        // imageCacherAfficher.setFitHeight(40);
        // on met dans le bouton un icon de font awesome oeil
        bouton.setText("\uf06e");
        bouton.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 30));
        bouton.setOnMouseEntered(e -> bouton.setStyle("-fx-text-fill: darkgrey;-fx-background-color: transparent;"));
        bouton.setOnMouseExited(e -> bouton.setStyle("-fx-text-fill: black;-fx-background-color: transparent;"));
        // on met la police du bouton en font awesome

        // on met le background du bouton en transparent
        bouton.setStyle("-fx-background-color: transparent;");
        boutonafficherCacher.getChildren().add(bouton);
        String texteAfficherCacher = "MPDA";
        for (int i = 0; i < 4; i++) {
            Button boutonTxt = new Button(texteAfficherCacher.charAt(i) + "");
            boutonTxt.setPrefSize(45, 45);
            // on met le background du bouton en transparent
            boutonTxt.setStyle("-fx-text-fill: black;-fx-background-color: transparent;-fx-font-size: 25px;");
            boutonTxt.setOnMouseEntered(e -> boutonTxt.setStyle("-fx-text-fill: darkgrey;-fx-background-color: transparent;-fx-font-size: 25px;"));
            boutonTxt.setOnMouseExited(e -> boutonTxt.setStyle("-fx-text-fill: black;-fx-background-color: transparent;-fx-font-size: 25px;"));
            // on met la taille du texte du bouton à 20
            boutonafficherCacher.getChildren().add(boutonTxt);
            // on enlève les padding du bouton
            boutonTxt.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        }

        this.getChildren().addAll(boutonHaut, listeDossierFichier, boutonafficherCacher);
    }

    @Override
    public void actualiser(Sujet s) {

    }

    public static void afficherContenuDossier(File f) {
        try {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    System.out.print(" | ");
                    afficherContenuDossier(file);
                }
            } else {
                System.out.println(" > " + f.getName());
            }
        } catch (Exception e) {

        }
    }

}
