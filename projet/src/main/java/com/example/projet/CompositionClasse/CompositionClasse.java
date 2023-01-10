package com.example.projet.CompositionClasse;

import java.io.Serializable;

/**
 * Classe abstraite CompositionClasse qui permet de gerer les attributs, constructeurs et methodes d'une classe
 */
public abstract class CompositionClasse implements Serializable {

    /**
     * accès de l'attribut, son nom et son type
     */
    private String acces, nom, type;

    /**
     * Constructeur de la classe CompositionClasse qui initialise les attributs
     * @param acces l'acces de l'attribut
     * @param nom le nom de l'attribut
     * @param type le type de l'attribut
     */
    public CompositionClasse(String acces, String nom, String type) {
        this.acces = acces;
        this.nom = nom;
        this.type = type;
    }

    /**
     * GETTER DES ATTRIBUTS
     */
    public String getAcces() {
        return acces;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
}
