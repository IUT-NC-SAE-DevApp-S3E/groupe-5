package com.example.projet.Controleur;

import com.example.projet.Modele.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.MalformedURLException;

/**
 * Classe qui permet de gerer les boutons d'affichages
 */
public class ControleurBoutonAffichage implements EventHandler<ActionEvent> {

    private Sujet sujet;

    public ControleurBoutonAffichage(Sujet s)
    {
        this.sujet = s;
    }

    /**
     * methode handle qui permet de gerer les boutons d'affichages
     * @param actionEvent l'evenement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        String id = actionEvent.getSource().toString().split("'")[1];
        switch (id)
        {
            case "M":
            case "P":
            case "D":
            case "A":
                break;

            default:
                this.sujet.inverserAffichage();
                break;
        }
        this.sujet.changerAffichage(id);
        try {
            sujet.notifierObservateur();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
