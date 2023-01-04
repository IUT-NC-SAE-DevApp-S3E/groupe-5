package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Fichier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class ControleurBoutonClear implements EventHandler<ActionEvent> {

    private Sujet sujet;

    /**
     * Constructeur du controleur du bouton clear
     * prend en paramètre le sujet pour avoir accès au boolean clear
     * @param sujet
     */
    public ControleurBoutonClear(Sujet sujet) {
        this.sujet = sujet;
    }

    @Override
    /**
     * méthode qui met a true dans le sujet le boolean clear
     * de se fait lorsque l'on notifie les observateurs ils vont regarder dans le sujet si il faut clear et
     * agir en fonction
     * @param event
     */
    public void handle(ActionEvent event) {
        // on met le boolean clear a true
        this.sujet.setClear(true);
        // on notifie les observateurs
        try {
            this.sujet.notifierObservateur();
        } catch (Exception e) {
        }
    }

}
