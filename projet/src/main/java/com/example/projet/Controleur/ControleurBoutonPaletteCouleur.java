package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.VuePaletteCouleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleurBoutonPaletteCouleur implements EventHandler<ActionEvent> {

    private Sujet sujet;

    public ControleurBoutonPaletteCouleur(Sujet s) {
        this.sujet = s;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // on créer une nouvelle VuePalette
        VuePaletteCouleur vue = new VuePaletteCouleur(this.sujet);
        // on ouvre une nouvelle fenêtre
        Scene scene = new Scene(vue, 600, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
