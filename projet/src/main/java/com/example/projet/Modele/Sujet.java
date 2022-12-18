package com.example.projet.Modele;

import com.example.projet.Controleur.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.util.ArrayList;

public interface Sujet {
    public void enregistrerObservateur(Observateur o);

    public void supprimerObservateur(Observateur o);

    public void notifierObservateur();

    void setListeFichiers(ArrayList<Fichier> listeFichiers);
}
