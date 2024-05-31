/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import practicalogin.ConexionBDD;
import practicalogin.Libros;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cgale
 */
public class BusquedaLibrosController implements Initializable {
    
    Connection conn;
    
    public BusquedaLibrosController(){
        conn = ConexionBDD.getConnection();
    }

    @FXML
    private Label lblGestiondeLibros;
    @FXML
    private TableView<Libros> tblTabla;
    @FXML
    private TableColumn<Libros, String> colIsbn;
    @FXML
    private TableColumn<Libros, String> colTitulo;
    @FXML
    private TableColumn<Libros, String> colAutor;
    @FXML
    private TableColumn<Libros, String> colEditorial;
    @FXML
    private TableColumn<Libros, Integer> colAnio;
    @FXML
    private TableColumn<Libros, String> colGenero;
    @FXML
    private TableColumn<Libros, Integer> colDisponibles;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnVolver;
    
    private ObservableList<Libros> listaLibros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listaLibros = FXCollections.observableArrayList();
        tblTabla.setItems(listaLibros);
        
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libros, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libros, String>("titulo_libro"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libros, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libros, String>("editorial"));
        colAnio.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("anio_de_publicacion"));
        colGenero.setCellValueFactory(new PropertyValueFactory<Libros, String>("genero_libro"));
        colDisponibles.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("cantidad_disponible"));
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
    }

    /*METODO QUE NOS PERMITE MOLDEAR EL COMPORTAMIENTO DE LOS BOTONES
            EN EL CONTROLLER DE LA INTERFAZ DE BUSQUEDA DE LIBROS*/
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // IMPLEMENTACION DEL BOTON BUSCAR
        if(event.getSource() == btnBuscar){
        buscarLibro();
        }
        // IMPLEMENTACION DEL BOTON VOLVER
        else if(event.getSource() == btnVolver){
            Parent root = FXMLLoader.load(getClass().getResource("/view/ViewPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
    }
          // METODO QUE PERMITE LA BUSQUEDA DEL LIBRO DENTRO DE LA BASE DE DATOS
    public void buscarLibro() {
        String query = "SELECT * FROM Libro WHERE isbn = ? OR titulo_libro = ?";
        try (Connection conn = ConexionBDD.getConnection(); PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, txtBuscar.getText());
            st.setString(2, txtBuscar.getText());
            ResultSet rs = st.executeQuery();

            listaLibros.clear();
            while (rs.next()) {
                listaLibros.add(new Libros(
                    rs.getString("isbn"),
                    rs.getString("titulo_libro"),
                    rs.getString("autor"),
                    rs.getString("editorial"),
                    rs.getInt("anio_de_publicacion"),
                    rs.getString("genero_libro"),
                    rs.getInt("cantidad_disponible")
                ));
            }
        limpiar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // METODO QUE PERMITE LA LIMPIEZA DEL TEXTFIELD PARA LA SIGUIENTE BUSQUEDA
    public void limpiar(){
    txtBuscar.setText("");
    }
    
}
