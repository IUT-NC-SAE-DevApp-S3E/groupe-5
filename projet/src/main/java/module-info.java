module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
    exports com.example.projet.Modele;
    opens com.example.projet.Modele to javafx.fxml;
    exports com.example.projet.Controleur;
    opens com.example.projet.Controleur to javafx.fxml;
}