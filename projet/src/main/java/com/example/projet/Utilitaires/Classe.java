package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.CompositionClasse;

import java.util.ArrayList;

public class Classe extends Fichier
{
    private ArrayList<CompositionClasse> compositionClasses;

    private String type;

    private Classe superClasse;

    private ArrayList<Classe> interfaces;

    public Classe(String chemin)
    {
        super(chemin);
    }

    public void lectureFichier()
    {
        // TODO
    }
}
