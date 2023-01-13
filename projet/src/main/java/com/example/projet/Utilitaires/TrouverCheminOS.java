package com.example.projet.Utilitaires;

/**
 * Classe TrouverCheminOS qui permet de trouver initial
 */
public class TrouverCheminOS {

    /**
     * Méthode permettant de déterminer le chemin courant du user connecté afin de déterminer son url
     * @return
     */
    public static String getChemin() {
        // On récupère le système d'exploitation courant
        String os = System.getProperty("os.name").toLowerCase();
        // On récupère le nom d'utilisateur courant
        String user = System.getProperty("user.name");
        String chemin = "";
        if (os.contains("win")) {
            chemin = "C:\\Users\\" + user;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            chemin = "/home/" + user;
        } else if (os.contains("mac")) {
            chemin = "/Users/" + user;
        }
        return chemin;
    }
}
