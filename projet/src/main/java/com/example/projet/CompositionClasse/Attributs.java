package com.example.projet.CompositionClasse;

import com.example.projet.Utilitaires.Classe;

public class Attributs extends CompositionClasse {

    private String definition;

    private Classe typeAttribut;

    public Attributs(String acces, String nom, String type, String definition, Classe typeAttribut) {
        super(acces, nom, type);
        this.definition = definition;
        this.typeAttribut = typeAttribut;
    }

}

