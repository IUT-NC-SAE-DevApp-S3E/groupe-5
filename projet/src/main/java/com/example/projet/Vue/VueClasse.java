
package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Constructeur;
import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.MalformedURLException;

/**
 * classe VueClasse qui permet l'affichage d'une classe
 */
public class VueClasse extends VBox implements Observateur {

    /**
     * startX et startY sa position en X et Y
     * classe, la classe à afficher
     */
    private int startX = 0;
    private int startY = 0;
    private Classe classe;
    /**
     * attribut de l'affichage graphique
     */
    private StackPane drag;
    private TextField title = new TextField();
    private TextField packageClasse = new TextField();
    private VBox attributs = new VBox();
    private VBox methodes = new VBox();
    /**
     * sujet de la classe
     */
    private Sujet sujet;
    public boolean visible = true;
    public Button boutonVisible = new Button();

    private int coordX = 0;
    private int coordY = 0;
    private int width = 0;
    private int height = 0;

    /**
     * Constructeur de la classe VueClasse
     * une vueClasse représente une classe dans le diagramme de classe
     * une VueClasse est un VBox qui contient :
     * - un StackPane qui permet de déplacer (drag) la classe
     * - un TextField qui représente le nom de la classe
     * - un VBox qui représente les attributs de la classe
     * - un VBox qui représente les méthodes de la classe
     *
     * @param classe
     */
    public VueClasse(Classe classe, Sujet sujet) {
        super();
        this.sujet = sujet;
        this.classe = classe;

        // stackPane permet de déplacer la classe
        StackPane Drag = new StackPane();
        this.drag = Drag;

        // on crée le bouton qui permet d'afficher est de cacher la classe,
        // on crée un bouton avec l'icône oeil
        Button button = new Button("\uf06e");
        button.setFont(Font.loadFont("file:src/main/resources/Font/fontawesome-webfont.ttf", 10));
        button.setStyle("-fx-background-color: none");
        button.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
        this.boutonVisible = button;
        // on setOnAction l'appel de la méthode affichage

        this.boutonVisible.setOnAction(actionEvent -> {
            this.visible = !this.visible;
            this.affichage();
        });


        Drag.getChildren().add(button);
        // on met le button en haut à droite
        Drag.setAlignment(Pos.TOP_RIGHT);


        // on dit qu'on peut éditer le titre de la classe
        this.title.setEditable(true);
        // on ajoute les éléments a la vue
        System.out.println("Classe :" + classe.getNom() + " Package : " + packageClasse.getText());


        // stylisation --------------------------------
        Drag.setPrefSize(200, 20);
        Drag.setStyle("-fx-background-color: rgba(168,163,163,0.66);-fx-background-radius: 10 10 0 0;");

        switch (this.classe.getType().toLowerCase()) {
            case "interface":
                Drag.setStyle("-fx-background-color: rgba(39,98,7,0.66);-fx-background-radius: 10 10 0 0;");
                break;
            case "abstract":
                Drag.setStyle("-fx-background-color: rgba(58,61,232,0.66);-fx-background-radius: 10 10 0 0;");
                break;
        }
        // stylisation --------------------------------

        // on rend drag draggable
        Drag.setOnMousePressed((MouseEvent event) -> {
            startX = (int) event.getSceneX();
            startY = (int) event.getSceneY();
            // on passe l'element en premier plan
            this.toFront();
        });

        // on déplace la classe
        Drag.setOnMouseDragged((MouseEvent event) -> {
            int offsetX = (int) event.getSceneX() - startX;
            int offsetY = (int) event.getSceneY() - startY;
            this.setTranslateX(this.getTranslateX() + offsetX);
            startX = (int) event.getSceneX();
            this.setTranslateY(this.getTranslateY() + offsetY);
            startY = (int) event.getSceneY();
        });

        // lorsque le bouton de la souris est laché
        Drag.setOnMouseReleased(mouseEvent -> {
            try {
                Bounds boundsInScene = this.getBoundsInParent();
                this.coordX = (int) boundsInScene.getMinX();
                this.coordY = (int) boundsInScene.getMinY();
                this.sujet.notifierObservateur();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        // Stylisation --------------------------------
        // on met une bordure de 1 px au top et au bottom du VBox Attribut
        this.attributs.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: grey;-fx-min-height: 8px;");
        this.methodes.setStyle("-fx-min-height: 8px;");
        // on met le background en noir
        this.setStyle("-fx-background-color: #FCF8A7;-fx-border-color: grey;-fx-border-radius: 10;");
        // on met la taille du titre à 20
        this.title.setPrefHeight(10);
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-background-color: none;");
        // ajout du package
        this.packageClasse.setAlignment(Pos.CENTER);
        this.packageClasse.setStyle("-fx-background-color: none;");
        // Stylisation --------------------------------

        String nomClasse = (this.classe.getNom()).split("\\.")[0];
        if(!this.classe.getType().equals("class")){
            nomClasse = "<<" + this.classe.getType() + ">> " + nomClasse;
        }
        this.title.setText(nomClasse);
        this.packageClasse.setText(this.classe.getPackageClasse());
        // on met le titre en gras
        this.title.setFont(javafx.scene.text.Font.font("System", 20));
        for (CompositionClasse c : this.classe.getCompositionClasses()) {
            if (c instanceof Attributs) {
                VueAttribut newAttribut = new VueAttribut(this.attributs, this.classe);
                newAttribut.setNom(c.toString());
                if(!c.toString().contains("$")) {
                    this.attributs.getChildren().add(newAttribut);
                }
            } else if (c instanceof Methodes || c instanceof Constructeur) {
                VueMethode newMethode = new VueMethode(this.methodes, this.classe);
                newMethode.setNom(c.toString());
                // permet de ne pas afficher la methode si ce n'est pas une méthode écrite dans la classe
                if(!c.toString().contains("$")) {
                    this.methodes.getChildren().add(newMethode);
                }
            }
        }
        if(packageClasse.getText().equals(" ")){
            this.getChildren().addAll(Drag, title, attributs, methodes);
        }
        else {
            this.getChildren().addAll(Drag, title, packageClasse, attributs, methodes);
        }
    }

    @Override
    public void actualiser(Sujet s) {
        this.attributs.setVisible(!s.getTypeMasque("A"));
        this.methodes.setVisible(!s.getTypeMasque("M"));

    }

    /**
     * getter de visuel, getVisuel
     * @return this
     */
    public VueClasse getVisuel() {
        return this;
    }

    /**
     * getter des vbox
     */
    public VBox getAttributs() {
        return this.attributs;
    }

    public VBox getMethodes() {
        return this.methodes;
    }

    /**
     * méthode ajouterAttribut
     *
     * @param attribut ajoute un attribut a la classe
     */
    public void ajouterAttribut(HBox attribut) {
        this.attributs.getChildren().add(attribut);
    }

    /**
     * méthode ajouterMethode
     *
     * @param methode ajoute une methode a la classe
     */
    public void ajouterMethode(HBox methode) {
        this.methodes.getChildren().add(methode);
    }

    /**
     * méthode getClasse
     *
     * @return la classe
     */
    public Classe getClasse() {
        return this.classe;
    }

    public int getLargeur(){
        return 200;
    }

    public int getHauteur(){
        return 60 + (this.attributs.getChildren().size() *20) + (this.methodes.getChildren().size() * 20)+10;
    }

    /**
     * méthode getCoordX
     *
     * @return coordX int la coordonnée X de la classe
     */
    public int getCoordX() {
        Bounds boundsInScene = this.getBoundsInParent();
        this.coordX = (int) boundsInScene.getMinX();
        return this.coordX;
    }

    /**
     * méthode getCoordY
     *
     * @return coordY int la coordonnée Y de la classe
     */
    public int getCoordY() {
        Bounds boundsInScene = this.getBoundsInParent();
        this.coordY = (int) boundsInScene.getMinY();
        return this.coordY;
    }

    /**
     * méthode estAuDessus
     * cette méthode return true si une vue est au dessus d'une autre
     *
     * @param vc
     * @return boolean
     */
    public boolean estAuDessus(VueClasse vc) {
        return false;
    }

    /**
     * méthode afficherMenuDependance
     * qui affiche le menu contextuel pour ajouter une dépendance
     * lorsque l'on clique sur le bouton pour ajouter un dépendance
     * ce menu apparait
     */
    public ScrollPane afficherMenuDependance() {
        ScrollPane menu = new ScrollPane();
        // on initialise le VBox
        VBox contentScroll = new VBox();
        menu.setContent(contentScroll);
        // stylisation
        contentScroll.setPrefWidth(200);
        // on va mettre un bouton dans le VBox pour chaque classe dans le sujet
        for (Classe c : this.sujet.getListeFichiers()) {
            // on ne met pas la classe correspondent à la classe que l'on vient de cliquer
            if (!c.equals(this.classe)) {
                // on met comme titre le nom de la classe
                Button bouton = new Button(c.getNom());

                //Stylisation --------------------------------
                bouton.setStyle("-fx-background-color: #f3f3f3;");
                bouton.setPrefWidth(200);
                bouton.setPrefHeight(25);
                //Stylisation --------------------------------

                // on met un hover aux boutons pour qu'ils soient plus visibles lorsqu'on passe la souris dessus
                bouton.setOnMouseEntered(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #ababab;");
                });
                bouton.setOnMouseExited(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #f3f3f3;");
                });

                // on ajoute le bouton au menuContextuel
                contentScroll.getChildren().add(bouton);

                // pour chaque bouton, on ajoute un vehement qui va ajouter la dépendance à la classe correspondante
                bouton.setOnAction(actionEvent -> {
                    this.classe.ajouterCompositionClasse(new Attributs("private", c.getNom().toLowerCase(), c.getNom(), "none"));
                    // on créer un TextField pour l'attribut que l'on ajoute dans le diagramme de classe
                    TextField newAttribut = new TextField("- " + c.getNom().toLowerCase() + " " + c.getNom());

                    //Stylisation --------------------------------
                    newAttribut.setPrefHeight(5);
                    newAttribut.setStyle("-fx-background-color: none;");
                    newAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                    //Stylisation --------------------------------

                    // on ajoute le TextField dans la vue
                    this.attributs.getChildren().add(newAttribut);
                });
            }
        }
        return menu;
    }

    /**
     * méthode getDrag
     *
     * @return drag
     */
    public StackPane getDrag() {
        return this.drag;
    }

    /**
     * méthode affichage
     */
    public void affichage() {
        if (this.visible) {
            this.getChildren().addAll(this.attributs, this.methodes);
            // on met l'icon de l'oeil
            this.boutonVisible.setText("\uf06e");
        } else {
            this.getChildren().removeAll(this.attributs, this.methodes);
            this.boutonVisible.setText("\uf070");
        }
    }


}