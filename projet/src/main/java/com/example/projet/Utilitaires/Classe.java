package com.example.projet.Utilitaires;

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
    }

    public void lectureFichier() throws ClassNotFoundException {
        // on fait de l'introspection sur le fichier
        // on récupère le nom de la classe
        String nomClasse = this.getNom().substring(0, this.getNom().length() - 6);
        // on récupère le chemin du fichier
        String chemin = this.getChemin();
        // on récupère le chemin du dossier
        String dossier = chemin.substring(0, chemin.length() - this.getNom().length());
        // on crée un nouveau classloader
        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{new File(dossier).toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // on charge la classe
        Class<?> c = classLoader.loadClass(nomClasse);
        // on affiche les attributs
        System.out.println("Attributs de la classe " + nomClasse + " :");
        for (java.lang.reflect.Field f : c.getDeclaredFields()) {
            System.out.println(" - " + f.getName());
        }
    }

    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        return res;
    }
}
