package com.example.projet.Utilitaires;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LectureFichier {
    public LectureFichier() {
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
        String nomF = nomFichier.replace(".class", "");
        boolean trouver = false;
        Class<?> c = null;
        file = file.getParentFile();
        while (nbDossier > 0 && !trouver) {
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()})) {
                c = classLoader.loadClass(nomF);
                trouver = true;
            }
            catch (NoClassDefFoundError e)
            {
                System.out.println("Message erreur : "+e.getMessage());
                nomF = e.getMessage().split(" ")[0].replace("/", ".");
                System.out.println("NomF : " + nomF);
                nbDossier--;
                file = file.getParentFile();
                System.out.println("Chemin : " + file.getAbsolutePath());
            }
            catch(ClassNotFoundException | IOException e)
            {
                System.out.println(e.getMessage());
            }

        }
        return c;
    }
}
