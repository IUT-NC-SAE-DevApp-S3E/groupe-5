package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.TrouverCheminOS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControleurBoutonOpenFile implements EventHandler<ActionEvent> {

    private Sujet sujet;

    /**
     * Constructeur de la classe ControleurBoutonOpenFile
     * @param s
     */
    public ControleurBoutonOpenFile(Sujet s) {
        this.sujet = s;
    }

    /**
     * Méthode qui permet de gérer l'évènement du bouton OpenFile
     * @param event
     */
    public void handle(ActionEvent actionEvent) {
        Stage choixDeFichier = new Stage();
        choixDeFichier.setTitle("Choix de fichier");
        choixDeFichier.setHeight(600);
        choixDeFichier.setWidth(250);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.setInitialDirectory(new File(TrouverCheminOS.getChemin()));
        File file = fileChooser.showOpenDialog(choixDeFichier);

        Classe c = new Classe(file.getPath(), file.getName());

        this.sujet.ajouterFichier(c);
        try {
            this.sujet.notifierObservateur();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }
}
