package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Controleur.Observateur;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Fichier;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VueClasse extends VBox implements Observateur {


    private Classe classe;
    private VBox Attributs = new VBox();
    private VBox Methodes = new VBox();

    public VueClasse(Classe c) {
        super();
        this.prefWidth(100);
        System.out.println("VueClasse");
        this.classe = c;
    }

    @Override
    public void actualiser(Sujet s) {
        Label title = new Label(classe.getNom());
        this.getChildren().add(title);
        for (CompositionClasse compo : this.classe.getCompositionClasses()) {
            if (compo instanceof com.example.projet.CompositionClasse.Attributs) {
                String attribut = compo.toString();
                Label l = new Label(attribut);
                this.Attributs.getChildren().add(l);
            } else if (compo instanceof com.example.projet.CompositionClasse.Methodes) {
                String methode = compo.toString();
                Label l = new Label(methode);
                this.Methodes.getChildren().add(l);
            }
        }
    }

}
