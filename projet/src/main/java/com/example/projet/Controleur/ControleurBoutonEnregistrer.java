package com.example.projet.Controleur;

import com.example.projet.Modele.Modele;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Source plantUML", "*.txt"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Capture d'écran JPG", "*.jpg"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG PlantUML", "*.png"));
            fileChooser.setInitialFileName("diagramme");
            fileChooser.setInitialDirectory((new File(sujet.getCheminArborescence())));
            File file = fileChooser.showSaveDialog(null);

            if(file.getName().endsWith(".diagramme")) {
                FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this.sujet.getListeFichiers());
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved");
            } else if(file.getName().endsWith(".jpg")) {
                this.sujet.capturerPane(file);
            } else if(file.getName().endsWith(".png"))
            {

            } else if(file.getName().endsWith(".txt")) {
                ArrayList<Classe> listeClasses = this.sujet.getListeFichiers();
                String res = "@startuml\n";
                for(Classe c : listeClasses) {
                    res += c.toPlantUML() + "\n";
                }
                for(Classe c : listeClasses) {
                    String parent = c.depExtend();
                    // on ajoute la dépendance uniquement si la classe existe dans la liste des classes
                    for(Classe c2 : listeClasses) {
                        if(c2.getNom().equals(parent)) {
                            res += c.getNom() + " --|> " + parent + "\n";
                        }
                    }
                    ArrayList<String> dependances = c.depImplement();
                    for(String s : dependances) {
                        // on ajoute la dépendance uniquement si la classe existe dans la liste des classes
                        for(Classe c2 : listeClasses) {
                            if(c2.getNom().equals(s)) {
                                res += c.getNom() + " ..|> " + s + "\n";
                            }
                        }
                    }
                }

                res+="@enduml";
                FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
                fileOut.write(res.getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

