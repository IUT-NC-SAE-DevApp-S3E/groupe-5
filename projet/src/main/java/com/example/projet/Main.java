package com.example.projet;

import com.example.projet.Modele.Modele;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.TrouverCheminOS;
import com.example.projet.Vue.VueDiagrammeClasse;
import com.example.projet.Vue.VueDossier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        HBox hbox = new HBox();
        Modele modele = new Modele();
        VueDiagrammeClasse vueDiagrammeClasse = new VueDiagrammeClasse(modele);
        VueDossier vueDossier = new VueDossier(modele, TrouverCheminOS.getChemin());

        modele.enregistrerObservateur(vueDossier);
        modele.enregistrerObservateur(vueDiagrammeClasse);
        modele.notifierObservateur();

        hbox.getChildren().addAll(vueDossier, vueDiagrammeClasse);
        // la vueDiagrammeDeClasse s'adapte a la fentre
        vueDiagrammeClasse.prefWidthProperty().bind(hbox.widthProperty());
        Scene scene = new Scene(hbox, 1000, 600);

        modele.notifierObservateur();

        stage.setTitle("Diagramme de classe");
        stage.setScene(scene);
        stage.show();

        // on met une icon au stage
        stage.getIcons().add(new Image("file:src/main/resources/icon.png"));

    }
}
