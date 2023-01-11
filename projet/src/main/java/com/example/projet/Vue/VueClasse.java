
package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Constructeur;
import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private Pane pane;
    private TextField title = new TextField();
    private TextField packageClasse = new TextField("package");
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
    private int width = 200;
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
            try {
                this.sujet.notifierObservateur();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
            }
        });
        Drag.getChildren().add(button);
        // on centre verticalement le button
        Drag.setAlignment(button, Pos.TOP_RIGHT);
        Drag.setPadding(new javafx.geometry.Insets(2, 2, 2, 2));


        // on dit qu'on peut éditer le titre de la classe
        this.title.setEditable(true);
        // on ajoute les éléments a la vue

        this.title.setOnAction(actionEvent -> {
            this.classe.setNom(this.title.getText());
        });


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
            this.classe.afficher();
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
        this.attributs.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: grey;-fx-min-height: 8px;");
        this.methodes.setStyle("-fx-min-height: 8px;");
        // on met le background en noir
        this.setStyle("-fx-background-color: #fcea4a;-fx-border-color: grey;-fx-border-radius: 10;");
        // on met la taille du titre à 20
        this.title.setPrefHeight(10);
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-background-color: none;");
        // ajout du package
        this.packageClasse.setAlignment(Pos.CENTER);
        this.packageClasse.setStyle("-fx-background-color: none;-fx-border-width: 0 0 1 0; -fx-border-color: grey;");
        // Stylisation --------------------------------


        String nomClasse = (this.classe.getNom()).split("\\.")[0];
        if (!this.classe.getType().equals("class")) {
            nomClasse = "<<" + this.classe.getType() + ">> " + nomClasse;
        }
        this.title.setText(nomClasse);
        this.packageClasse.setText(this.classe.getPackageClasse());
        // on met une bordure de 1px au botton du package
        // on met le titre en gras
        this.title.setFont(javafx.scene.text.Font.font("System", 20));
        for (CompositionClasse c : this.classe.getCompositionClasses()) {
            if (c instanceof Attributs) {
                VueAttribut newAttribut = new VueAttribut(this.attributs, this.classe);
                newAttribut.setNom(c.toString());
                if (!c.toString().contains("$")) {
                    this.attributs.getChildren().add(newAttribut);
                }
            } else if (c instanceof Methodes || c instanceof Constructeur) {
                VueMethode newMethode = new VueMethode(this.methodes, this.classe);
                newMethode.setNom(c.toString());
                // permet de ne pas afficher la methode si ce n'est pas une méthode écrite dans la classe
                if (!c.toString().contains("$")) {
                    this.methodes.getChildren().add(newMethode);
                }
            }
        }

        this.getChildren().addAll(Drag, title);

        if (!this.packageClasse.getText().equals("")) {
            this.getChildren().add(packageClasse);
        }


        if (!this.visible) {
            this.getChildren().addAll(attributs, methodes);
        }
    }

    @Override
    public void actualiser(Sujet s) {
        /**
         * on enlève tout pour pas qu'il n'y ai pas de problème de position
         * si on supprime et remet juste les VBox
         * il se peut que les Attributs soient en dessous des méthodes
         * mais en supprimant tout avant ce n'est plus le cas
         */
        this.getChildren().remove(this.attributs);
        this.getChildren().remove(this.methodes);
        this.getChildren().remove(this.packageClasse);

        if (!s.getTypeMasque("P") && this.visible) {
            this.getChildren().add(this.packageClasse);
        }
        if (!s.getTypeMasque("A") && this.visible) {
            this.getChildren().add(this.attributs);
        }
        if (!s.getTypeMasque("M") && this.visible) {
            this.getChildren().add(this.methodes);
        }
    }

    /**
     * getter de visuel, getVisuel
     *
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
    public void ajouterAttribut(VueAttribut attribut) {
        this.attributs.getChildren().add(attribut);
        // on ajoute l'attribut a la classe
        this.classe.ajouterCompositionClasse(new Attributs("public", attribut.getTextField().getText(), "None", "normal"));
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

    public int getLargeur() {
        return this.width;
    }

    public int getHauteur() {
        double hauteur = 63.3;
        if (!this.sujet.getTypeMasque("P") && this.visible) {
            hauteur += 26.4;
        }
        if (!this.sujet.getTypeMasque("A") && this.visible) {
            for (int i = 0; i < this.attributs.getChildren().size(); i++) {
                hauteur += 17.6;
            }
            hauteur += 0.8;
        }
        hauteur += 1.6;
        if (!this.sujet.getTypeMasque("M") && this.visible) {
            for (int i = 0; i < this.methodes.getChildren().size(); i++) {
                hauteur += 17.6;
            }
            hauteur += 0.8;
        }
        // on renvoie une valeur arrondie à l'entier supérieur
        return (int) Math.ceil(hauteur);
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

                    VueAttribut newAttribut = new VueAttribut(this.attributs, c);
                    this.ajouterAttribut(newAttribut);
                    newAttribut.setNom("+ "+c.getNom() + " "+ c.getNom().toLowerCase());
                    try {
                        this.sujet.notifierObservateur();
                    } catch (Exception e) {
                        // TODO
                    }

                    // on ajoute le TextField dans la vue
                });
            }
        }
        return menu;
    }

    /**
     * méthode afficherMenuHeritage
     */
    public ScrollPane afficherMenuHeritage() {
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
                    // on ajoute un heritage dans la class
                    this.classe.setSuperClasse(bouton.getText());
                });
            }
        }
        return menu;
    }

    /**
     * méthode afficherMenuImplementation
     */
    public ScrollPane afficherMenuImplementation() {
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
                    // on ajoute un heritage dans la class
                    this.classe.ajouterInterface(bouton.getText());
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
            this.getChildren().addAll(this.attributs, this.methodes, this.packageClasse);
            // on met l'icon de l'oeil
            this.boutonVisible.setText("\uf06e");
        } else {
            this.getChildren().removeAll(this.attributs, this.methodes, this.packageClasse);
            this.boutonVisible.setText("\uf070");
        }
    }


}