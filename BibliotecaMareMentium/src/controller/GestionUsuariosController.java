/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;
import java.io.IOException;
import java.net.URL;
import com.sun.jdi.connect.spi.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import practicalogin.Usuarios;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import practicalogin.ConexionBDD;

/**
 * FXML Controller class
 *
 * @author cgale
 */
public class GestionUsuariosController implements Initializable {
    java.sql.Connection conn;
    
    public GestionUsuariosController(){
        conn = ConexionBDD.getConnection();
    }

    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDpi;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVolver;
    @FXML
    private TableView<Usuarios> tblTablaUsuarios;
    @FXML
    private TableColumn<Usuarios, String> colId;
    @FXML
    private TableColumn<Usuarios, String> colNombre;
    @FXML
    private TableColumn<Usuarios, String> colUsuario;
    @FXML
    private TableColumn<Usuarios, String> colContrasenia;
    @FXML
    private TableColumn<Usuarios, String> colDireccion;
    @FXML
    private TableColumn<Usuarios, Integer> colTelefono;
    @FXML
    private TableColumn<Usuarios, String> colDpi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            mostrarUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /*METODO QUE NOS PERMITE MOLDEAR EL COMPORTAMIENTO DE LOS BOTONES
            EN EL CONTROLLER DE LA INTERFAZ DE GESTION DE USUARIOS*/
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException, IOException {
        //IMPLEMENTACION DEL BOTON AGREGAR, HACIENDO USO DEL METODO CORRESPONDIENTE A ÉL 
        if(event.getSource() == btnAgregar){
            agregarUsuario();
        }
        //IMPLEMENTACION DEL BOTON ELIMINAR, HACIENDO USO DEL METODO CORRESPONDIENTE
        else if(event.getSource() == btnEliminar){
            eliminarUsuario();
        }
        //IMPLEMENTACION DEL BOTON ACTUALIZAR, HACINDO USO DEL METODO CORRESPONDIENTE
        else if(event.getSource() == btnActualizar){
            actualizar();
        }
        else if(event.getSource() == btnLimpiar){
            limpiarTextFiel();
        }
        else if(event.getSource() == btnVolver){
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    public ObservableList<Usuarios> getUsuariosList() throws SQLException{
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        ObservableList<Usuarios> usuariosLista = FXCollections.observableArrayList(listaUsuarios);
        conn = ConexionBDD.getConnection();
        String query = "SELECT * FROM usuarios";
        Statement st;
        ResultSet rs;
        
        try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          Usuarios usuario;
          while(rs.next()){
            usuario = new Usuarios(rs.getString("id_usuario"), rs.getString("nombre_usuario"), rs.getString("usuario"), rs.getString("password_usuario"), rs.getString("direccion_usuario"), rs.getInt("telefono_usuario"), rs.getString("dpi_usuario"));
            usuariosLista.add(usuario);
          }
        }catch(Exception ex){
        ex.printStackTrace();
        }
        return usuariosLista;
    }
    
    //METODO QUE PERMITE LA VISUALIZACON DE LOS USUARIOS EN TABLA
    public void mostrarUsuarios() throws SQLException{
    conn = ConexionBDD.getConnection();
    
    ObservableList<Usuarios> lista = getUsuariosList();
    
    //ASIGNACION A CADA COLUMNA
    colId.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("id_usuario"));
    colNombre.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("nombre_usuario"));
    colUsuario.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("usuario"));
    colContrasenia.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("password_usuario"));
    colDireccion.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("direccion_usuario"));
    colTelefono.setCellValueFactory(new PropertyValueFactory<Usuarios, Integer>("telefono_usuario"));
    colDpi.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("dpi_usuario"));
    
    tblTablaUsuarios.setItems(lista);
    }
    //METODO QUE NOS PERMITE HACER LA INSERCION DE NUEVOS USUARIOS DESDE EL ADMINISTRADOR
    public void agregarUsuario(){
    Vector<Object> datosUsuario = new Vector<>();
    
    //AGREGAR LOS DATOS AL VECTOR
    datosUsuario.add(txtIdUsuario.getText());
    datosUsuario.add(txtNombre.getText());
    datosUsuario.add(txtUsuario.getText());
    datosUsuario.add(txtContraseña.getText());
    datosUsuario.add(txtDireccion.getText());
    datosUsuario.add(Integer.valueOf(txtTelefono.getText()));
    datosUsuario.add(txtDpi.getText());
    
    String query = "INSERT INTO Usuarios(Id_Usuario, Nombre_Usuario, Usuario, Password_Usuario, "
            + "Direccion_Usuario, Telefono_Usuario, DPI_Usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try(java.sql.Connection conn = ConexionBDD.getConnection(); PreparedStatement stNuevo = conn.prepareStatement(query)){
    // ESTABLECER LOS VALORES EN EL PREPAREDSTATEMENT DESDE EL VECTOR
    stNuevo.setString(1, (String) datosUsuario.get(0));
    stNuevo.setString(2, (String) datosUsuario.get(1));
    stNuevo.setString(3, (String) datosUsuario.get(2));
    stNuevo.setString(4, (String) datosUsuario.get(3));
    stNuevo.setString(5, (String) datosUsuario.get(4));
    stNuevo.setInt(6, (Integer) datosUsuario.get(5));
    stNuevo.setString(7, (String) datosUsuario.get(6));
    
    stNuevo.executeUpdate();
    mostrarUsuarios();
    limpiarTextFiel();
    
    }catch(Exception ex){
        ex.printStackTrace();
    }
            executeQuery(query);
    }
    
    //METODO QUE PERMITE ACTUALIZAR LOS REGISTROS
    private void actualizar() throws SQLException{
        String query = "UPDATE Usuarios SET nombre_usuario = '" + txtNombre.getText() + "', usuario = '" + txtUsuario.getText()
                + "', password_usuario = '" + txtContraseña.getText() + "', direccion_usuario = '" + txtDireccion.getText() 
                + "', telefono_usuario = " + txtTelefono.getText() + " WHERE id_usuario = '" + txtIdUsuario.getText() + "'";
        
        executeQuery(query);
        mostrarUsuarios();
    }
    
    //METODO PARA ELIMINAR REGISTROS DE USUARIO
    private void eliminarUsuario() throws SQLException{
    String query = "DELETE FROM Usuarios WHERE id_usuario = '" + txtIdUsuario.getText() + "'";
    executeQuery(query);
    mostrarUsuarios();
    }
    
    //METODO DE EJECUCION EN BASE DE DATOS
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

    //METODO QUE PERMITE LIMPIAR TEXTFIELD
    private void limpiarTextFiel(){
    txtIdUsuario.setText("");
    txtNombre.setText("");
    txtUsuario.setText("");
    txtContraseña.setText("");
    txtDireccion.setText("");
    txtTelefono.setText("");
    txtDpi.setText("");
    }
     
    
    @FXML //METODO QUE PERMITE SELECCIONAR UN REGISTRO Y VISUALIZARLO EN LOS TEXT FIELD
    private void handleMouseAction(MouseEvent event) {
        Usuarios usuario = tblTablaUsuarios.getSelectionModel().getSelectedItem();
        txtIdUsuario.setText(usuario.getId_usuario());
        txtNombre.setText(usuario.getNombre_usuario());
        txtUsuario.setText(usuario.getUsuario());
        txtContraseña.setText(usuario.getPassword_usuario());
        txtDireccion.setText(usuario.getDireccion_usuario());
        txtTelefono.setText("" + usuario.getTelefono_usuario());
        txtDpi.setText(usuario.getDpi_usuario());
    }
    
}
