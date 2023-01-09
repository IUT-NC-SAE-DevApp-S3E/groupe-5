package com.example.projet.Utilitaires;

public class MoyValue {

    private int nbFilsSuper = 0;
    private int nbFilsImplements = 0;

    /**
     * méthode ajouterFilsSuper
     */
    public void ajouterFilsSuper() {
        this.nbFilsSuper++;
    }

    /**
     * méthode ajouterFilsImplements
     */
    public void ajouterFilsImplements() {
        this.nbFilsImplements++;
    }

    public double getValue() {
        return (this.nbFilsSuper + this.nbFilsImplements);
    }
}