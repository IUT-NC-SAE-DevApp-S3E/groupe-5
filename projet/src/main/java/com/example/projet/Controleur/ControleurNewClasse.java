package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Observateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurNewClasse implements EventHandler<ActionEvent> {
    private Sujet sujet;

    public ControleurNewClasse(Sujet s) {
        this.sujet = s;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("new classe");
        Classe c = new Classe("new");
        this.sujet.ajouterFichier(c);
        try {
            this.sujet.notifierObservateur();
        } catch (Exception e) {

        }
    }
}
