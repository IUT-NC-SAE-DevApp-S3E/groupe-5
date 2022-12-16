package com.example.projet.CompositionClasse;

public abstract class CompositionClasse {

    private String acces, nom, type;

    public CompositionClasse(String acces, String nom, String type) {
        this.acces = acces;
        this.nom = nom;
        this.type = type;
    }

    //getter des attributs
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
