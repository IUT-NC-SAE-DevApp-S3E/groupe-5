package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import com.example.projet.Vue.VueAttribut;
import com.example.projet.Vue.VueClasse;
import com.example.projet.Vue.VueMethode;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Classe ControleurCliqueDroitClasse qui permet de gerer le clic droit sur une classe
 */
public class ControleurCliqueDroitClasse implements EventHandler<MouseEvent> {

    /**
     * Attribut de la classe ControleurCliqueDroitClasse
     */
    private final Sujet sujet;
    private final Pane pane;
    private VBox menu;
    private ScrollPane menuContextuel = new ScrollPane();

    private final VueClasse actualVBox;
    private boolean clicked = false;

    /**
     * Constructeur de la classe ControleurCliqueDroitClasse
     * initialise les attributs
     * @param sujet le sujet
     * @param pane le pane
     * @param actualVBox la vue de la classe
     */
    public ControleurCliqueDroitClasse(Sujet sujet, Pane pane, VueClasse actualVBox) {
        this.sujet = sujet;
        this.pane = pane;
        this.actualVBox = actualVBox;

        this.pane.setOnMousePressed(mouseEvent -> {
            // quand un clique droit est effectué on récupère les coordonnées du clique
            // pour pouvoir le menu ensuite au bon endroit
            this.sujet.setStartX((int)mouseEvent.getX());
            this.sujet.setStartY((int)mouseEvent.getY());
        });
    }

    /**
     * methode handle qui permet de gerer le clic droit sur une classe
     * @param event l'evenement
     */
    @Override
    public void handle(MouseEvent event) {
        if(event.getButton().toString().equals("SECONDARY") && !this.clicked) {
            this.pane.getChildren().remove(this.menuContextuel);
            this.clicked = true;
            VBox menu = new VBox();
            this.menu = menu;

            String[] titreBoutons = {"+ Ajouter un attribut", "+ Ajouter une méthode", "+ Ajouter une dépendance", "+ ajouter un héritage", "+ ajouter une implémentation", "- Supprimer"};
            for (int i = 0; i < titreBoutons.length; i++) {
                Button bouton = new Button(titreBoutons[i]);
                bouton.setStyle("-fx-background-color: #f3f3f3;");
                bouton.setPrefWidth(200);
                bouton.setPrefHeight(25);
                // on met un hover aux boutons pour qu'ils soient plus visibles
                bouton.setOnMouseEntered(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #ababab;");
                });
                bouton.setOnMouseExited(mouseEvent -> {
                    bouton.setStyle("-fx-background-color: #f3f3f3;");
                });
                menu.getChildren().add(bouton);
                switch (i) {
                    case 0:
                        bouton.setOnAction(event1 -> {
                            VueAttribut newAttribut = new VueAttribut(this.actualVBox.getAttributs(),this.actualVBox.getClasse());
                            newAttribut.setNom("+ new");
                            this.actualVBox.ajouterAttribut(newAttribut);
                            try {
                                this.sujet.notifierObservateur();
                            } catch (Exception e) {
                                // TODO
                            }
                        });
                        break;
                    case 1:
                        bouton.setOnAction(event1 -> {
                            VueMethode newMethode = new VueMethode(this.actualVBox.getMethodes(), this.actualVBox.getClasse());
                            newMethode.setNom("+ new");
                            this.actualVBox.ajouterMethode(newMethode);
                            try {
                                this.sujet.notifierObservateur();
                            } catch (Exception e) {
                                // TODO
                            }
                        });
                        break;
                    case 2:
                        bouton.setOnAction(event1 -> {
                            ScrollPane newMenuListeDependance = this.actualVBox.afficherMenuDependance();
                            newMenuListeDependance.setPrefWidth(200);
                            newMenuListeDependance.setPrefHeight(300);
                            // on rend le menu non scrollable sur les y
                            newMenuListeDependance.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                            this.pane.getChildren().add(newMenuListeDependance);
                            newMenuListeDependance.setLayoutX(menu.getLayoutX()+menu.getWidth());
                            newMenuListeDependance.setLayoutY(menu.getLayoutY());
                            this.menuContextuel = newMenuListeDependance;
                            try {
                                this.sujet.notifierObservateur();
                            } catch (Exception e) {
                                // TODO
                            }
                        });
                        break;
                    case 3:
                        bouton.setOnAction(event1 -> {
                            ScrollPane newMenuListeHeritage = this.actualVBox.afficherMenuHeritage();
                            newMenuListeHeritage.setPrefWidth(200);
                            newMenuListeHeritage.setPrefHeight(300);
                            // on rend le menu non scrollable sur les y
                            newMenuListeHeritage.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                            this.pane.getChildren().add(newMenuListeHeritage);
                            newMenuListeHeritage.setLayoutX(menu.getLayoutX()+menu.getWidth());
                            newMenuListeHeritage.setLayoutY(menu.getLayoutY());
                            this.menuContextuel = newMenuListeHeritage;
                            try {
                                this.sujet.notifierObservateur();
                            } catch (Exception e) {
                                // TODO
                            }
                        });
                        break;
                    case 4:
                        // ajouter une implémentation
                        bouton.setOnAction(event1 -> {
                            ScrollPane newMenuImplementation = this.actualVBox.afficherMenuImplementation();
                            newMenuImplementation.setPrefWidth(200);
                            newMenuImplementation.setPrefHeight(300);
                            // on rend le menu non scrollable sur les y
                            newMenuImplementation.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                            this.pane.getChildren().add(newMenuImplementation);
                            newMenuImplementation.setLayoutX(menu.getLayoutX()+menu.getWidth());
                            newMenuImplementation.setLayoutY(menu.getLayoutY());
                            this.menuContextuel = newMenuImplementation;
                            try {
                                this.sujet.notifierObservateur();
                            } catch (Exception e) {
                                // TODO
                            }
                        });
                        break;
                    case 5:
                        bouton.setOnAction(event2 -> {
                            // on supprime la classe
                            this.pane.getChildren().remove(this.actualVBox);
                            // on remove aussi la classe du modèle
                            this.sujet.supprimerFichier(this.actualVBox.getClasse());
                            this.pane.getChildren().remove(this.menu);
                            this.pane.getChildren().remove(this.menuContextuel);
                        });
                        break;
                }
            }
            // on ajoute le visuel du menu au pane
            this.pane.getChildren().add(menu);
            menu.setPrefSize(200, 100);
            // on met le menu a la position du clique
            // on place le menu a la position du clique
            menu.setLayoutX(this.sujet.getStartX() - 10);
            menu.setLayoutY(this.sujet.getStartY() - 10);
            // on met 0 padding au menu
            menu.setPadding(new javafx.geometry.Insets(1, 1, 1, 1));
            menu.setStyle("-fx-background-color: rgba(169,165,165,0.28);");
            // si la souris n'est pas au dessus du menu on le supprrime

            /*
             * si on a déjà fait un clique droit
             */
        } else {
            // on supprime le menu
            // on met le boolean cliquer a false pour pouvoir refaire un clique droit
            this.clicked = false;
            this.pane.getChildren().remove(this.menu);
            this.pane.getChildren().remove(this.menuContextuel);
        }
    }

}
