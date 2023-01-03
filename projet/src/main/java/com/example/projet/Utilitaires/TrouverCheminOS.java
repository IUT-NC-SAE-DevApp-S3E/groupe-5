package com.example.projet.Utilitaires;

public class TrouverCheminOS
{
    public static String getChemin()
    {
        String os = System.getProperty("os.name").toLowerCase();
        String user = System.getProperty("user.name");
        String chemin = "";
        if (os.contains("win"))
        {
            chemin = "C:\\Users\\"+ user;
        }
        else if (os.contains("nix") || os.contains("nux") || os.contains("aix"))
        {
            chemin = "/home/" + user;
        }
        else if (os.contains("mac"))
        {
            chemin = "/Users/" + user;
        }
        return chemin;
    }
}
