package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Classe extends Fichier {
    private ArrayList<CompositionClasse> compositionClasses;

    private String type;

    private Classe superClasse;

    private ArrayList<Classe> interfaces;

    public Classe(String chemin, String nom) {
        super(chemin, nom);
        this.compositionClasses = new ArrayList<>();
    }

    public void lectureFichier() throws MalformedURLException {
        Class<?> c = LectureFichier.lectureFichier(this.getChemin(), this.getNom());

        for (java.lang.reflect.Field f : c.getDeclaredFields()) {
            String type = f.getType().toString();
            String[] tabType = type.split("\\.");
            type = tabType[tabType.length-1];
            // on créer un Attribut
            this.compositionClasses.add(new Attributs(null, f.getName(), type , null ,null));
        }
        afficher();

    }


    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        return res;
    }

    public void afficher()
    {
        for (CompositionClasse c : this.compositionClasses)
        {
            System.out.println(c);
        }
    }
}
