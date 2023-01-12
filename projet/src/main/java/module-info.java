module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires plantuml;
    //requires plantuml;


    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
    exports com.example.projet.Utilitaires;
}