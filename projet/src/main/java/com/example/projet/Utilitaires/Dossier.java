package com.example.projet.Utilitaires;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dossier extends Fichier {

    private ArrayList<Fichier> listeFichiers;

    public Dossier(String chemin, String nom) {
        super(chemin, nom);
        this.listeFichiers = new ArrayList<Fichier>();
    }

    public void lectureDossier() throws ClassNotFoundException, IOException {
        File file = new File(this.getChemin());
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                if(f.getAbsolutePath().endsWith(".class")) {
                    Classe c = new Classe(f.getAbsolutePath(), f.getName());
                    c.lectureFichier();
                    this.listeFichiers.add(c);
                }
            } else if (f.isDirectory()) {
                Dossier dossier = new Dossier(f.getAbsolutePath(), f.getName());
                dossier.lectureDossier();
                this.listeFichiers.add(dossier);
            }
        }
    }

    public ArrayList<String> recupererArborescence(String debut) {
        ArrayList<String> res = new ArrayList<String>();
        res.add(debut + this.getNom() + "\n");
        for (Fichier f : this.listeFichiers) {
            res.add(debut + f.toString(" | "));
        }
        return res;
    }

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

}