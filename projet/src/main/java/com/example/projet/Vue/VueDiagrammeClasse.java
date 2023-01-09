package com.example.projet.Vue;

import com.example.projet.Controleur.ControleurCliqueDroitClasse;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Fleches.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

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
    HashMap<VueClasse, VueClasse> listeAssociationInterfaces = new HashMap<>();
    HashMap<VueClasse, VueClasse> listeAssociationDependances = new HashMap<>();


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
            // on affiche vide si les listes sont vides
            if (this.listeAssociationSuperClasse.isEmpty() && this.listeAssociationInterfaces.isEmpty()) {
                System.out.println("videvidevide");
            }
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
                this.makeImplementsList();
                this.makeSuperClassListe();
            }

            this.supprimerFleches();
            this.drawSuperClasse();
            this.drawImplementations();
            this.placerVue();
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
                        this.listeAssociationInterfaces.put(classe, vueClasseInterface);
                        vueClasseInterface.getClasse().getMoyValue().ajouterFilsImplements();
                    }
                }
            }
        }
    }


    public void drawImplementations() {
        // Pour chaque association on dessine une ligne
        for (VueClasse vueClasse : this.listeAssociationInterfaces.keySet()) {
            int coordArriveeX = (int) this.listeAssociationInterfaces.get(vueClasse).getCoordX() + 125;
            int coordArriveeY = (int) this.listeAssociationInterfaces.get(vueClasse).getCoordY() + (int) this.listeAssociationInterfaces.get(vueClasse).getHeight();
            int coordDepartX = (int) vueClasse.getCoordX() + 125;
            int coordDepartY = (int) vueClasse.getCoordY();
            VueFleche vueFleche = new VueFleche(coordDepartX, coordDepartY, coordArriveeX, coordArriveeY, 2);
            this.pane.getChildren().addAll(vueFleche);
            this.listeFleches.add(vueFleche);
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
                    System.out.println("association entre " + this.listeVueClasse.get(i).getClasse().getNom() + " et " + this.listeVueClasse.get(j).getClasse().getNom());
                    this.listeVueClasse.get(j).getClasse().getMoyValue().ajouterFilsSuper();
                    System.out.println(this.listeVueClasse.get(j).getClasse().getNom()+" a "+this.listeVueClasse.get(j).getClasse().getMoyValue().getValue()+" fils");
                } else {
                    //System.out.println(this.listeVueClasse.get(j).getClasse().getNom() + " =/= " + nomSuperClasse);
                }
            }
        }
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
            int coordArriveeX = (int) this.listeAssociationSuperClasse.get(vueClasse).getCoordX() + 125;
            int coordArriveeY = (int) this.listeAssociationSuperClasse.get(vueClasse).getCoordY() + (int) this.listeAssociationSuperClasse.get(vueClasse).getHeight();
            int coordDepartX = (int) vueClasse.getCoordX() + 125;
            int coordDepartY = (int) vueClasse.getCoordY();
            System.out.println("coord class "+vueClasse.getClasse().getNom()+" : "+coordDepartX + " coord class Y : "+ coordDepartY);
            VueFleche fleche = new VueFleche(coordDepartX, coordDepartY, coordArriveeX, coordArriveeY, 1);
            this.pane.getChildren().add(fleche);
            this.listeFleches.add(fleche);
        }
        this.fait = true;
    }


    /**
     * méthode
     * supprimer les fleches
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
            System.out.println("value classe "+ vueClasse.getClasse().getNom()+" : "+value);
            // on arrondi value
            int valueArrondi = (int) Math.round(value);
            System.out.println("value arrondi : "+valueArrondi);
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
                double hauteur = this.hauteurLigne.get(i).get(j).getHauteur();
                if ( hauteur > plusGrand) {
                    plusGrand = (int) this.hauteurLigne.get(i).get(j).getHauteur();
                }
            }
            this.startX = 25;
            this.startY += plusGrand + 10;
        }

    }

}