package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Fichier;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

import java.util.ArrayList;

public class VueDiagrammeClasse extends Pane implements Observateur {


    public VueDiagrammeClasse() {
        super();
        this.setPrefSize(1000, 700);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        ArrayList<Fichier> fichiers = s.getListeFichiers();
        for (Fichier f : fichiers) {
            if (f instanceof Classe) {
                this.getChildren().add(new VueClasse((Classe) f));
            }
        }
    }
}