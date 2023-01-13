package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Classe ControleurBoutonRecharger qui permet de gerer le bouton recharger
 */
public class ControleurBoutonRecharger implements EventHandler<ActionEvent> {

    private Sujet sujet;

    /***
     * Constructeur du controleur du bouton ouvrir dossier
     * @param s
     */
    public ControleurBoutonRecharger(Sujet s) {
        this.sujet = s;
    }

    /**
     * Méthode qui permet de recharger la page
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            this.sujet.setReplacer(true);
            this.sujet.notifierObservateur();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }

}
