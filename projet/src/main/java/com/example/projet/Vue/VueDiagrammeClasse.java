package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Utilitaires.Fichier;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

import java.util.ArrayList;

public class VueDiagrammeClasse extends ScrollPane implements Observateur {


    private Pane pane = new Pane();
    private int startX = 0;
    private int startY = 0;

    public VueDiagrammeClasse() {
        super();
        this.setContent(this.pane);
        this.setPrefSize(1000, 700);
    }

    @Override
    public void actualiser(Sujet s) {
        if (!s.getClear()) {
            System.out.println("je ne suis pas a clear");
            ArrayList<Classe> fichiers = s.getListeFichiers();

            System.out.println("il y a : " + this.pane.getChildren().size() + " enfants");
            System.out.println("il y a : "+fichiers.size()+" fichiers");
            if(this.pane.getChildren().size() < fichiers.size()) {
                System.out.println("je suis dans la boucle");
                for (int i = this.pane.getChildren().size(); i < fichiers.size(); i++) {
                    if (fichiers.get(i) instanceof Classe) {
                        System.out.println("je suis dans la boucle de classe=====================");
                        this.creerVisuelClasse((Classe) fichiers.get(i));
                    }
                }
            }
        } else {
            System.out.println("je suis a clear");
            this.pane.getChildren().clear();
            s.clearFichier();
            s.setClear(false);
            this.startX = 0;
            this.startY = 0;
        }

    }

    /**
     * méthode creerVisuelClasse qui va ajouter une classe au pane
     * et faire en sorte que les classes ne se superposent pas
     * @param classe
     */
    public void creerVisuelClasse(Classe classe) {
        VueClasse vueClasse = new VueClasse(classe);
        this.pane.getChildren().add(vueClasse);
        vueClasse.setLayoutX(this.startX);
        vueClasse.setLayoutY(this.startY);
        this.startY += 210;
        if (this.startY > 1000 ) {
            this.startY = 0;
            this.startX += 300;
        }
    }
}