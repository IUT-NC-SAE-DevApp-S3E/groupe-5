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
        ArrayList<Fichier> fichiers = s.getListeFichiers();
        System.out.println("il y a : "+fichiers.size()+" fichiers");
        if(this.pane.getChildren().size() < fichiers.size()) {
            System.out.println("je suis dans la boucle");
            for (int i = this.pane.getChildren().size(); i < fichiers.size(); i++) {
                if (fichiers.get(i) instanceof Classe) {
                    this.creerVisuelClasse((Classe) fichiers.get(i));
                } else if (fichiers.get(i) instanceof Dossier) {
                    this.creerContenueDossier((Dossier) fichiers.get(i));
                }
            }
        }

    }

    /**
     * méthode creerContenueDossier
     * cette méthode créer un le visuel du contenue dans un dossier
     * si il y a d'autre dossier dans le dossier il rappelle la méthode
     * @param dossier
     */
    public void creerContenueDossier(Dossier dossier) {
        ArrayList<Fichier> listeFichiers = dossier.getListeFichiers();
        for (Fichier fichier : listeFichiers) {
            if (fichier instanceof Classe) {
                this.creerVisuelClasse((Classe) fichier);
            } else if (fichier instanceof Dossier) {
                creerContenueDossier((Dossier) fichier);
            }
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
        vueClasse.setLayoutX(startX);
        vueClasse.setLayoutY(startY);
        System.out.println("la width est de : "+vueClasse.getWidth());
        System.out.println("la height est de : "+vueClasse.getHeight());
        this.startX = this.startX + 310;
        if (this.startX > this.getWidth()) {
            this.startX = 0;
            this.startY = this.startY + 210;
        }
    }
}