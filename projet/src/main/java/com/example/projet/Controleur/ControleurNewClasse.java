package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurNewClasse implements Observateur, EventHandler<ActionEvent> {
    @Override
    public void actualiser(Sujet s) {

    }

    @Override
    public void handle(ActionEvent event) {
        //this.observateur.actualiser(this);
    }

}
