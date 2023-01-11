package com.example.projet.CompositionClasse;

import java.util.ArrayList;

/**
 * Classe Constructeur qui permet de gerer les constructeurs d'une classe
 */
public class Constructeur extends CompositionClasse {

    /**
     * liste des parametres du constructeur
     */
    private ArrayList<String> parametres;

    /**
     * Constructeur de la classe Constructeur qui initialise les attributs
     * @param acces l'acces du constructeur
     * @param nom le nom du constructeur
     * @param type le type du constructeur
     */
    public Constructeur(String acces, String nom, String type, ArrayList<String> parametres) {
        super(acces, nom, type);
        this.parametres = parametres;
    }

    /**
     * methode toString de la classe Constructeur qui permet d'afficher un constructeur
     * @return le constructeur
     */
    public String toString(){
        String res = this.getAcces() + " " + this.getNom() + "(";
        for (int i = 0; i < this.parametres.size(); i++) {
            res += this.parametres.get(i);
            if (i != this.parametres.size() - 1) {
                res += ", ";
            }
        }
        res += ")";
        return res;
    }

    /**
     * méthode getSqueletteJava
     * qui retourne sous forme de String le constructeur en java
     */
    public String getSqueletteJava() {
        String acces = "public";
        if (this.getAcces().equals("-")) {
            acces = "private";
        } else if (this.getAcces().equals("#")){
            acces = "protected";
        }
        String res = acces + " " + this.getNom() + "(";
        for (int i = 0; i < this.parametres.size(); i++) {
            res += this.parametres.get(i);
            if (i != this.parametres.size() - 1) {
                res += ", ";
            }
        }
        res += ") {";
        return res;
    }

}
