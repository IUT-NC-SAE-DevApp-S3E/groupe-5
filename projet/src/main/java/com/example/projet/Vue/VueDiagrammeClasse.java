package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Fleches.*;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * classe VueDiagrammeClasse qui permet l'affichage des diagrammes de classe que nous générons avec l'application
 */
public class VueDiagrammeClasse extends ScrollPane implements Observateur {

    private Pane pane = new Pane();

    private final int DECALAGEX = 25;
    private final int DECALAGEY = 25;

    private int startX = 25;
    private int startY = 25;
    private HashMap<Integer, ArrayList<VueClasse>> hauteurLigne = new HashMap<>();
    private Sujet sujet;

    private ArrayList<VueClasse> listeVueClasse = new ArrayList<>();

    // HashMap qui comme clé prend une VueClasse et comme valeur un ArrayList de VueClasse
    HashMap<VueClasse, VueClasse> listeAssociationSuperClasse = new HashMap<>();
    HashMap<VueClasse, ArrayList<VueClasse>> listeAssociationInterfaces = new HashMap<>();
    HashMap<VueClasse, ArrayList<VueClasse>> listeAssociationDependances = new HashMap<>();


    // liste des flèches
    private ArrayList<VueFleche> listeFleches = new ArrayList<>();

    private boolean fait = false;

    /**
     * constructeur de la classe VueDiagrammeClasse
     *
     * @param sujet le modele
     */
    public VueDiagrammeClasse(Sujet sujet) {
        super();
        this.setContent(this.pane);
        this.setPrefSize(1000, 700);
        this.sujet = sujet;
    }

    /**
     * methode actualiser qui permet d'actualiser l'interface graphique
     *
     * @param s le sujet
     */
    @Override
    public void actualiser(Sujet s) {
        /*
         * S'il ne faut pas clear le diagramme de classe,
         * on ajoute le visuel de la classe qui sont dans le modèle
         */
        if (!s.getClear()) {

            //this.listeVueClasse.clear();
            // on récupère la liste des fichiers du modèle
            ArrayList<Classe> fichiers = s.getListeFichiers();
            // si le nombre d'éléments dans la liste est supérieur au nombre de class dans le visuel
            if (this.pane.getChildren().size() < fichiers.size() || !this.fait) {
                for (int i = this.pane.getChildren().size(); i < fichiers.size(); i++) {
                    if (fichiers.get(i) instanceof Classe) {
                        Classe c = fichiers.get(i);
                        this.creerVisuelClasse(c, s);
                    }

                }
            }
            //this.placerVue();
            this.supprimerFleches();
            this.drawSuperClasse();
            this.drawImplementations();
            this.makeSuperClassListe();
            this.makeImplementsList();
            this.makeDependanceList();
        } else {
            // on clear le visuel
            this.pane.getChildren().clear();
            // on clear le contenu de la liste du modèle
            s.clearFichier();
            // on dit que le contenu n'est plus à clear
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
        // on ajoute la VueClasse à la liste
        this.listeVueClasse.add(vueClasse);
        this.pane.getChildren().add(vueClasse);
        vueClasse.setLayoutX(this.startX);
        vueClasse.setLayoutY(this.startY);
        // on récupère la taille de la vueClasse
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
                        vueClasseInterface.getClasse().getMoyValue().ajouterFilsImplements();
                    }
                }
            }
        }
        drawImplementations();
    }

    /**
     * methode drawImplementations qui permet de dessiner les implémentations
     */
    public void drawImplementations() {
        // Pour chaque association on dessine une ligne
        System.out.println("fleches : " + this.listeFleches.size());
        for (VueClasse vueClasse : this.listeAssociationInterfaces.keySet()) {
            for (VueClasse vueClasseInterface : this.listeAssociationInterfaces.get(vueClasse)) {
                int[] coord = this.getCoord(vueClasse, vueClasseInterface);
                VueFleche vueFleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 2);
                this.listeFleches.add(vueFleche);
                this.pane.getChildren().add(vueFleche);
                vueFleche.toBack();
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
                    System.out.println("association entre " + this.listeVueClasse.get(i).getClasse().getNom() + " et " + this.listeVueClasse.get(j).getClasse().getNom());
                    this.listeVueClasse.get(j).getClasse().getMoyValue().ajouterFilsSuper();
                    System.out.println(this.listeVueClasse.get(j).getClasse().getNom() + " a " + this.listeVueClasse.get(j).getClasse().getMoyValue().getValue() + " fils");
                } else {
                    //System.out.println(this.listeVueClasse.get(j).getClasse().getNom() + " =/= " + nomSuperClasse);
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
        // si la liste est vide, on écrit "aucun"
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
        // Pour chaque association, on dessine une ligne
        for (VueClasse vueClasse : this.listeAssociationSuperClasse.keySet()) {
            int coord[] = getCoord(vueClasse, this.listeAssociationSuperClasse.get(vueClasse));
            VueFleche fleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 1);
            this.pane.getChildren().add(fleche);
            fleche.toBack();
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
                vueFleche.toBack();
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


    /**
     * méthode placerVue
     */
    public void placerVue() {
        for (int i = 0; i < 5; i++) {
            this.hauteurLigne.put(i, new ArrayList<>());
        }
        for (VueClasse vueClasse : this.listeVueClasse) {
            double value = vueClasse.getClasse().getMoyValue().getValue();
            System.out.println("value classe " + vueClasse.getClasse().getNom() + " : " + value);
            // on arrondi value
            int valueArrondi = (int) Math.round(value);
            System.out.println("value arrondi : " + valueArrondi);
            switch (valueArrondi) {
                case 0:
                    this.hauteurLigne.get(0).add(vueClasse);
                    break;
                case 1:
                    this.hauteurLigne.get(1).add(vueClasse);
                    break;
                case 2:
                    this.hauteurLigne.get(2).add(vueClasse);
                    break;
                case 3:
                    this.hauteurLigne.get(3).add(vueClasse);
                    break;
                default:
                    this.hauteurLigne.get(4).add(vueClasse);
                    break;
            }

        }


        this.startX = 25;
        this.startY = 25;

        for (int i = 4; i >= 0; i--) {
            int plusGrand = 0;
            for (int j = 0; j < this.hauteurLigne.get(i).size(); j++) {
                if (this.startX > 1000) {
                    this.startX = 25;
                    this.startY += plusGrand;
                    plusGrand = 0;
                }
                this.hauteurLigne.get(i).get(j).setLayoutX(this.startX);
                this.hauteurLigne.get(i).get(j).setLayoutY(this.startY);
                // this.startX += this.hauteurLigne.get(i).get(j).getWidth() + 25;
                this.startX += 275;
                // on récupère la largeur de la Vue
                Bounds bounds = this.hauteurLigne.get(i).get(j).getLayoutBounds();
                double hauteur = bounds.getHeight();
                if (hauteur > plusGrand) {
                    plusGrand = (int) this.hauteurLigne.get(i).get(j).getHauteur();
                }
            }
            this.startX = 25;
            this.startY += plusGrand + 10;
        }

    }

}