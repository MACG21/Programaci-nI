/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicalogin;

import com.sun.jdi.connect.spi.Connection;

/**
 *
 * @author cgale
 */
public class Usuarios {
    
    //Conexion a base de dtos
    Connection conn;
        public Usuarios(){
        conn = (Connection) ConexionBDD.getConnection();
    }
        
    //APLICACION DEL PRINCIPIO DE ABSTRACCION DE POO 
        // APLICACION DEL PRINCIPIO DE ENCAPSULAMIENTO
    private String id_usuario;
    private String nombre_usuario;
    private String usuario;
    private String password_usuario;
    private String direccion_usuario;
    private Integer telefono_usuario;
    private String dpi_usuario;
    
    
    //CONSTRUCTOR DE LOS ATRIBUTOS DE LA CLASE
    public Usuarios(String id_usuario, String nombre_usuario, String usuario, String password_usuario, String direccion_usuario, Integer telefono_usuario, String dpi_usuario) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.usuario = usuario;
        this.password_usuario = password_usuario;
        this.direccion_usuario = direccion_usuario;
        this.telefono_usuario = telefono_usuario;
        this.dpi_usuario = dpi_usuario;
    }
    
    //GETTERS DE LOS ATRIBUTOS QUE NOS PERMITEN ACCEDER A ELLOS PARA IMPLEMENTARLOS EN OTRAS CLASES
    public String getId_usuario() {
        return id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword_usuario() {
        return password_usuario;
    }

    public String getDireccion_usuario() {
        return direccion_usuario;
    }

    public Integer getTelefono_usuario() {
        return telefono_usuario;
    }

    public String getDpi_usuario() {
        return dpi_usuario;
    }
    
    
        
        
}
