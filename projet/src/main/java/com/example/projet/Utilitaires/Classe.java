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

    public void lectureFichier() throws ClassNotFoundException {

        Class.forName(this.getNom());
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)classLoader).getURLs();
        for(URL url: urls){
            System.out.println(url.getFile());
        }
        File file = new File(this.getChemin());
        File[] files = file.listFiles();
        assert files != null;
    }

    public String toString(String debut)
    {
        String res = debut + this.getNom() + "\n";
        return res;
    }
}
