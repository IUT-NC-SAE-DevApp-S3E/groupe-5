package com.example.projet.Utilitaires;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dossier extends Fichier {

    private ArrayList<Fichier> listeFichiers;

    public Dossier(String chemin, String nom) {
        super(chemin, nom);
        this.listeFichiers = new ArrayList<>();
    }

    /**
     * Methode lectureDossier() permettant de lire un dossier puis de l'ajouter dans la liste de fichier,
     * ce principe fonctionne à l'aide du patron de conception composite.
     * @throws IOException
     */
    public void lectureDossier() {
        File file = new File(this.getChemin());
        // on récupère la liste des fichiers
        File[] files = file.listFiles();
        // on parcours la liste des fichiers
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                if (f.getAbsolutePath().endsWith(".class") && !f.getName().contains("module-info")) {
                    // à chaque fois que l'on tombe sur un fichier en .class on crée un objet Classe
                    Classe c = new Classe(f.getAbsolutePath(), f.getName().replace(".class", ""));
                    System.out.println("----" + f.getAbsolutePath());
                    c.lectureFichier();
                    // Puis on l'ajoute à la liste de fichier.
                    this.listeFichiers.add(c);
                }
            } else if (f.isDirectory()) {
                // Si le fichier est un dossier afin de fonctionne récursivement, il va récupérer le chemin du dossier
                // et le nom du dossier puis il va créer un objet Dossier et va relancer le méthode lecture fichier afin de
                // récuperer tous les fichiers classe et sous dossier.
                Dossier dossier = new Dossier(f.getAbsolutePath(), f.getName());
                dossier.lectureDossier();
                this.listeFichiers.add(dossier);
            }
        }
    }


    /**
     * Methode permettant d'afficher l'arborescence du fichier en cours
     * @param debut
     * @return
     */

    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        for (Fichier f : this.listeFichiers) {
            res += f.toString(debut + " | ");
        }
        return res;
    }


    // Getters and Setters
    public ArrayList<Fichier> getListeFichiers() {
        return listeFichiers;
    }

    public ArrayList<Classe> getClasse()
    {
        ArrayList<Classe> res = new ArrayList<>();
        for (Fichier f : this.listeFichiers) {
            if(f instanceof Classe)
            {
                res.add((Classe)f);
            }
            else
            {
                res.addAll(((Dossier)f).getClasse());
            }
        }
        return res;
    }

}