
package com.example.projet.Vue;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Methodes;
import com.example.projet.Modele.Modele;
import com.example.projet.Controleur.ControleurCliqueDroitElement;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;

public class VueClasse extends VBox implements Observateur {
    private int startX = 0;
    private int startY = 0;
    private Classe classe;
    private StackPane drag;
    private TextField title = new TextField();
    private VBox Attributs = new VBox();
    private VBox Methodes = new VBox();
    private Sujet sujet;

    private int coordX = 0;
    private int coordY = 0;
    private int width = 0;
    private int height = 0;

    /**
     * Constructeur de la classe VueClasse
     * une vueClasse représente une classe dans le diagramme de classe
     * une VueClasse est un VBox qui contiert:
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
        // on dit qu'on peut éditer le titre de la classe
        this.title.setEditable(true);
        // on ajoute les éléments a la vue
        this.getChildren().addAll(Drag, title, Attributs, Methodes);
        // stylisation --------------------------------
        this.prefWidth(200);
        this.prefHeight(400);
        // stylisation --------------------------------

        // stylisation --------------------------------
        Drag.setPrefSize(200, 20);
        Drag.setStyle("-fx-background-color: rgba(168,163,163,0.66);-fx-background-radius: 10 10 0 0;");

        switch (this.classe.getType().toLowerCase()) {
            case "interface":
                this.classe.setNom("<<Interface>> " + this.classe.getNom());
                Drag.setStyle("-fx-background-color: rgba(39,98,7,0.66);-fx-background-radius: 10 10 0 0;");
                break;
            case "abstract":
                this.classe.setNom("<<Abstract>> " + this.classe.getNom());
                Drag.setStyle("-fx-background-color: rgba(58,61,232,0.66);-fx-background-radius: 10 10 0 0;");
                break;
        }
        // stylisation --------------------------------

        // on rend drag draggable
        Drag.setOnMousePressed((MouseEvent event) -> {
            startX = (int) event.getSceneX();
            startY = (int) event.getSceneY();
        });

        // on déplace la classe
        Drag.setOnMouseDragged((MouseEvent event) -> {
            int offsetX = (int) event.getSceneX() - startX;
            int offsetY = (int) event.getSceneY() - startY;
            this.setTranslateX(this.getTranslateX() + offsetX);
            this.setTranslateY(this.getTranslateY() + offsetY);
            startX = (int) event.getSceneX();
            startY = (int) event.getSceneY();
        });

        Drag.setOnMouseReleased(mouseEvent -> {
            try {
                this.sujet.notifierObservateur();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        // Stylisation --------------------------------
        // on met une bordur de 1 px au top et au bottom du VBox Attribut
        this.Attributs.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: grey;-fx-min-height: 8px;");
        this.Methodes.setStyle("-fx-min-height: 8px;");
        // on met le background en noir
        this.setStyle("-fx-background-color: #FCF8A7;-fx-border-color: grey;-fx-border-radius: 10;");
        // on met la taille du titre a 20
        this.title.setPrefHeight(10);
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-background-color: none;");
        // Stylisation --------------------------------


        this.title.setText((this.classe.getNom()).split("\\.")[0]);
        // on met le titre en gras
        this.title.setFont(javafx.scene.text.Font.font("System", 20));
        for (CompositionClasse c : this.classe.getCompositionClasses()) {
            if (c instanceof Attributs) {
                /*
                TextField newAttribut = new TextField(c.toString());
                newAttribut.setPrefHeight(5);
                newAttribut.setStyle("-fx-background-color: none;");
                newAttribut.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                newAttribut.setOnMouseClicked(new ControleurCliqueDroitElement(this.classe, c, newAttribut));
                // on enlève le menu contextuel du TextField
                newAttribut.setContextMenu(null);
                this.Attributs.getChildren().add(newAttribut);
                 */
                VueAttribut newAttribut = new VueAttribut(this.Attributs, this.classe);
                newAttribut.setNom(c.toString());
                this.Attributs.getChildren().add(newAttribut);
            } else if (c instanceof Methodes) {
                /*
                TextField newMethode = new TextField(c.toString());
                newMethode.setPrefHeight(5);
                newMethode.setStyle("-fx-background-color: none;");
                newMethode.setPadding(new javafx.geometry.Insets(0, 0, 0, 5));
                 */
                VueMethode newMethode = new VueMethode(this.Methodes, this.classe);
                newMethode.setNom(c.toString());
                // permet de ne pas afficher la methode si ce n'est pas une méthode écrite dans la classe
                if(!c.toString().contains("$")) {
                    this.Methodes.getChildren().add(newMethode);
                }
            }
        }


        this.setTaille();

    }

    @Override
    public void actualiser(Sujet s) {

    }

    /**
     * getVisuel
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
        return this.Attributs;
    }

    public VBox getMethodes() {
        return this.Methodes;
    }

    /**
     * méthode ajouterAttribut
     *
     * @param attribut ajoute un attribut a la classe
     */
    public void ajouterAttribut(HBox attribut) {
        this.Attributs.getChildren().add(attribut);
    }

    /**
     * méthode ajouterMethode
     *
     * @param methode ajoute une methode a la classe
     */
    public void ajouterMethode(HBox methode) {
        this.Methodes.getChildren().add(methode);
    }

    /**
     * méthode getClasse
     *
     * @return la classe
     */
    public Classe getClasse() {
        return this.classe;
    }

    /**
     * méthode setTaille
     */
    public void setTaille() {
        this.width = (int) this.getWidth();
        this.height = (int) this.getHeight();
    }

    /**
     * méthode getCoordX
     *
     * @return coordX int la coordonnée X de la classe
     */
    public int getCoordX() {
        return this.coordX;
    }

    /**
     * méthode getCoordY
     *
     * @return coordY int la coordonnée Y de la classe
     */
    public int getCoordY() {
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
            // on ne met pas la classe correxpondante à la classe que l'on vient de cliquer
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

                // on ajoute ajoute le bouton au menuContextuel
                contentScroll.getChildren().add(bouton);

                // pour chaque bouton on ajoute un evenement qui va ajouter la dépendance à la classe correspondante
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
                    this.Attributs.getChildren().add(newAttribut);
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


}