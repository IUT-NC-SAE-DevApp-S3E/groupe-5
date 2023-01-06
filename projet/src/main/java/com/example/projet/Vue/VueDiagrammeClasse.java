package com.example.projet.Vue;

import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Fleches.DecorateurFleche;
import com.example.projet.Vue.Fleches.FinFlecheVide;
import com.example.projet.Vue.Fleches.VueFleche;
import com.example.projet.Vue.Fleches.DecorateurFinFleche;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;


import java.util.ArrayList;
import java.util.HashMap;

public class VueDiagrammeClasse extends ScrollPane implements Observateur {


    private Pane pane = new Pane();

    private final int DECALAGEX = 25;
    private final int DECALAGEY = 25;
    private int startX = 25;
    private int startY = 25;

    private ArrayList<VueClasse> listeVueClasse = new ArrayList<>();

    // HashMap qui comme clé prend une VueClasse et comme valeur un ArrayList de VueClasse
    HashMap<VueClasse, VueClasse> listeAssociationSuperClasse = new HashMap<>();

    // liste des flèches
    private HashMap<DecorateurFleche, DecorateurFinFleche> listeFleches = new HashMap<>();




    public VueDiagrammeClasse() {
        super();
        this.setContent(this.pane);
        this.setPrefSize(1000, 700);
    }

    @Override
    public void actualiser(Sujet s) {
        /**
         * Si il ne faut pas clear le diagramme de classe
         * on ajoute le visuel des classe qui sont dans le modèle
         */
        if (!s.getClear()) {
            //this.listeVueClasse.clear();
            // on récupère la liste des fichiers du modèle
            ArrayList<Classe> fichiers = s.getListeFichiers();
            // si le nombre d'éléments dans la liste est supérieur au nombre de class dans le visuel
            if (this.pane.getChildren().size() < fichiers.size()) {
                for (int i = this.pane.getChildren().size(); i < fichiers.size(); i++) {
                    if (fichiers.get(i) instanceof Classe) {
                        Classe c = (Classe) fichiers.get(i);
                        this.creerVisuelClasse(c, s);
                    }
                }
            }
            this.drawSuperClasse();
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
            this.startX = DECALAGEX;
            this.startY = DECALAGEY;
        }

    }

    /**
     * méthode creerVisuelClasse qui va ajouter une classe au pane
     * et faire en sorte que les classes ne se superposent pas
     * @param classe
     * @param s
     */
    public void creerVisuelClasse(Classe classe, Sujet s) {
        VueClasse vueClasse = new VueClasse(classe, s);
        vueClasse.setOnMouseClicked(new ControleurCliqueDroitClasse(s, this.pane, vueClasse));
        // on ajoute la VueClasse a la liste
        this.listeVueClasse.add(vueClasse);

        this.pane.getChildren().add(vueClasse);
        vueClasse.setLayoutX(this.startX);
        vueClasse.setLayoutY(this.startY);
        this.startY += 210;
        if (this.startY > 1000) {
            this.startY = DECALAGEY;
            this.startX += 300;
        }
    }

    /**
     * méthode drawSuperClasse
     * qui parcour la liste des classes et va trouver les super classe de chaque classe
     * dessiner une ligne entre les classes
     */
    public void drawSuperClasse() {
        // on supprime les flèches
        this.pane.getChildren().removeAll(this.listeFleches.keySet());
        this.pane.getChildren().removeAll(this.listeFleches.values());
        // on clear la liste des associations
        this.listeAssociationSuperClasse.clear();
        for (int i = 0; i < this.listeVueClasse.size() -1; i++) {
            boolean trouver = false;
            String nomSuperClasse = this.listeVueClasse.get(i).getClasse().getSuperClasse();
            for (int j = i+1; j < this.listeVueClasse.size() && !trouver; j++) {
                String nomClasseCourante = this.listeVueClasse.get(j).getClasse().getNom();
                if (nomClasseCourante.equals(nomSuperClasse)) {
                    trouver = true;
                    this.listeAssociationSuperClasse.put(this.listeVueClasse.get(i), this.listeVueClasse.get(j));
                    System.out.println("trouver : "+ this.listeVueClasse.get(i).getClasse().getNom() + " " + this.listeVueClasse.get(j).getClasse().getNom());
                } else {
                    //System.out.println(this.listeVueClasse.get(j).getClasse().getNom() + " =/= " + nomSuperClasse);
                }
            }
        }

        // Pour chaque association on dessine une ligne
        for (VueClasse vueClasse : this.listeAssociationSuperClasse.keySet()) {
            int coordArriveeX = (int) this.listeAssociationSuperClasse.get(vueClasse).getLayoutX()+125;
            int coordArriveeY = (int) this.listeAssociationSuperClasse.get(vueClasse).getLayoutY()+(int) this.listeAssociationSuperClasse.get(vueClasse).getHeight();
            System.out.println("coordArriveeX : " + coordArriveeX + " coordArriveeY : " + coordArriveeY);
            int coordDepartX = (int) vueClasse.getLayoutX() + 125;
            int coordDepartY = (int) vueClasse.getLayoutY() + (int) vueClasse.getHeight();
            System.out.println("coordDepartX : " + coordDepartX + " coordDepartY : " + coordDepartY);
            VueFleche fleche = new VueFleche(coordDepartX, coordDepartY, coordArriveeX, coordArriveeY);
            FinFlecheVide finFlecheVide = new FinFlecheVide(coordDepartX, coordDepartY, coordArriveeX, coordArriveeY);
            this.pane.getChildren().addAll(fleche, finFlecheVide);
            this.listeFleches.put(fleche, finFlecheVide);
        }
    }

}