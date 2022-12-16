package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.CompositionClasse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Classe extends Fichier
{
    private ArrayList<CompositionClasse> compositionClasses;

    private String type;

    private Classe superClasse;

    private ArrayList<Classe> interfaces;

    public Classe(String chemin, String nom)
    {
        super(chemin, nom);
    }

    public void lectureFichier() throws MalformedURLException, ClassNotFoundException {
        // Charger une classe pour utiliser l'introspection à l'aide d'un chemin absolue
        File file = new File(this.getChemin());
        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);
        System.out.println(this.getChemin());
        System.out.println(this.getNom());
        Class<?> cls = cl.loadClass(this.getNom().substring(0, this.getNom().length() - 6));

    }

    public String toString(String debut)
    {
        String res = debut + this.getNom() + "\n";
        return res;
    }
}
