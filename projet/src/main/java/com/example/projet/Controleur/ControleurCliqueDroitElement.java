package com.example.projet.Controleur;

import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Utilitaires.Classe;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Classe controleur qui permet de gerer le menu après un clique droit sur une classe
 */
public class ControleurCliqueDroitElement implements EventHandler<MouseEvent> {

    /**
     * Attribut classe de type Classe qui est la classe sur laquelle on a fait un clique droit
     */
    private Classe classe;
    /**
     * compositionClassed qui est la composition de la classe sur laquelle on a fait un clique droit
     */
    private CompositionClasse compositionClassed;
    private TextField element;

    /**
     * Constructeur du controleur
     * @param classe la classe sur laquelle on a fait un clique droit
     * @param compositionClassed la composition de la classe sur laquelle on a fait un clique droit
     * @param element
     */
    public ControleurCliqueDroitElement(Classe classe, CompositionClasse compositionClassed, TextField element) {
        this.classe = classe;
        this.compositionClassed = compositionClassed;
        this.element = element;
    }

    /**
     * Methode qui permet de gerer le menu après un clique droit sur une classe
     * @param event l'evenement
     */
    @Override
    public void handle(MouseEvent event) {
        if(event.getButton().toString().equals("SECONDARY")) {
            this.element.setContextMenu(null);

        }
    }

}
