package com.example.projet.Modele;

import com.example.projet.Utilitaires.TrouverCheminOS;
import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Modele implements Sujet {

    private ArrayList<Observateur> listeObservateurs = new ArrayList<>();

    private ArrayList<Fichier> listeFichiers = new ArrayList<>();

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
    public ArrayList<Fichier> getListeFichiers() {
        return listeFichiers;
    }

    public void setListeFichiers(ArrayList<Fichier> listeFichiers) {
        this.listeFichiers = listeFichiers;
    }

    public void ajouterFichier(Fichier fichier) {
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
}
