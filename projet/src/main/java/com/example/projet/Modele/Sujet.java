package com.example.projet.Modele;

import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.net.MalformedURLException;
import java.util.ArrayList;

public interface Sujet {
    public void enregistrerObservateur(Observateur o);

    public void supprimerObservateur(Observateur o);

    public void notifierObservateur() throws MalformedURLException;

    public void setListeFichiers(ArrayList<Classe> listeFichiers);

    public void ajouterFichier(Classe f);

    public ArrayList<Classe> getListeFichiers();

    public String getCheminArborescence();

    public void setChemin(String res);

    public void supprimerFichier(Fichier f);

    public void setClear(boolean clear);

    public boolean getClear();

    public void clearFichier();

    public int getStartX();

    public int getStartY();

    public void setStartX(int x);

    public void setStartY(int y);

}
