package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.VuePaletteCouleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe ControleurBoutonPaletteCouleur qui permet de gerer le bouton palette couleur
 */
public class ControleurBoutonPaletteCouleur implements EventHandler<ActionEvent> {

    private Sujet sujet;

    /**
     * Constructeur de la classe ControleurBoutonPaletteCouleur
     * @param s le sujet
     */
    public ControleurBoutonPaletteCouleur(Sujet s) {
        this.sujet = s;
    }

    /**
     * methode handle qui permet de gerer le bouton palette couleur
     * @param actionEvent l'evenement
     */
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
