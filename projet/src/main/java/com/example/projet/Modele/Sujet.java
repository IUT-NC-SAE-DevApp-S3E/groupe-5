package com.example.projet.Modele;

import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.net.MalformedURLException;
import java.util.ArrayList;

public interface Sujet {
    public void enregistrerObservateur(Observateur o);

    public void supprimerObservateur(Observateur o);

    public void notifierObservateur() throws MalformedURLException;

    public void setListeFichiers(ArrayList<Fichier> listeFichiers);

    public void ajouterFichier(Fichier f);

    public ArrayList<Fichier> getListeFichiers();

    public String getCheminArborescence();

    public void setChemin(String res);
}
