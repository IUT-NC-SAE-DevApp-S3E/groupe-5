package com.example.projet.Modele;

import com.example.projet.CompositionClasse.CompositionClasse;
import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.TrouverCheminOS;
import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Classe Modele qui permet de gerer le modele
 */
public class Modele implements Sujet {

    /**
     * Le boolean clear permet de savoir si il faut effacer le contenu du diagramme
     * si il est a true lorsque l'on va notifier les observateurs
     * ils vont effacer le contenu du diagramme
     */
    private boolean clear = false;

    /**
     * liste des observateurs
     */
    private ArrayList<Observateur> listeObservateurs = new ArrayList<>();

    /**
     * liste des fichiers
     */
    private ArrayList<Classe> listeFichiers = new ArrayList<>();

    /**
     * chemin de l'arborescence
     */
    private String cheminArborescence = TrouverCheminOS.getChemin();

    /**
     * position en X et Y pour placer les classes sur l'inferface graphique
     */
    private int startX = 0;
    private int startY = 0;

    /**
     * methode enregistrerObservateur qui permet d'enregistrer un observateur
     *
     * @param o l'observateur
     */
    @Override
    public void enregistrerObservateur(Observateur o) {
        this.listeObservateurs.add(o);
    }

    /**
     * methode supprimerObservateur qui permet de supprimer un observateur
     *
     * @param o l'observateur
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        this.listeObservateurs.remove(o);
    }

    /**
     * methode notifierObservateur qui permet de notifier tous les observateurs
     */
    @Override
    public void notifierObservateur() {
        chercherParentsEtInterface();
        for (Observateur o : listeObservateurs) {
            o.actualiser(this);
        }
    }

    /**
     * GETTER ET SETTER DE LISTEFICHIERS
     */
    public ArrayList<Classe> getListeFichiers() {
        return this.listeFichiers;
    }

    public void setListeFichiers(ArrayList<Classe> listeFichiers) {
        this.listeFichiers = listeFichiers;
    }

    public void ajouterFichier(Classe fichier) {
        this.listeFichiers.add(fichier);
    }

    public ArrayList<Observateur> getListeObservateurs() {
        return listeObservateurs;
    }

    public String getCheminArborescence() {
        return this.cheminArborescence;
    }

    public void setChemin(String res) {
        this.cheminArborescence = res;
    }

    /**
     * @return the clear
     */
    public boolean getClear() {
        return clear;
    }

    /**
     * @param clear
     */
    public void setClear(boolean clear) {
        this.clear = clear;
    }

    /**
     * méthode clear qui permet de vider la liste des fichiers
     */
    public void clearFichier() {
        this.listeFichiers = new ArrayList<>();
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartX(int x) {
        this.startX = x;
    }

    public void setStartY(int y) {
        this.startY = y;
    }

    public void chercherParentsEtInterface() {
        for (Classe c : this.listeFichiers) {
            if (c.getSuperClasse() != null) {
                for (Classe c2 : this.listeFichiers) {
                    if (c2.getNom().equals(c.getSuperClasse())) {
                        c.setSuperClasse(c2.getNom());
                    }
                }
            }
            if (c.getInterfaces() != null) {
                for (Classe c2 : this.listeFichiers) {
                    for (int i = 0; i < c.getInterfaces().size(); i++) {
                        if (c2.getNom().equals(c.getInterfaces().get(i))) {
                            c.getInterfaces().set(i, c2.getNom());
                        }
                    }
                }
            }
        }
    }

    /**
     * méthode supprimerFichier qui permet de supprimer un fichier de la liste des fichiers
     *
     * @param f le fichier a supprimer
     */
    public void supprimerFichier(Fichier f) {
        this.listeFichiers.remove(f);
    }
}
