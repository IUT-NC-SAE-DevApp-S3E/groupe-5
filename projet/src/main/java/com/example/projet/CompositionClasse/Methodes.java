package com.example.projet.CompositionClasse;

import java.util.ArrayList;

/**
 * Classe qui permet de gerer les methodes d'une classe
 */
public class Methodes extends CompositionClasse {

    /**
     * definition de la methode et son type de retour
     */
    private String definition, retour;
    /**
     * liste des parametres de la methode
     */
    private ArrayList<String> parametres;

    /**
     * Constructeur de la classe Methodes qui initiamise les attributs
     * @param acces l'acces de la methode
     * @param nom le nom de la methode
     * @param type le type de la methode
     */
    public Methodes(String acces, String nom, String type, String definition) {
        super(acces, nom, type);
        this.definition = definition;
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

    /**
     * methode toString qui permet d'afficher la méthode sous forme de chaine de caractere
     */
    @Override
    public String toString() {
        return this.getAcces() + " " + this.getDefinition() + " " + this.getType() + " " + this.getNom() + "()";
        }
}
