package com.example.projet.Utilitaires;

import com.example.projet.CompositionClasse.Attributs;
import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.CompositionClasse.Constructeur;
import com.example.projet.CompositionClasse.Methodes;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Classe extends Fichier {
    private ArrayList<CompositionClasse> compositionClasses;
    private String type = "";
    private String superClasse;
    private ArrayList<String> interfaces;
    private MoyValue moyValue = new MoyValue();
    private String packageClasse;

    /**
     * Constructeur de la classe Classe
     * ce constructeur prend deux paramètres
     *
     * @param chemin du fichier de la classe
     * @param nom    du fichier de la classe
     */
    public Classe(String chemin, String nom) {
        super(chemin, nom);
        this.compositionClasses = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Classe
     * ce constructeur prend un seul paramètre
     * ce constructeur permet de créer un classe à partir du bouton et nom d'un fichier réel
     *
     * @param nom
     */
    public Classe(String nom) {
        super(nom);
        this.compositionClasses = new ArrayList<>();
    }

    /**
     * méthode lectureFichier
     *
     * @throws MalformedURLException
     */
    public void lectureFichier() {
        Class<?> c = LectureFichier.lectureFichier(this.getChemin(), this.getNom());
        try {
            this.packageClasse = c.getPackageName();

            if (c.getSuperclass() != null) {
                String[] tab = c.getSuperclass().getName().split("\\.");
                this.superClasse = tab[tab.length - 1];
            }

            for (Class<?> i : c.getInterfaces()) {
                String[] nomInterface = i.getName().split("\\.");
                this.interfaces.add(nomInterface[nomInterface.length - 1]);
            }


            if (c.isInterface()) {
                this.type = "interface";
            } else if (Modifier.isAbstract(c.getModifiers())) {
                this.type = "abstract";
            } else {
                this.type = "class";
            }

            for (Field f : c.getDeclaredFields()) {
                String type = f.getType().toString();
                String[] tabType = type.split("\\.");
                type = tabType[tabType.length - 1];
                // on créer un Attribut
                String access = "", definition = "";
                if (Modifier.isPublic(f.getModifiers())) {
                    access = "+";
                } else if (Modifier.isPrivate(f.getModifiers())) {
                    access = "-";
                } else if (Modifier.isProtected(f.getModifiers())) {
                    access = "=";
                }
                if(Modifier.isFinal(f.getModifiers())){
                    definition += "final ";
                }
                if(Modifier.isStatic(f.getModifiers())){
                    definition += "static";
                }
                this.compositionClasses.add(new Attributs(access, f.getName(), type, definition));
            }

            // on recupere les constructeurs de la classe
            for(Constructor constructor : c.getDeclaredConstructors()){
                String access = "";
                if (Modifier.isPublic(constructor.getModifiers())) {
                    access = "+";
                } else if (Modifier.isPrivate(constructor.getModifiers())) {
                    access = "-";
                } else if (Modifier.isProtected(constructor.getModifiers())) {
                    access = "=";
                }
                // lecture et ajout des paramètres des constructeurs
                ArrayList<String> parametres = new ArrayList<>();
                for(Parameter parametre : constructor.getParameters()){
                    String type = parametre.getType().toString();
                    String[] tabType = type.split("\\.");
                    type = tabType[tabType.length - 1];
                    parametres.add(type);
                }
                String[] nomMethode = constructor.getName().split("\\.");
                this.compositionClasses.add(new Constructeur(access, nomMethode[nomMethode.length-1], "", parametres));
            }

            // on récupère les méthodes de la classe
            for (Method m : c.getDeclaredMethods()) {
                // on récupère le type de retour de la méthode
                String type = m.getReturnType().toString();
                if(type.contains("[")){
                    String[] tabType = m.toString().split(" ");
                    type = tabType[1];
                }else{
                    String[] tabType = type.split("\\.");
                    type = tabType[tabType.length - 1];
                }

                // on créer une méthode
                String access = "", definition = "";
                if (Modifier.isPublic(m.getModifiers())) {
                    access = "+";
                } else if (Modifier.isPrivate(m.getModifiers())) {
                    access = "-";
                } else if (Modifier.isProtected(m.getModifiers())) {
                    access = "=";
                }
                // ajout de la définition de la méthode
                if (Modifier.isAbstract(m.getModifiers()) && !this.type.equals("interface")) {
                    definition += "abstract ";
                }
                if(Modifier.isFinal(m.getModifiers())){
                    definition += "final ";
                }
                if(Modifier.isStatic(m.getModifiers())){
                    definition += "static";
                }
                // lecture et ajout des paramètres des méthodes
                ArrayList<String> parametres = new ArrayList<>();
                for(Parameter parametre : m.getParameters()){
                    String typeParametre = parametre.getType().toString();
                    String[] tabTypeParametre = typeParametre.split("\\.");
                    typeParametre = tabTypeParametre[tabTypeParametre.length - 1];
                    parametres.add(typeParametre);
                }
                this.compositionClasses.add(new Methodes(access, m.getName(), type, definition, parametres));
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

    /**
     * méthode ajouterInterface
     *
     * @param i
     */
    public void ajouterInterface(String i) {
        this.interfaces.add(i);
    }


    public String toString(String debut) {
        String res = debut + this.getNom() + "\n";
        return res;
    }

    public void afficher() {
        for (CompositionClasse c : this.compositionClasses) {
            System.out.println(c);
        }
    }

    /**
     * getter
     */
    public ArrayList<CompositionClasse> getCompositionClasses() {
        return compositionClasses;
    }

    public String getSuperClasse() {
        return this.superClasse;
    }

    public String getPackageClasse(){
        return this.packageClasse;
    }

    /**
     * ajouterCompositionClasse
     *
     * @param c
     */
    public void ajouterCompositionClasse(CompositionClasse c) {
        this.compositionClasses.add(c);
    }

    /**
     * méthode getTypes
     *
     * @return type String
     */
    public String getType() {
        return this.type;
    }

    /**
     * méthode suppressionCompositionClasse
     * cette méthode permet de supprimer une composition de classe à partir du nom de la composition
     * @param nom String
     */
    public void suppressionCompositionClasse(String nom) {
        for (CompositionClasse c : this.compositionClasses) {
            if (c.getNom().equals(nom)) {
                this.compositionClasses.remove(c);
                break;
            }
        }
    }

    /**
     * méthode getMoyValue
     */
    public MoyValue getMoyValue() {
        return this.moyValue;
    }
}
