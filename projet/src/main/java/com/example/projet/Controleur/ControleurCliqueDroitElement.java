package com.example.projet.Controleur;

import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Utilitaires.Classe;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControleurCliqueDroitElement implements EventHandler<MouseEvent> {


    private Classe classe;
    private CompositionClasse compositionClassed;
    private TextField element;

    public ControleurCliqueDroitElement(Classe classe, CompositionClasse compositionClassed, TextField element) {
        this.classe = classe;
        this.compositionClassed = compositionClassed;
        this.element = element;
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getButton().toString().equals("SECONDARY")) {
            this.element.setContextMenu(null);

        }
    }

}
