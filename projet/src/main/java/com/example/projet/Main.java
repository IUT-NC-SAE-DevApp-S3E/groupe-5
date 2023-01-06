package com.example.projet;

import com.example.projet.Modele.Modele;
import com.example.projet.Utilitaires.TrouverCheminOS;
import com.example.projet.Vue.Fleches.DecorateurFinFleche;
import com.example.projet.Vue.Fleches.VueFleche;
import com.example.projet.Vue.Fleches.VueFlechePointille;
import com.example.projet.Vue.VueDiagrammeClasse;
import com.example.projet.Vue.VueDossier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
        Scene scene = new Scene(hbox);
        stage.setTitle("Diagramme de classe");
        stage.setScene(scene);
        stage.show();
    }
}
