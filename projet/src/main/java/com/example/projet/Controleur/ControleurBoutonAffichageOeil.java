package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.net.MalformedURLException;

/**
 * Classe ControleurBoutonAffichageOeil qui permet de gerer les boutons d'affichages
 */
public class ControleurBoutonAffichageOeil implements EventHandler<ActionEvent> {

    /**
     * Attribut de la classe ControleurBoutonAffichageOeil
     */
    private Sujet sujet;
    private Button btn;
    private boolean pressed = false;

    /**
     * Constructeur de la classe ControleurBoutonAffichageOeil
     * initialise les attributs
     * @param s le sujet
     * @param btn le bouton
     */
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
            // on met l'icone de l'oeil barré
            this.btn.setText("\uf070");
        } else {
            // on met l'icone de l'oeil
            this.btn.setText("\uf06e");
        }
        this.sujet.inverserAffichage();
        try {
            sujet.notifierObservateur();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}