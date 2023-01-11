package com.example.projet.Utilitaires;

import java.io.Serializable;

public class MoyValue implements Serializable {

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
