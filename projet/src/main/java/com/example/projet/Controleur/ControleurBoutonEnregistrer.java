package com.example.projet.Controleur;

import com.example.projet.Modele.Modele;
import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ControleurBoutonEnregistrer implements EventHandler<ActionEvent> {

    private Sujet sujet;

    public ControleurBoutonEnregistrer(Sujet s)
    {
        this.sujet = s;
    }

    /**
     * methode handle qui permet de gerer le bouton enregistrer
     * @param actionEvent l'evenement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Sérialise le modèle
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le diagramme");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Diagramme UML", "*.diagramme"));
            fileChooser.setInitialFileName("diagramme");
            fileChooser.setInitialDirectory((new File(sujet.getCheminArborescence())));

            FileOutputStream fileOut = new FileOutputStream("/Users/arthur/Desktop/sauvegarde.diagramme");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.sujet.getListeFichiers());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

