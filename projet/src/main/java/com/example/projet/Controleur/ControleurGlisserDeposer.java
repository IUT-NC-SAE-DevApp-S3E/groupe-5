package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurGlisserDeposer implements EventHandler<ActionEvent> {

    private String path;
    private Button button;

    public ControleurGlisserDeposer(Button button) {
        this.button = button;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("h^p");
    }
}
