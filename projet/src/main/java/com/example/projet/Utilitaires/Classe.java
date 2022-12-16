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
    }

    public void lectureFichier() throws ClassNotFoundException {
        // on affiche le chemin du fichier
        //System.out.println("Chemin du fichier : " + getChemin());

        // on récupère le chemin et on remplace les \ par des /
        String chemin = getChemin().replace("\\", "/");
        //System.out.println("Chemin du fichier : " + chemin);

        // on récupère le nombre de nom de dossier
        String[] tab = chemin.split("/");


        // on affiche le tableau
        /*
        for (String s : tab) {
            System.out.print(s+" - ");
        }
        System.out.println();
        */


        int nbDossier = tab.length-2;

        // on crée un objet File
        File file = new File(chemin);


        // on crée un objet URLClassLoader
        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        } catch (MalformedURLException e) {
        }

        String nomFichier = getNom().replace(".class", "");

        boolean trouver = false;
        while (nbDossier > 0 && !trouver) {
            try {
                //System.out.println("Nom du fichier : " + nomFichier);
                Class<?> c = classLoader.loadClass(nomFichier);
                //System.out.println("trouver");
                trouver = true;

                // on affiche le nom de la classe
                System.out.println("Nom de la classe : " + nomFichier);

                // on affiche les attributs de la classe
                System.out.println("Attributs de la classe " + nomFichier + " :");
                for (java.lang.reflect.Field f : c.getDeclaredFields()) {
                    System.out.println(" - " + f.getName());
                    // on créer un Attribut
                    Attributs attribut = new Attributs("test", f.getName(), f.getType().getName(), f.toString(), null);
                }
            } catch (Exception e) {
                nomFichier = tab[nbDossier] + "." + nomFichier;
                nbDossier--;
            }
        }
    }


    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        return res;
    }
}
