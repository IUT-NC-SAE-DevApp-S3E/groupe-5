package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.net.MalformedURLException;

public class ControleurBoutonAffichageOeil implements EventHandler<ActionEvent> {

    private Sujet sujet;
    private Button btn;
    private boolean pressed = false;

    public ControleurBoutonAffichageOeil(Sujet s, Button btn)
    {
        this.sujet = s;
        this.btn = btn;
    }

    /**
     * methode handle qui permet de gerer les boutons d'affichages
     * @param actionEvent l'evenement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.pressed = !this.pressed;
        this.btn.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 30));
        if (this.pressed) {
            // this.btn.setStyle("-fx-text-fill: darkgrey;-fx-background-color: transparent;-fx-font-size: 25px;");
            // on met l'icon de l'oeil barré
            this.btn.setText("\uf070");
        } else {
            // this.btn.setStyle("-fx-text-fill: black;-fx-background-color: transparent;-fx-font-size: 25px;");
            // on met l'icon de l'oeil
            this.btn.setText("\uf06e");
        }
        String id = actionEvent.getSource().toString().split("'")[1];
        switch (id)
        {
            case "M":
            case "P":
            case "D":
            case "A":
                break;

            default:
                this.sujet.inverserAffichage();
                break;
        }
        this.sujet.changerAffichage(id);
        try {
            sujet.notifierObservateur();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}