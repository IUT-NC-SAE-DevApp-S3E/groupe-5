import com.example.projet.Utilitaires.Classe;
import com.example.projet.Utilitaires.Dossier;
import com.example.projet.Utilitaires.LectureFichier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testIntrospection
{

    private Class<?> classeSansPackage1;
    private Class<?> classeSansPackage2;

    private Class<?> classeAvecPackage1;

    private Class<?> classeAvecPackage2;

    private Dossier dossierClasseAvecPackage;

    @BeforeEach
    public void setUp()
    {
        this.classeSansPackage1 = LectureFichier.lectureFichier("/Users/arthur/Documents/GitHub/groupe-5/projet/test/ressources/AdresseIp.class", "AdresseIp");
        this.classeSansPackage2 = LectureFichier.lectureFichier("/Users/arthur/Documents/GitHub/groupe-5/projet/test/ressources/AnnuaireComplexe.class", "AnnuaireComplexe");

        this.classeAvecPackage1 = LectureFichier.lectureFichier("/Users/arthur/Documents/GitHub/groupe-5/projet/test/ressources/cafe/Boisson.class", "Boisson");
        this.classeAvecPackage2 = LectureFichier.lectureFichier("/Users/arthur/Documents/GitHub/groupe-5/projet/test/ressources/cafe/BoissonChantilly.class", "BoissonChantilly");

        this.dossierClasseAvecPackage = new Dossier("/Users/arthur/Documents/GitHub/groupe-5/projet/test/ressources/cafe", "Cafe");
    }

    @Test
    public void testIntrospection1fichierSansPackage() {

        assertEquals(this.classeSansPackage1.getName(), "AdresseIp");
        assertEquals(this.classeSansPackage2.getName(), "AnnuaireComplexe");
    }

    @Test
    public void testIntrospection1fichierAvecPackage() {

        assertEquals(this.classeAvecPackage1.getName(), "cafe.Boisson");
        assertEquals(this.classeAvecPackage2.getName(), "cafe.BoissonChantilly");
    }

    @Test
    public void testIntrospectionAvecDossier() throws IOException {
        this.dossierClasseAvecPackage.lectureDossier();
        assertEquals(this.dossierClasseAvecPackage.getClasse().size(), 8);
    }

    @Test
    public void testRecupererSuperClasse()
    {
        assertEquals(this.classeAvecPackage1.getSuperclass().getName(), "java.lang.Object");
        assertEquals(this.classeAvecPackage2.getSuperclass().getName(), "cafe.DecorateurIngredient");
        assertEquals(this.classeSansPackage1.getSuperclass().getName(), "java.lang.Object");
        assertEquals(this.classeSansPackage2.getSuperclass().getName(), "java.util.HashMap");
    }

    @Test
    public void testRecupererInterfaces()
    {
        assertEquals(this.classeAvecPackage1.getInterfaces().length, 0);
        assertEquals(this.classeAvecPackage2.getInterfaces().length, 0);
        assertEquals(this.classeSansPackage1.getInterfaces().length, 1);
        assertEquals(this.classeSansPackage2.getInterfaces().length, 0);
    }

    @Test
    public void testRecupererConstructeurs()
    {
        assertEquals(this.classeAvecPackage1.getConstructors().length, 1);
        assertEquals(this.classeAvecPackage2.getConstructors().length, 1);
        assertEquals(this.classeSansPackage1.getConstructors().length, 1);
        assertEquals(this.classeSansPackage2.getConstructors().length, 1);
    }

    @Test
    public void testRecupererAttributs()
    {
        assertEquals(this.classeAvecPackage1.getDeclaredFields().length, 1);
        assertEquals(this.classeAvecPackage2.getDeclaredFields().length, 0);
        assertEquals(this.classeSansPackage1.getDeclaredFields().length, 4);
        assertEquals(this.classeSansPackage2.getDeclaredFields().length, 0);
    }

    @Test
    public void testRecupererMethodes() {
        assertEquals(this.classeAvecPackage1.getDeclaredMethods().length, 3);
        assertEquals(this.classeAvecPackage2.getDeclaredMethods().length, 0);
        assertEquals(this.classeSansPackage1.getDeclaredMethods().length, 7);
    }




}
