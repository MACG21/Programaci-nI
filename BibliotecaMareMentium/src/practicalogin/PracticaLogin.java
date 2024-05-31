/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package practicalogin;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cgale
 */


public class PracticaLogin extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getResource("/view/GestionLibros.fxml"));
        stage.setTitle("Aplicación JavaFX");
        stage.setScene(new Scene(root));
        stage.show();*/
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewLOGIN.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        
    }


