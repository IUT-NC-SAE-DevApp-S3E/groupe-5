package com.example.projet.CompositionClasse;

/**
 * Classe Attributs qui permet de gerer les attributs d'une classe
 */
public class Attributs extends CompositionClasse {

    /**
     * definition de l'attribut définition (final, static, etc...)
     */
    private String definition;

    /**
     * Constructeur de la classe Attributs qui initialise les attributs
     *
     * @param acces      l'acces de l'attribut
     * @param nom        le nom de l'attribut
     * @param type       le type de l'attribut
     * @param definition la definition de l'attribut
     */
    public Attributs(String acces, String nom, String type, String definition) {
        super(acces, nom, type);
        this.definition = definition;
    }

    /**
     * GETTER DES ATTRIBUTS
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * SETTER DES ATTRIBUTS
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /*
    public void setTypeAttribut(String typeAttribut) {
        this.typeAttribut = typeAttribut;
    }
     */

    /**
     * methode toString qui permet d'afficher l'attribut sous forme de chaine de caractere
     * @return la chaine de caractere
     */
    @Override
    public String toString() {
        String res = this.getAcces() +" " + this.getDefinition() + " " + this.getType() + " " + this.getNom();
        return res;
    }

    /**
     * méthode getSqueletteJava
     * retourne sous forme de text l'attribut comme il serait écrit en java
     */
    public String getSqueletteJava() {
        String acces = "public";
        if (this.getAcces().equals("-")) {
            acces = "private";
        } else if (this.getAcces().equals("#")){
            acces = "protected";
        }
        String res = acces + " " + this.getType() + " " + this.getNom() + ";";
        return res;
    }

}

