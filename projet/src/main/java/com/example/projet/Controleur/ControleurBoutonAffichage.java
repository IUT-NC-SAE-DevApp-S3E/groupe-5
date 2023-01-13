package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.net.MalformedURLException;

/**
 * Classe qui permet de gerer les boutons d'affichages
 */
public class ControleurBoutonAffichage implements EventHandler<ActionEvent> {

    /**
     * Attribut de la classe ControleurBoutonAffichage
     */
    private Sujet sujet;
    private Button btn;
    private boolean pressed = false;

    /**
     * Constructeur de la classe ControleurBoutonAffichage
     * initialise les attributs
     * @param s le sujet
     * @param btn le bouton
     */
    public ControleurBoutonAffichage(Sujet s, Button btn)
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
        if (this.pressed) {
            this.btn.setStyle("-fx-text-fill: darkgrey;-fx-background-color: transparent;-fx-font-size: 25px;");
        } else {
            this.btn.setStyle("-fx-text-fill: black;-fx-background-color: transparent;-fx-font-size: 25px;");
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
