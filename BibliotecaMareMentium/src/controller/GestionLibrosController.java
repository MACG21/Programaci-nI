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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import practicalogin.Libros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static javax.swing.UIManager.getInt;
import practicalogin.ConexionBDD;

/**
 * FXML Controller class
 *
 * @author cgale
 */
public class GestionLibrosController implements Initializable {

    Connection conn;
    
    public GestionLibrosController(){
        conn = ConexionBDD.getConnection();
    }
    
    @FXML
    private Label lblIsbn;
    @FXML
    private TextField txtIsbn;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private TextField txtAutor;
    @FXML
    private Label lblEditorial;
    @FXML
    private TextField txtEditorial;
    @FXML
    private Label lblAnio;
    @FXML
    private TextField txtAnio;
    @FXML
    private Label lblGenero;
    @FXML
    private TextField txtGenero;
    @FXML
    private Label lblCantidad;
    @FXML
    private TextField txtCantidad;
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
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnLimpiar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            mostrarLibros();
        } catch (SQLException ex) {
            Logger.getLogger(GestionLibrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    /*METODO QUE NOS PERMITE MOLDEAR EL COMPORTAMIENTO DE LOS BOTONES
            EN EL CONTROLLER DE LA INTERFAZ DE GESTION DE LIBROS*/
    @FXML
        private void handleButtonAction(ActionEvent handleButtonAction) throws SQLException, IOException {
        //Implementacion del botón agregar, haciendo uso del metodo agregar registro que hace la inserción en la Base de datos
        if(handleButtonAction.getSource() == btnAgregar){
            agregarRegistro();
        }
        //Implementacion del boton volver, devolviendo a la vista principal del usuario administrador
        else if(handleButtonAction.getSource()== btnVolver){
            Parent root = FXMLLoader.load(getClass().getResource("/view/ViewPrincipal.fxml"));
            Stage stage = (Stage) ((Node) handleButtonAction.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            }
        //implementacion del metodo limpiar con el boton limpiar
        else if(handleButtonAction.getSource() == btnLimpiar){
            limpiarTextFiel();
        }
        //impleentacion del metodo actualizar con el boton actualizar 
        else if (handleButtonAction.getSource() == btnActualizar){
        actualizar();
        }
        //implementacion del metodo eliminar con el boton elimanar
        else if (handleButtonAction.getSource() == btnEliminar){
        eliminarRegistro();
        }    
    }
    
    //METODO QUE PERMITE LA VISUALIZACION DE LOS DATOS 
    public ObservableList<Libros> getLibrosList() throws SQLException{
        ArrayList<Libros> listaLibros = new ArrayList<>();
        ObservableList<Libros> librosLista = FXCollections.observableArrayList(listaLibros);
        conn = ConexionBDD.getConnection();
        String query = "SELECT * FROM libro";
        Statement st;
        ResultSet rs;
        
        try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          Libros libro;
          while(rs.next()){
            libro = new Libros(rs.getString("isbn"), rs.getString("titulo_libro"), rs.getString("autor"), rs.getString("editorial"), rs.getInt("anio_de_publicacion") , rs.getString("genero_libro"), rs.getInt("cantidad_disponible"));
            librosLista.add(libro);
          }
        }catch(Exception ex){
        ex.printStackTrace();
        }
        return librosLista;
    }
    //Metodo que nos permite mostrar los libros en la tabla de la interfaz
    public void mostrarLibros() throws SQLException{
        conn = ConexionBDD.getConnection();
        
        ObservableList<Libros> lista = getLibrosList();
        
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libros, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libros, String>("titulo_libro"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libros, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libros, String>("editorial"));
        colAnio.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("anio_de_publicacion"));
        colGenero.setCellValueFactory(new PropertyValueFactory<Libros, String>("genero_libro"));
        colDisponibles.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("cantidad_disponible"));
        
        tblTabla.setItems(lista);
    }
    //Metodo que nos permite hacer la inserción de nuevos registros en la base de datos
    public void agregarRegistro() throws SQLException {
    // Crear un Vector para almacenar los datos
    Vector<Object> datosLibro = new Vector<>();
    
    // Agregar los datos al Vector
    datosLibro.add(txtIsbn.getText());
    datosLibro.add(txtTitulo.getText());
    datosLibro.add(txtAutor.getText());
    datosLibro.add(txtEditorial.getText());
    datosLibro.add(Integer.parseInt(txtAnio.getText()));
    datosLibro.add(txtGenero.getText());
    datosLibro.add(Integer.parseInt(txtCantidad.getText()));

    String query = "INSERT INTO Libro(isbn, titulo_libro, autor, editorial, anio_de_publicacion, genero_libro, cantidad_disponible) VALUES (?,?,?,?,?,?,?)";

    try (Connection conn = ConexionBDD.getConnection(); PreparedStatement stNuevo = conn.prepareStatement(query)) {
        // Establecer los valores en el PreparedStatement desde el Vector
        stNuevo.setString(1, (String) datosLibro.get(0));
        stNuevo.setString(2, (String) datosLibro.get(1));
        stNuevo.setString(3, (String) datosLibro.get(2));
        stNuevo.setString(4, (String) datosLibro.get(3));
        stNuevo.setInt(5, (Integer) datosLibro.get(4));
        stNuevo.setString(6, (String) datosLibro.get(5));
        stNuevo.setInt(7, (Integer) datosLibro.get(6));

        stNuevo.executeUpdate();
        mostrarLibros();
        limpiarTextFiel();

    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    executeQuery(query);
}
    //Metodo que permite limpiar los campos 
    private void limpiarTextFiel(){
    txtIsbn.setText("");
    txtTitulo.setText("");
    txtAutor.setText("");
    txtEditorial.setText("");
    txtAnio.setText("");
    txtGenero.setText("");
    txtCantidad.setText("");
    }
    //Metodo que permite la actualizacion de los regitros
    private void actualizar() throws SQLException{
        String query = "UPDATE Libro SET titulo_libro = '" + txtTitulo.getText() + "', autor = '" + txtAutor.getText() + 
                "', editorial = '" + txtEditorial.getText() + "', anio_de_publicacion = " + txtAnio.getText() + 
                ", genero_libro = '" + txtGenero.getText() + "', cantidad_disponible = " + txtCantidad.getText() +
                    " WHERE isbn = '" + txtIsbn.getText() + "'";
        
        executeQuery(query);
        mostrarLibros();
    }
    //Metodo para eliminar registros
    private void eliminarRegistro() throws SQLException{
    String query = "DELETE FROM Libro WHERE isbn = '" + txtIsbn.getText() + "'";
    executeQuery(query);
    mostrarLibros();
    }

    //METODO QUE PERMITE LA CONEXION A BASE DE DATOS PARA LAS SOLICITUDES DE CRUD
    private void executeQuery(String query) {
        conn = ConexionBDD.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
            
        }catch(Exception ex){
        ex.printStackTrace();
        }
    }

    @FXML //METODO QUE PERMITE SELECCIONAR UN REGISTRO Y VISUALIZARLO EN LOS TEXT FIELD
    private void handleMouseAction(MouseEvent event) {
       Libros libro =  tblTabla.getSelectionModel().getSelectedItem();
       txtIsbn.setText(libro.getIsbn());
       txtTitulo.setText(libro.getTitulo_libro());
       txtAutor.setText(libro.getAutor());
       txtEditorial.setText(libro.getEditorial());
       txtAnio.setText("" + libro.getAnio_de_publicacion());
       txtGenero.setText(libro.getGenero_libro());
       txtCantidad.setText("" + libro.getCantidad_disponible());     
    }
    
}