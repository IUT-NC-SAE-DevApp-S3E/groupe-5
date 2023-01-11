package com.example.projet.Modele;

import com.example.projet.Utilitaires.Classe;
import com.example.projet.Vue.Observateur;
import com.example.projet.Utilitaires.Fichier;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * interface Sujet
 */
public interface Sujet extends Serializable {

    /**
     * methode enregistrerObservateur qui permet d'enregistrer un observateur
     * @param o l'observateur
     */
    public void enregistrerObservateur(Observateur o);

    /**
     * methode supprimerObservateur qui permet de supprimer un observateur
     * @param o l'observateur
     */
    public void supprimerObservateur(Observateur o);

    /**
     * methode notifierObservateur qui permet de notifier tous les observateurs
     * @throws MalformedURLException
     */
    public void notifierObservateur() throws MalformedURLException;

    /**
     * mehode setListeFichiers qui permet de modifier la liste des fichiers
     * @param listeFichiers la liste des fichiers
     */
    public void setListeFichiers(ArrayList<Classe> listeFichiers);

    /**
     * mehtode ajouterFichier qui permet d'ajouter un fichier
     * @param f le fichier
     */
    public void ajouterFichier(Classe f);

    /**
     * methode getListeFichiers qui permet de recuperer la liste des fichiers
     * @return la liste des fichiers
     */
    public ArrayList<Classe> getListeFichiers();

    /**
     * methode getCheminArborescence qui permet de recuperer le chemin de l'arborescence
     * @return le chemin de l'arborescence
     */
    public String getCheminArborescence();

    /**
     * methode setChemin qui permet de modifier le chemin de l'arborescence
     * @param res le chemin de l'arborescence
     */
    public void setChemin(String res);

    /**
     * methode supprimerFichier qui permet de supprimer un fichier
     * @param f le fichier
     */
    public void supprimerFichier(Fichier f);

    /**
     * GETTER ET SETTER DE CLEAR
     */

    public void setClear(boolean clear);

    public boolean getClear();

    public void clearFichier();

    /**
     * GETTER ET SETTER DE START X ET Y
     */

    public int getStartX();

    public int getStartY();

    public void setStartX(int x);

    public void setStartY(int y);

    public void changerAffichage(String type);

    public void inverserAffichage();

    public boolean getTypeMasque(String type);

    public void capturerPane(File f);
}
