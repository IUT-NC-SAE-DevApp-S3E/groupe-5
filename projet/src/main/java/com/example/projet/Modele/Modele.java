package com.example.projet.Modele;

import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.TrouverCheminOS;
import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Modele implements Sujet {

    /**
     * Le boolean clear permet de savoir si il faut effacer le contenu du diagramme
     * si il est a true lorsque l'on va notifier les observateurs
     * ils vont effacer le contenu du diagramme
     */
    private boolean clear = false;
    private ArrayList<Observateur> listeObservateurs = new ArrayList<>();

    private ArrayList<Classe> listeFichiers = new ArrayList<>();

    private String cheminArborescence = TrouverCheminOS.getChemin();

    @Override
    public void enregistrerObservateur(Observateur o) {
        this.listeObservateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        this.listeObservateurs.remove(o);
    }

    @Override
    public void notifierObservateur() {
        for (Observateur o : listeObservateurs) {
            o.actualiser(this);
        }
    }

    // Getters and Setters
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
}
