package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Observateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurNewClasse implements EventHandler<ActionEvent> {
    private Sujet sujet;

    /**
     * Constructeur du controleur
     * @param s
     */
    public ControleurNewClasse(Sujet s) {
        this.sujet = s;
    }

    /**
     * Methode qui permet de creer une nouvelle classe
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {
        // On cree un nouveau objet de type Classe qu'on donn comme nom "new"
        Classe c = new Classe("new");
        // on ajoute la classe a la liste des classes du sujet
        this.sujet.ajouterFichier(c);
        try {
            // on notifie les observateurs pour pouvoir afficher la nouvelle classe
            // cette nouvelle classe n'a pas d'attributs ni de methodes
            this.sujet.notifierObservateur();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }
}
