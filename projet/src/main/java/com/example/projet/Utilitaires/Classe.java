package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Methodes;

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

    /**
     * Constructeur de la classe Classe
     * ce constructeur prend deux paramètres
     * @param chemin du fichier de la classe
     * @param nom du fichier de la classe
     */
    public Classe(String chemin, String nom) {
        super(chemin, nom);
        this.compositionClasses = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Classe
     * ce constructeur prend un seul paramètre
     * ce constructeur permet de créer un classe à partir du bouton et nom d'un fichier réel
     * @param nom
     */
    public Classe(String nom) {
        super(nom);
        this.compositionClasses = new ArrayList<>();
    }

    /**
     * méthode lectureFichier
     * @throws MalformedURLException
     */
    public void lectureFichier() throws MalformedURLException {
        Class<?> c = LectureFichier.lectureFichier(this.getChemin(), this.getNom());
        try {
            for (java.lang.reflect.Field f : c.getDeclaredFields()) {
                String type = f.getType().toString();
                String[] tabType = type.split("\\.");
                type = tabType[tabType.length - 1];
                // on créer un Attribut
                String access = "";
                if (java.lang.reflect.Modifier.isPublic(f.getModifiers())) {
                    access = "+";
                } else if (java.lang.reflect.Modifier.isPrivate(f.getModifiers())) {
                    access = "-";
                } else if (java.lang.reflect.Modifier.isProtected(f.getModifiers())) {
                    access = "=";
                }
                this.compositionClasses.add(new Attributs(access, f.getName(), type, null, null));
            }

            // on récupère les méthodes de la classe

            for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
                // on récupère le type de retour de la méthode
                String type = m.getReturnType().toString();
                String[] tabType = type.split("\\.");
                type = tabType[tabType.length - 1];
                // on créer une méthode
                String access = "";
                if (java.lang.reflect.Modifier.isPublic(m.getModifiers())) {
                    access = "+";
                } else if (java.lang.reflect.Modifier.isPrivate(m.getModifiers())) {
                    access = "-";
                } else if (java.lang.reflect.Modifier.isProtected(m.getModifiers())) {
                    access = "=";
                }
                this.compositionClasses.add(new Methodes(access, m.getName(), type));
            }
        } catch (NoClassDefFoundError e) {
            System.out.println("Message erreur : " + e.getMessage());
        }


        afficher();

    }


    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        return res;
    }

    public void afficher() {
        System.out.println(this.getNom());
        for (CompositionClasse c : this.compositionClasses) {
            System.out.println(c);
        }
        System.out.println("------------------------------------------------");
    }

    /**
     * getter
     */
    public ArrayList<CompositionClasse> getCompositionClasses() {
        return compositionClasses;
    }
}
