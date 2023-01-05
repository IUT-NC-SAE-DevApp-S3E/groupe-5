package com.example.projet.Vue;

import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Utilitaires.Fichier;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.HashMap;

public class VueDiagrammeClasse extends ScrollPane implements Observateur {


    private Pane pane = new Pane();
    private int startX = 0;
    private int startY = 0;
    private static HashMap<String, int[]> listeCoordonnees;
    private static HashMap<String, String> listeAssociation;
    private static HashMap<String, Line> listeFleche;


    public VueDiagrammeClasse() {
        super();
        this.setContent(this.pane);
        this.setPrefSize(1000, 700);
        this.listeCoordonnees = new HashMap<>();
        this.listeAssociation = new HashMap<>();
        this.listeFleche = new HashMap<>();
    }

    @Override
    public void actualiser(Sujet s) {
        /**
         * Si il ne faut pas clear le diagramme de classe
         * on ajoute le visuel des classe qui sont dans le modèle
         */
        if (!s.getClear()) {
            // on récupère la liste des fichiers du modèle
            ArrayList<Classe> fichiers = s.getListeFichiers();
            // si le nombre d'éléments dans la liste est supérieur au nombre de class dans le visuel
            if (this.pane.getChildren().size() < fichiers.size()) {
                for (int i = this.pane.getChildren().size(); i < fichiers.size(); i++) {
                    if (fichiers.get(i) instanceof Classe) {
                        Classe c = (Classe) fichiers.get(i);
                        this.creerVisuelClasse(c, s);


                        // ajoute les associations au attributs
                        if(!listeCoordonnees.containsKey(c.getNom())) {
                            String nom = c.getNom().split("\\.")[0];
                            listeCoordonnees.put(nom, new int[]{this.startX, this.startY});
                            String parent = c.getSuperClasse();
                            System.out.println("parent : " + parent);
                            if(parent != null) {
                                System.out.println("classe : " + nom + ", parent : " + parent);
                                listeAssociation.put(nom, parent);
                            }
                        }


                    }
                }
            }
            /**
             * si il faut clear le contenue du digramme de classe
             */
        } else {
            // on clear le visuel
            this.pane.getChildren().clear();
            // on clear le contenue de la liste du modèle
            s.clearFichier();
            // on dit que le contenue n'est plus à clear
            s.setClear(false);
            // on remet les coordonnées de départ à 0 et 0
            this.startX = 0;
            this.startY = 0;
        }

        // créer les flèches entre les classes
        for(String cle : listeAssociation.keySet()){
            System.out.println(cle + " " + listeAssociation.get(cle));
            if(listeCoordonnees.containsKey(listeAssociation.get(cle))){
                int[] pos = listeCoordonnees.get(cle);
                int[] posParent = listeCoordonnees.get(listeAssociation.get(cle));
                Line l = new Line(pos[0]+125, pos[1]-210, posParent[0]+125, posParent[1]-210);
                if(this.listeFleche.containsKey(cle)) {
                    this.listeFleche.get(cle).setStartX(pos[0]+125);
                    this.listeFleche.get(cle).setStartY(pos[1]-210);
                    this.listeFleche.get(cle).setEndX(posParent[0]+125);
                    this.listeFleche.get(cle).setEndY(posParent[1]-210);
                }
                this.listeFleche.put(cle, new Line(pos[0] + 125, pos[1] - 210, posParent[0] + 125, posParent[1] - 210));
                this.pane.getChildren().add(l);
            }
        }


    }

    /**
     * méthode creerVisuelClasse qui va ajouter une classe au pane
     * et faire en sorte que les classes ne se superposent pas
     *
     * @param classe
     */
    public void creerVisuelClasse(Classe classe, Sujet s) {
        VueClasse vueClasse = new VueClasse(classe, s);
        vueClasse.setOnMouseClicked(new ControleurCliqueDroitClasse(s, this.pane, vueClasse));

        this.pane.getChildren().add(vueClasse);
        vueClasse.setLayoutX(this.startX);
        vueClasse.setLayoutY(this.startY);
        this.startY += 210;
        if (this.startY > 1000) {
            this.startY = 0;
            this.startX += 300;
        }
    }

    public static void setListeCoordonnees(String nomClasse, int posX, int posY){
        VueDiagrammeClasse.listeCoordonnees.put(nomClasse, new int[]{posX, posY});
    }
}