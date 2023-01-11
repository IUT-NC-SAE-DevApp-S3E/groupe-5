package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ControleurBoutonEnregistrer implements EventHandler<ActionEvent> {

    private Sujet sujet;

    public ControleurBoutonEnregistrer(Sujet s) {
        this.sujet = s;
    }

    /**
     * methode handle qui permet de gerer le bouton enregistrer
     *
     * @param actionEvent l'evenement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Sérialise le modèle
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le diagramme");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Diagramme UML", "*.diagramme"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Capture d'écran PNG", "*.png"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG PlantUML", "*.png"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Source plantUML", "*.txt"));
            fileChooser.setInitialFileName("diagramme");
            fileChooser.setInitialDirectory((new File(sujet.getCheminArborescence())));
            File file = fileChooser.showSaveDialog(null);
            FileOutputStream fileOut = null;

            if (file != null) {
                switch (fileChooser.getSelectedExtensionFilter().getDescription()) {
                    case "Diagramme UML":
                        fileOut = new FileOutputStream(file.getAbsoluteFile());
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(this.sujet.getListeFichiers());
                        out.close();
                        fileOut.close();
                        System.out.printf("Serialized data is saved");
                        break;

                    case "Capture d'écran PNG":
                        this.sujet.capturerPane(file);
                        break;

                    case "PNG PlantUML":
                        fileOut = new FileOutputStream(file.getAbsolutePath());

                        SourceStringReader reader = new SourceStringReader(this.sujet.genererPlantUML().toString());
                        String desc = reader.outputImage(fileOut).getDescription();
                        System.out.println(desc);
                        // Return a null string if no generation
                        break;

                    case "Source plantUML":
                        StringBuilder res = this.sujet.genererPlantUML();
                        fileOut = new FileOutputStream(file.getAbsoluteFile());
                        fileOut.write(res.toString().getBytes());
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

