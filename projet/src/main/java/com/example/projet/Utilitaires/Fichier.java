package com.example.projet.Utilitaires;

public abstract class Fichier {
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

    public String getChemin() {
        return chemin;
    }

    public abstract String toString(String debut);
}
