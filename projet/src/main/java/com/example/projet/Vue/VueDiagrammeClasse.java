package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import javafx.scene.layout.Pane;

public class VueDiagrammeClasse extends Pane implements Observateur {

    public VueDiagrammeClasse() {
        super();
        this.setPrefSize(1000, 700);
    }

    @Override
    public void actualiser(Sujet s) {

    }

    public void ajouterVueClasse(VueClasse vc) {
        this.getChildren().add(vc);
    }

}
