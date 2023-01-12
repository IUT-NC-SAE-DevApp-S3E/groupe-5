package com.example.projet.Controleur;

import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Modele.Modele;
import com.example.projet.Modele.Sujet;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Utilitaires.Fichier;
import com.example.projet.Utilitaires.LectureFichier;
import com.example.projet.Vue.VueClasse;
import com.example.projet.Vue.VueDiagrammeClasse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe qui permet de gerer les boutons de l'arborescence
 */
public class ControleurBoutonArborescence implements EventHandler<ActionEvent> {

    private Sujet sujet;
    private String path;
    private VBox vBox;
    private boolean isClicked = false;

    private int startX = 0;
    private int startY = 0;
    private int margin = 1;

    /**
     * Constructeur du controleur du bouton arborescence
     * @param path path du dossier duquel on veut afficher l'arborescence
     * @param vBox vBox dans laquelle on ajoute l'arborescence
     * @param margin on incremente la marge a gauche pour avoir un espace a gauche des boutons
     * @param s sujet pour pouvoir le donner aux éléments necessitant un sujet
     */
    public ControleurBoutonArborescence(String path, VBox vBox, int margin , Sujet s) {
        this.path = path;
        this.vBox = vBox;
        this.margin = margin;
        this.sujet = s;
    }

    /**
     * Lorque l'on clique sur le bouton arborescence, on affiche l'arborescence du dossier dans le VBox du dessous
     * avec un margin a gauche que l'on incremente
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // on créer un Objet File avec le path recupere lors de la création du controleur
        File file = new File(this.path);
        /**
         * Si le file est un dossier et que on n'a pas déjà cliquer sur le bouton qui contient le dossier
         * alors on affiche dans le dossier le contenu du dossier
         */
        if (file.isDirectory() && !isClicked) {
            // on passe la variable isClicked a true pour signifier que l'on a cliquer sur le bouton
            isClicked = true;
            // pour chaque fichier dans le dossier
            for (File f : Objects.requireNonNull(file.listFiles())) {
                // si le fichier ne contient pas de $ et ne commence pas par un point
                if(!f.getName().contains("$") && f.getName().charAt(0) != '.') {
                    try {
                        // si le fichier est un dossier
                        if (f.isDirectory()) {
                            // on charge l'image du dossier pour l'ajouter au visuel du bouton
                            ImageView view = new ImageView(new Image("folder.png"));
                            // on créer un bouton avec le nom du dossier
                            Button bouton = new Button(f.getName());
                            // on ajoute l'image a gauche du bouton
                            bouton.setGraphic(view);
                            // la taille de l'image est de 20x20
                            view.setFitHeight(20);
                            // on preserve le ratio de l'image
                            view.setPreserveRatio(true);
                            // on ajoute le bouton a la vBox
                            this.vBox.getChildren().add(bouton);
                            // la width du bouton est la width du scrollpane
                            bouton.setPrefWidth(this.vBox.getPrefWidth());
                            // on cole le text a gauche
                            bouton.setAlignment(Pos.BASELINE_LEFT);
                            // on rend le bouton transparent
                            bouton.setStyle("-fx-background-color: transparent;");
                            // on met un margin a gauche
                            bouton.setPadding(new javafx.geometry.Insets(0, 0, 0, this.margin * 20));

                            bouton.setOnMouseEntered(event -> {
                                bouton.setCursor(javafx.scene.Cursor.MOVE);
                            });
                            bouton.setOnMouseExited(event -> {
                                bouton.setCursor(javafx.scene.Cursor.DEFAULT);
                            });

                            // on gère le système de Drag (glisser déposer)
                            bouton.setOnMousePressed(mouseEvent -> {
                                /**
                                 * les startX et startY sont les coordonnées du bouton lorsqu'on clique dessus
                                 * AU DEBUT
                                 * pour par la suite pouvoir calculer la bonne translation
                                 */
                                this.startX = (int) mouseEvent.getSceneX();
                                this.startY = (int) mouseEvent.getSceneY();
                            });

                            /**
                             * on translate le bouton lorsqu'on le glisse
                             */
                            bouton.setOnMouseDragged(event -> {
                                bouton.setTranslateX(event.getSceneX() - this.startX);
                                bouton.setTranslateY(event.getSceneY() - this.startY);
                            });


                            // Lorsque l'on relache le drag
                            bouton.setOnMouseReleased((event) -> {
                                /**
                                 * si le bouton n'est pas dans la zone de drop
                                 * on le remet a sa position initiale
                                 */
                                if (event.getSceneX() < 250) {
                                    bouton.setTranslateX(0);
                                    bouton.setTranslateY(0);
                                    /**
                                     * sinon on charge le dossier correspondant au bouton
                                     * pour pouvoir l'afficher dans la vueDiagrammeClasse
                                     */
                                } else {
                                    // on créer un objet Dossier avec le path du bouton glisser dans la zone de drop
                                    Dossier dossier = new Dossier(f.getPath(), "dossier");
                                    try {
                                        /**
                                         * on lit le dossier glisser
                                         * ce qui a l'aide du patron de conception composite
                                         * ajouter les classes correspondantes dans le dossier
                                         * au Modele
                                         */
                                        dossier.lectureDossier();
                                        /**
                                         * pour chaque Fichier dans l'objet dossier creer
                                         */
                                        for (int i = 0; i < dossier.getListeFichiers().size(); i++) {
                                            /**
                                             * si le fichier est une classe
                                             * on ajoute la classe au modele
                                             */
                                            if(dossier.getListeFichiers().get(i) instanceof Classe) {
                                                this.sujet.ajouterFichier((Classe) dossier.getListeFichiers().get(i));
                                            }
                                            /**
                                             * si le fichier est un dossier
                                             * on ajoute le dossier au modele
                                             */
                                            else if (dossier.getListeFichiers().get(i) instanceof Dossier) {
                                                ArrayList<Classe> listeClasse = ((Dossier) dossier.getListeFichiers().get(i)).getClasse();
                                                for (int j = 0; j < listeClasse.size(); j++) {
                                                    this.sujet.ajouterFichier(listeClasse.get(j));
                                                }
                                            }
                                        }
                                        this.sujet.notifierObservateur();
                                    } catch (Exception e) {
                                        // TODO on ne fait rien car le fichier est mauvais, dommage
                                    }
                                    // on remet le bouton a sa place et on le rend non draggable
                                    bouton.setTranslateX(0);
                                    bouton.setTranslateY(0);
                                    bouton.setMnemonicParsing(true);
                                }
                            });
                            // on met le controlleur sur le bouton
                            VBox bottomFile = new VBox();
                            bottomFile.setPrefWidth(this.vBox.getPrefWidth());
                            bottomFile.setSpacing(3);
                            this.vBox.getChildren().add(bottomFile);
                            bouton.setOnAction(new ControleurBoutonArborescence(f.getPath(), bottomFile, this.margin + 1, this.sujet) );
                            /**
                             * Si l'element créer est un fichier
                             */
                        } else {
                            // on charge l'image de l'icon des fichiers
                            ImageView view = new ImageView(new Image("file.png"));
                            // on ajoute un label au lieu d'un bouton
                            Label label = new Label(" | " + f.getName());
                            // on met la couleur du label en blanc
                            label.setStyle("-fx-text-fill: black;");
                            // on met un margin a gauche
                            label.setPadding(new javafx.geometry.Insets(0, 0, 0, this.margin * 20));

                            label.setOnMouseEntered(event -> {
                                label.setCursor(javafx.scene.Cursor.MOVE);
                            });
                            label.setOnMouseExited(event -> {
                                label.setCursor(javafx.scene.Cursor.DEFAULT);
                            });

                            /**
                             * on rends ici le fichier draggable
                             * on commence par set l'endroit ou on clique au depart
                             * pour par la suite pouvoir calculer la bonne translation
                             */
                            label.setOnMousePressed(mouseEvent -> {
                                this.startX = (int) mouseEvent.getSceneX();
                                this.startY = (int) mouseEvent.getSceneY();
                            });
                            /**
                             * on translate le bouton lorsqu'on le drag
                             */
                            label.setOnMouseDragged(event -> {
                                label.setTranslateX(event.getSceneX() - this.startX);
                                label.setTranslateY(event.getSceneY() - this.startY);
                            });
                            /**
                             * Lorsque l'on relache le drag
                             */
                            label.setOnMouseReleased((event) -> {
                                /**
                                 * si le bouton n'est pas dans la zone de drop
                                 * on le remet a sa position initiale
                                 */
                                if (event.getSceneX() < 250) {
                                    label.setTranslateX(0);
                                    label.setTranslateY(0);
                                    /**
                                     * Si lefichier est laché dans la zone de drop
                                     */
                                } else {
                                    // on créer un objet class avec le path du fichier glisser dans la zone de drop
                                    Classe c = new Classe(f.getPath(), f.getName());
                                    try {
                                        /**
                                         * on lit le fichier glisser
                                         * ce qui ajoute a l'objet class les attributs et methodes
                                         */
                                        c.lectureFichier();
                                    } catch (Exception e) {
                                        // TODO on ne fait rien car le fichier est mauvais, dommage
                                    }
                                    try {
                                        // on ajoute la class au modele
                                        this.sujet.ajouterFichier(c);
                                        /**
                                         * on notifie les observateurs
                                         * ce qui va recharger la VueDiagramme de classe
                                         */
                                        this.sujet.notifierObservateur();
                                    } catch (Exception e) {
                                        System.out.println("le fichier est mauvais");
                                    }
                                }
                            });
                            // on ajoute l'image a gauche du label
                            label.setGraphic(view);
                            // la taille de l'image est de 20x20
                            view.setFitHeight(20);
                            view.setPreserveRatio(true);
                            this.vBox.getChildren().add(label);
                            // la width du label est la width du scrollpane
                            label.setPrefWidth(this.vBox.getPrefWidth());
                        }
                    } catch (Exception e) {
                        System.out.println("erreur");
                    }
                }

            }
            /**
             * Sinon si le fichier est un dossier et qu'on à déjà cliquer sur le bouton
             * on supprime le contenu du VBox du dessous pour fermer le dossier
             */
        } else if (file.isDirectory() && isClicked) {
            // on met le boolean a false pour dire que le dossier est fermé
            isClicked = false;
            // on supprime tout le contenu du VBox
            this.vBox.getChildren().clear();
        }

    }


}
