package com.example.projet;

import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Vue.VueClasse;
import com.example.projet.Vue.VueDossier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        Dossier dossier = new Dossier("/Users/arthur/Desktop/TD1/", "projet");
        try {
            dossier.lectureDossier();
            System.out.println(dossier.toString(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(Stage stage) {
        HBox hbox = new HBox();
        VueDossier vueDossier = new VueDossier();
        VueClasse vueClasse = new VueClasse();

        hbox.getChildren().addAll(vueDossier, vueClasse);

        Scene scene = new Scene(hbox);
        stage.setTitle("Diagramme de classe");
        stage.setScene(scene);
        stage.show();
    }
}
