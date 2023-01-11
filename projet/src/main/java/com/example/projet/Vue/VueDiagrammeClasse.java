package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Fleches.*;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    private boolean syncro = false;


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
            if (this.listeVueClasse.size() < fichiers.size()) {
                for (int i = this.listeVueClasse.size(); i < fichiers.size(); i++) {
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
            if (!this.sujet.getTypeMasque("D")) {
                this.makeDependanceList();
            }
            for (VueClasse v : this.listeVueClasse) {
                v.actualiser(this.sujet);
            }
            // s'il faut clear le contenu du digramme de classe
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

        // on fait en sorte de pouvoir zoomer dans le pane
        this.pane.setOnScroll(event -> {
            // si la touche controle est appuyer
            if (event.isControlDown()) {
                // on récupère la valeur du zoom
                double zoom = event.getDeltaY();
                // si la valeur du zoom est supérieur à 0

                if (event.getDeltaY() > 0) {
                    if (this.pane.getScaleX() < 2) {
                        this.pane.setScaleX(this.pane.getScaleX() + 0.1);
                        this.pane.setScaleY(this.pane.getScaleY() + 0.1);
                    }
                } else {
                    // si le scroll est vers le bas
                    // on dézoom
                    if (this.pane.getScaleX() > 0.5) {
                        this.pane.setScaleX(this.pane.getScaleX() - 0.1);
                        this.pane.setScaleY(this.pane.getScaleY() - 0.1);
                    }
                }

            }
        });

        if (!this.syncro) {
            this.syncro = true;
            this.actualiser(s);
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
        for (VueClasse vueClasse : this.listeAssociationInterfaces.keySet()) {
            for (VueClasse vueClasseInterface : this.listeAssociationInterfaces.get(vueClasse)) {
                int[] coord = this.getClosestCoord(vueClasse, vueClasseInterface);
                VueFleche vueFleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 2);
                this.listeFleches.add(vueFleche);
                this.pane.getChildren().add(vueFleche);
                vueFleche.toBack();
            }
        }
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
                    this.listeVueClasse.get(j).getClasse().getMoyValue().ajouterFilsSuper();
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
            int coord[] = getClosestCoord(vueClasse, this.listeAssociationSuperClasse.get(vueClasse));
            VueFleche fleche = new VueFleche(coord[0], coord[1], coord[2], coord[3], 1);
            this.pane.getChildren().add(fleche);
            fleche.toBack();
            this.listeFleches.add(fleche);
        }
    }

    /**
     * Cette méthode fauit la liste des Dependances
     * entre les VueCLasses
     * pour
     */
    public void makeDependanceList() {
        this.listeAssociationDependances.clear();
        for (VueClasse vueClasseDependance : this.listeVueClasse) {
            for (VueClasse classe : this.listeVueClasse) {
                for (CompositionClasse nomDependance : classe.getClasse().getCompositionClasses()) {
                    if (nomDependance instanceof Attributs) {
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
        drawDependance();
    }

    public void drawDependance() {
        for (VueClasse vueClasse : this.listeAssociationDependances.keySet()) {
            for (VueClasse vueClasseDependance : this.listeAssociationDependances.get(vueClasse)) {
                int[] coord = this.getClosestCoord(vueClasse, vueClasseDependance);
                VueFleche vueFleche = new VueFleche(coord[2], coord[3], coord[0], coord[1], 3);
                this.listeFleches.add(vueFleche);
                this.pane.getChildren().add(vueFleche);
                vueFleche.toBack();
            }
        }
    }

    /**
     * Méthode permettant de récupérer les coordonnées d'une classe.
     *
     * @param vueClasseDepart
     * @param vueClasseArrive
     * @return
     */
    public int[] getCoord(VueClasse vueClasseDepart, VueClasse vueClasseArrive) {
        int[] coord = new int[4];
        coord[0] = vueClasseDepart.getCoordX() + 125;
        coord[1] = vueClasseDepart.getCoordY();
        coord[2] = vueClasseArrive.getCoordX() + 125;
        coord[3] = vueClasseArrive.getCoordY() + (int) vueClasseArrive.getHauteur();
        return coord;
    }

    /**
     * méthode getClosestCoord
     * qui va calculer les deux points les plus proches pour chacune des deux VueClasse
     *
     * @param vueClasseDepart
     * @param vueClasseArrive
     */
    public int[] getClosestCoord(VueClasse vueClasseDepart, VueClasse vueClasseArrive) {
        int x1 = vueClasseDepart.getCoordX(), y1 = vueClasseDepart.getCoordY(), w1 = 250, h1 = vueClasseDepart.getHauteur();
        int x2 = vueClasseArrive.getCoordX(), y2 = vueClasseArrive.getCoordY(), w2 = 250, h2 = vueClasseArrive.getHauteur();
        int[][] points1 = {{x1 + w1 / 2, y1}, {x1 + w1, y1 + h1 / 2}, {x1 + w1 / 2, y1 + h1}, {x1, y1 + h1 / 2}};
        int[][] points2 = {{x2 + w2 / 2, y2}, {x2 + w2, y2 + h2 / 2}, {x2 + w2 / 2, y2 + h2}, {x2, y2 + h2 / 2}};
        double closestDistance = Double.POSITIVE_INFINITY;
        int[] closestPoints = new int[4];
        for (int[] p1 : points1) {
            for (int[] p2 : points2) {
                double distance = Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPoints[0] = p1[0];
                    closestPoints[1] = p1[1];
                    closestPoints[2] = p2[0];
                    closestPoints[3] = p2[1];
                }
            }
        }
        return closestPoints;
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
            int valueArrondi = (int) Math.round(value);
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