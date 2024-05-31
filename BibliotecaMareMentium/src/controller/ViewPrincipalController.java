/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cgale
 */
public class ViewPrincipalController implements Initializable {

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnGestionDeUsuario;
    @FXML
    private Button btnGestionDeLibros;
    @FXML
    private Button btnBusquedaLibros;
    @FXML
    private Button btnGestionMultas;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /* METODO QUE NOS PERMITE MOLDEAR EL COMPORTAMIENTO DE LOS BOTONES
                 EN LA INTERFAZ PRINCIPAL DEL ADMINISTRADOR*/
    @FXML
    private void eventAction(ActionEvent event) {
        try {
            String fxmlFile = "";
            if (event.getSource() == btnGestionDeLibros) {
                fxmlFile = "/view/GestionLibros.fxml";
            } else if (event.getSource() == btnGestionDeUsuario) {
                fxmlFile = "/view/GestionUsuarios.fxml";
            } else if (event.getSource() == btnCerrarSesion) {
                fxmlFile = "/view/ViewLOGIN.fxml";
            } else if (event.getSource() == btnBusquedaLibros) {
                fxmlFile = "/view/BusquedaLibros.fxml";
            }

            if (!fxmlFile.isEmpty()) {
                changeScene(event, fxmlFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
