package com.example.projet.Utilitaires;

import java.io.Serializable;


/**
 * Classe MoyValue qui permet de gerer les moyennes
 */
public class MoyValue implements Serializable {

    /**
     * valeur initiale
     */
    private int value = 0;

    /**
     * méthode addValue
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * méthode getValue
     */
    public int getValue() {
        return this.value;
    }


}
