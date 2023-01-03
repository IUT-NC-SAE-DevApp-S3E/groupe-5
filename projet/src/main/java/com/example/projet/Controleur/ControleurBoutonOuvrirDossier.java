package com.example.projet.Controleur;

import com.example.projet.Utilitaires.TrouverCheminOS;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ControleurBoutonOuvrirDossier implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choisir un dossier");
            directoryChooser.setInitialDirectory(new File(TrouverCheminOS.getChemin()));

            Stage choixDeDossier = new Stage();
            choixDeDossier.setTitle("Choix de dossier");
            choixDeDossier.setHeight(600);
            choixDeDossier.setWidth(250);
            File f = directoryChooser.showDialog(choixDeDossier);
        }
}
