package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Fichier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class ControleurBoutonClear implements EventHandler<ActionEvent> {

    private Sujet sujet;

    public ControleurBoutonClear(Sujet sujet) {
        this.sujet = sujet;
    }

    @Override
    public void handle(ActionEvent event) {
        // on créer une liste de fichier vide
        this.sujet.setClear(true);
        try {
            this.sujet.notifierObservateur();
        } catch (Exception e) {
        }
        this.sujet.setClear(false);
    }

}
