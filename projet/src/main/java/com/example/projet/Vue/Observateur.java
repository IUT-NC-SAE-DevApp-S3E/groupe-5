package com.example.projet.Vue;

import com.example.projet.Modele.Sujet;

import java.io.Serializable;

/**
 * Interface Observateur
 */
public interface Observateur extends Serializable {

    /**
     * Methode qui permet de mettre a jour l'observateur
     * @param s le sujet
     */
    public void actualiser(Sujet s);
}
