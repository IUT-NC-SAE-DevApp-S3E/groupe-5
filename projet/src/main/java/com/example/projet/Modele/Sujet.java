package com.example.projet.Modele;

import com.example.projet.Controleur.Observateur;

public interface Sujet
{
    public void enregistrerObservateur(Observateur o);
    public void supprimerObservateur(Observateur o);

    public void notifierObservateur();
}
