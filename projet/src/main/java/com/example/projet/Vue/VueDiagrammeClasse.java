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
//        Classe c = new Classe("C:\\Users\\nzosi\\fghdf\\test\\Money.class","Money");
//        try {
//            c.lectureFichier();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        this.ajouterVueClasse(new VueClasse(c));
    }

    public void ajouterVueClasse(VueClasse vc) {
        this.getChildren().add(vc);
    }

}
