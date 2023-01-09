package com.example.projet.CompositionClasse;

import com.example.projet.Utilitaires.Classe;

/**
 * Classe Attributs qui permet de gerer les attributs d'une classe
 */
public class Attributs extends CompositionClasse {

    /**
     * definition de l'attribut
     */
    private String definition;

    /**
     * type de l'attribut
     */
    // private Classe typeAttribut;

    /**
     * Constructeur de la classe Attributs qui initialise les attributs
     *
     * @param acces      l'acces de l'attribut
     * @param nom        le nom de l'attribut
     * @param type       le type de l'attribut
     * @param definition la definition de l'attribut
     */
    public Attributs(String acces, String nom, String type, String definition) {
        super(acces, nom, type);
        this.definition = definition;
    }

    /**
     * GETTER DES ATTRIBUTS
     */
    public String getDefinition() {
        return definition;
    }

    /*
    public String getTypeAttribut() {
        return typeAttribut;
    }
     */

    /**
     * SETTER DES ATTRIBUTS
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /*
    public void setTypeAttribut(String typeAttribut) {
        this.typeAttribut = typeAttribut;
    }
     */

    /**
     * methode toString qui permet d'afficher l'attribut sous forme de chaine de caractere
     *
     * @return la chaine de caractere
     */
    @Override
    public String toString() {
        String res = this.getAcces() +" " + this.getDefinition() + " " + this.getType() + " " + this.getNom();
        return res;
    }


}

