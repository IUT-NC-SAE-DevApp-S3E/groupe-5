package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

public class VueDiagrammeClasse extends Pane implements Observateur {

    public VueDiagrammeClasse() {
        super();
        this.setPrefSize(1000, 700);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().add(new Rectangle(10,10));
    }

    public void ajouterVueClasse(VueClasse vc) {
        this.getChildren().add(vc);
    }

}
