package com.example.projet.CompositionClasse;

import java.util.ArrayList;

public class Methodes extends CompositionClasse {

    private String definition, retour;
    private ArrayList<String> parametres;

    public Methodes(String acces, String nom, String type) {
        super(acces, nom, type);
    }

    /**
     * GETTER DES ATTRIBUTS
     */
    public String getDefinition() {
        return this.definition;
    }

    public String getRetour() {
        return this.retour;
    }

    public ArrayList<String> getParametres() {
        return this.parametres;
    }

    /**
     * SETTER DES ATTRIBUTS
     */

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setRetour(String retour) {
        this.retour = retour;
    }
}
