package com.example.projet.Utilitaires;

import java.io.Serializable;

/**
 * Classe Fichier qui permet de gerer les fichiers
 */
public abstract class Fichier implements Serializable {

    /**
     * nom et chemin du fichier
     */
    private String nom, chemin;

    /**
     * Constructeur de la classe Fichier
     * @param chemin chemin du fichier
     * @param nom nom du fichier
     */
    public Fichier(String chemin, String nom) {
        this.chemin = chemin;
        this.nom = nom;
    }

    /**
     * Constructeur de la classe Fichier
     * @param nom nom du fichier
     */
    public Fichier (String nom) {
        this.nom = nom;
    }

    /**
     * GETTER ET SETTER
     */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    /**
     * Methode toString() permettant d'afficher le fichier
     */
    public abstract String toString(String debut);
}
