package com.example.projet;

import com.example.projet.Modele.Modele;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Vue.VueClasse;
import com.example.projet.Vue.VueDiagrammeClasse;
import com.example.projet.Vue.VueDossier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        /*
        // Dossier dossier = new Dossier("C:/Users/s5688/Desktop/PROJET/TEST", "nom");
        Dossier dossier = new Dossier("C:\\Users\\s5688\\Desktop\\GIT\\groupe-5\\projet\\", "nom");
        try {
            dossier.lectureDossier();
            System.out.println(dossier.toString(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        launch(args);
    }

    public void start(Stage stage) {
        HBox hbox = new HBox();
        Modele modele = new Modele();
        VueDossier vueDossier = new VueDossier();
        VueDiagrammeClasse vueDiagrammeClasse = new VueDiagrammeClasse();

        modele.enregistrerObservateur(vueDossier);
        modele.enregistrerObservateur(vueDiagrammeClasse);
        modele.notifierObservateur();



        hbox.getChildren().addAll(vueDossier, vueDiagrammeClasse);


        Scene scene = new Scene(hbox);
        stage.setTitle("Diagramme de classe");
        stage.setScene(scene);
        stage.show();
    }
}
