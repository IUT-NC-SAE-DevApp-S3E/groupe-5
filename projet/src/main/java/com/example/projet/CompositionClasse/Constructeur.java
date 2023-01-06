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
    public Constructeur(String acces, String nom, String type) {
        super(acces, nom, type);
    }

}
