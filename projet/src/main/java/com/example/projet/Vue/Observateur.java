package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;

/**
 * Interface Observateur
 */
public interface Observateur {

    /**
     * Methode qui permet de mettre a jour l'observateur
     * @param s le sujet
     */
    public void actualiser(Sujet s);
}
