package com.example.projet.Utilitaires;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LectureFichier
{
    public LectureFichier()
    {
    }

    public static Class<?> lectureFichier(String chemin, String nomFichier) throws MalformedURLException {
        // on récupère le chemin et on remplace les \ par des /
        String chem = chemin.replace("\\", "/");
        // on récupère le nombre de nom de dossier
        String[] tab = chem.split("/");
        int nbDossier = tab.length - 2;
        // on crée un objet File
        File file = new File(chem);
        // on crée un objet URLClassLoader
        URLClassLoader classLoader = null;
        classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
        String nomF = nomFichier.replace(".class", "");
        boolean trouver = false;
        Class<?> c = null;
        while (nbDossier > 0 && !trouver) {
            try {
                //System.out.println("Nom du fichier : " + nomFichier);
                c = classLoader.loadClass(nomF);
                //System.out.println("trouver");
                trouver = true;
            } catch (Exception e) {
                nomF = tab[nbDossier] + "." + nomF;
                nbDossier--;
            }
        }
        return c;
    }
}
