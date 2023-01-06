package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Methodes;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Classe extends Fichier {
    private ArrayList<CompositionClasse> compositionClasses;
    private String type;
    private String superClasse;
    private ArrayList<String> interfaces;

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
            if(c.getSuperclass() != null) {
                String[] tab = c.getSuperclass().getName().split("\\.");
                this.superClasse = tab[tab.length - 1];
            }

            if(c.getInterfaces().length > 0) {
                this.interfaces = new ArrayList<>();
                for(Class<?> i : c.getInterfaces()) {
                    this.interfaces.add(i.getName());
                }
            }

            if(c.isInterface()) {
                this.type = "interface";
            } else if(Modifier.isAbstract(c.getModifiers())) {
                this.type = "abstract";
            } else {
                this.type = "class";
            }

            for (Field f : c.getDeclaredFields()) {
                String type = f.getType().toString();
                String[] tabType = type.split("\\.");
                type = tabType[tabType.length - 1];
                // on créer un Attribut
                String access = "";
                if (Modifier.isPublic(f.getModifiers())) {
                    access = "+";
                } else if (Modifier.isPrivate(f.getModifiers())) {
                    access = "-";
                } else if (Modifier.isProtected(f.getModifiers())) {
                    access = "=";
                }
                this.compositionClasses.add(new Attributs(access, f.getName(), type, null, null));
            }

            // on récupère les méthodes de la classe

            for (Method m : c.getDeclaredMethods()) {
                // on récupère le type de retour de la méthode
                String type = m.getReturnType().toString();
                String[] tabType = type.split("\\.");
                type = tabType[tabType.length - 1];
                // on créer une méthode
                String access = "";
                if (Modifier.isPublic(m.getModifiers())) {
                    access = "+";
                } else if (Modifier.isPrivate(m.getModifiers())) {
                    access = "-";
                } else if (Modifier.isProtected(m.getModifiers())) {
                    access = "=";
                }
                this.compositionClasses.add(new Methodes(access, m.getName(), type));
            }
        } catch (NoClassDefFoundError e) {
            System.out.println("Message erreur : " + e.getMessage());
        }


        //afficher();

    }


    public void setSuperClasse(String sC) {
        this.superClasse = sC;
    }

    public void setInterfaces(ArrayList<String> interfaces) {
        this.interfaces = interfaces;
    }

    public ArrayList<String> getInterfaces() {
        return this.interfaces;
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

    public String getSuperClasse(){
        return this.superClasse;
    }
}
