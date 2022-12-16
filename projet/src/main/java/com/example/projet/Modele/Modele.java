package com.example.projet.Modele;

import com.example.projet.Controleur.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.util.ArrayList;

public class Modele implements Sujet {

    private ArrayList<Observateur> listeObservateurs;

    private ArrayList<Fichier> listeFichiers;

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
}
