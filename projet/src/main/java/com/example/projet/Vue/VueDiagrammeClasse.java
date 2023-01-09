package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Fleches.*;
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
    private Sujet sujet;

    private ArrayList<VueClasse> listeVueClasse = new ArrayList<>();

    // HashMap qui comme clé prend une VueClasse et comme valeur un ArrayList de VueClasse
    HashMap<VueClasse, VueClasse> listeAssociationSuperClasse = new HashMap<>();
    HashMap<VueClasse, ArrayList<VueClasse>> listeAssociationInterfaces = new HashMap<>();
    HashMap<VueClasse, ArrayList<VueClasse>> listeAssociationDependances = new HashMap<>();


    // liste des flèches
    private ArrayList<VueFleche> listeFleches = new ArrayList<>();

    private boolean fait = false;



    public VueDiagrammeClasse(Sujet sujet) {
        super();
        this.setContent(this.pane);
        this.setPrefSize(1000, 700);
        this.sujet = sujet;
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
                        Classe c = fichiers.get(i);
                        this.creerVisuelClasse(c, s);
                    }
                }
            }
            this.supprimerFleches();
            this.makeSuperClassListe();
            this.makeImplementsList();
            this.makeDependanceList();
            //this.makeImplementsList();
            //this.drawImplementations();
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

            this.listeAssociationSuperClasse.clear();
            this.listeAssociationInterfaces.clear();
            this.listeAssociationDependances.clear();
            this.listeFleches.clear();
            this.listeVueClasse.clear();
        }

    }

    /**
     * méthode creerVisuelClasse qui va ajouter une classe au pane
     * et faire en sorte que les classes ne se superposent pas
     *
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
     * méthode qui va créer la liste des associations entre les classes
     */
    public void makeImplementsList() {
        this.listeAssociationInterfaces.clear();
        for (VueClasse vueClasseInterface : this.listeVueClasse) {
            for (VueClasse classe : this.listeVueClasse) {
                for (String nomInterface : classe.getClasse().getInterfaces()) {
                    if (nomInterface.equals(vueClasseInterface.getClasse().getNom())) {
                        ArrayList<VueClasse> temp = this.listeAssociationInterfaces.get(classe);
                        if (temp == null) {
                            temp = new ArrayList<>();
                        }
                        temp.add(vueClasseInterface);
                        this.listeAssociationInterfaces.put(classe, temp);
                    }
                }
            }
        }
        drawImplementations();
    }


    public void drawImplementations() {
        // Pour chaque association on dessine une ligne
        System.out.println("fleches : " + this.listeFleches.size());
        for (VueClasse vueClasse : this.listeAssociationInterfaces.keySet()) {
            for (VueClasse vueClasseInterface : this.listeAssociationInterfaces.get(vueClasse)) {
                int[] coord = this.getCoord(vueClasse, vueClasseInterface);
                VueFleche vueFleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 2);
                this.listeFleches.add(vueFleche);
                this.pane.getChildren().add(vueFleche);
            }
        }
        this.fait = true;
    }


    /**
     * méthode qui permet de faire la liste des associations entre les classes
     */
    public void makeSuperClassListe() {
        this.listeAssociationSuperClasse.clear();
        for (int i = 0; i < this.listeVueClasse.size() - 1; i++) {
            boolean trouver = false;
            String nomSuperClasse = this.listeVueClasse.get(i).getClasse().getSuperClasse();
            for (int j = i + 1; j < this.listeVueClasse.size() && !trouver; j++) {
                String nomClasseCourante = this.listeVueClasse.get(j).getClasse().getNom();
                if (nomClasseCourante.equals(nomSuperClasse)) {
                    trouver = true;
                    this.listeAssociationSuperClasse.put(this.listeVueClasse.get(i), this.listeVueClasse.get(j));
                }
            }
        }
        drawSuperClasse();
    }

    /**
     * méthode afficher superClasse
     * qui va afficher les super classe de chaque classe
     */
    public void afficherSuperClasse() {
        // on affiche le nombre de cle
        for (VueClasse vueClasse : this.listeAssociationSuperClasse.keySet()) {
            System.out.println(vueClasse.getClasse().getNom() + " -> " + this.listeAssociationSuperClasse.get(vueClasse).getClasse().getNom());
        }
        // si la liste est vide on ecrit aucun
        if (this.listeAssociationSuperClasse.isEmpty()) {
            System.out.println("Aucun");
        }
    }

    /**
     * méthode drawSuperClasse
     * qui parcour la liste des classes et va trouver les super classe de chaque classe
     * dessiner une ligne entre les classes
     */
    public void drawSuperClasse() {
        // Pour chaque association on dessine une ligne
        for (VueClasse vueClasse : this.listeAssociationSuperClasse.keySet()) {
            int coord[] = getCoord(vueClasse, this.listeAssociationSuperClasse.get(vueClasse));
            VueFleche fleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 1);
            this.pane.getChildren().add(fleche);
            this.listeFleches.add(fleche);
        }
        this.fait = true;
    }

    public void makeDependanceList() {
        this.listeAssociationDependances.clear();
        for (VueClasse vueClasseDependance : this.listeVueClasse) {
            for (VueClasse classe : this.listeVueClasse) {
                for (CompositionClasse nomDependance : classe.getClasse().getCompositionClasses())
                {
                    if(nomDependance instanceof Attributs) {
                        String type = nomDependance.getType();
                        if (type.equals(vueClasseDependance.getClasse().getNom())) {
                            ArrayList<VueClasse> temp = this.listeAssociationDependances.get(classe);
                            if (temp == null) {
                                temp = new ArrayList<>();
                            }
                            temp.add(vueClasseDependance);
                            this.listeAssociationDependances.put(classe, temp);
                        }
                    }
                }
            }
        }
        for (VueClasse liste : this.listeAssociationDependances.keySet()) {
            System.out.println(liste.getClasse().getNom() + " -> ");
            for (VueClasse vueClasse : this.listeAssociationDependances.get(liste)) {
                System.out.println(vueClasse.getClasse().getNom());
            }
        }
        drawDependance();
    }

    public void drawDependance()
    {
        System.out.println("taille association dependance : " + this.listeAssociationDependances.size());
        for (VueClasse vueClasse : this.listeAssociationDependances.keySet()) {
            for (VueClasse vueClasseDependance : this.listeAssociationDependances.get(vueClasse)) {
                int[] coord = this.getCoord(vueClasse, vueClasseDependance);
                VueFleche vueFleche = new VueFleche(coord[2], coord[3], coord[0], coord[1], 3);
                this.listeFleches.add(vueFleche);
                this.pane.getChildren().add(vueFleche);
            }
        }
        this.fait = true;
    }

    /**
     * Méthode permettant de récupérer les coordonnées d'une classe.
     * @param vueClasseDepart
     * @param vueClasseArrive
     * @return
     */
    public int[] getCoord(VueClasse vueClasseDepart, VueClasse vueClasseArrive) {
        int[] coord = new int[4];
        coord[0] = vueClasseDepart.getCoordX() + 125;
        coord[1] = vueClasseDepart.getCoordY();
        coord[2] = vueClasseArrive.getCoordX() + 125;
        coord[3] = vueClasseArrive.getCoordY() + (int) vueClasseArrive.getHeight();
        return coord;
    }


    /**
     * méthode supprimer les fleches
     * qui va supprimer les fleches
     */
    public void supprimerFleches() {
        for (VueFleche fleche : this.listeFleches) {
            this.pane.getChildren().remove(fleche);
        }
        this.listeFleches.clear();
    }

}