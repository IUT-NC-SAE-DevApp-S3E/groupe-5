package com.example.projet.Utilitaires;

import java.io.Serializable;

public abstract class Fichier implements Serializable {
    private String nom, chemin;


    public Fichier(String chemin, String nom) {
        this.chemin = chemin;
        this.nom = nom;
    }

    public Fichier (String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public abstract String toString(String debut);
}
