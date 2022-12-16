package com.example.projet.Vue;

import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VueClasse extends Pane implements Observateur{

    public VueClasse() {
        super();
    }

    @Override
    public void actualiser(Sujet s) {

    }

}
