package com.example.projet.Utilitaires;

public class Fichier
{
    private String nom, chemin;

    public Fichier(String chemin)
    {
        this.chemin = chemin;
    }

    public String getNom()
    {
        return nom;
    }

    public String getChemin()
    {
        return chemin;
    }
}
