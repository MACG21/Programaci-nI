/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    
package practicalogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cgale
 */
public class Libros {
    
    //Coneccion a BASE DE DATOS
    Connection conn;    
    public Libros() {
        conn = ConexionBDD.getConnection();
    }
    
    //DECLARACION DE VARIABLES Y APLICACION DEL PRINCIPIO DE ABSTRACCION
    // APLICACION DEL PRINCIPIO DE ENCAPSULAMIENTO
    private String isbn;
    private String titulo_libro;
    private String autor;
    private String editorial;
    private int anio_de_publicacion;
    private String genero_libro;
    private int cantidad_disponible;

    
    //CONSTRUCTOR DE LOS ATRIBUTOS DE LA CLASE
    public Libros(String isbn, String titulo_libro, String autor, String editorial, int anio_de_publicacion, String genero_libro, int cantidad_disponible) {
        this.isbn = isbn;
        this.titulo_libro = titulo_libro;
        this.autor = autor;
        this.editorial = editorial;
        this.anio_de_publicacion = anio_de_publicacion;
        this.genero_libro = genero_libro;
        this.cantidad_disponible = cantidad_disponible;
    }
    
    //GETTERS DE LOS ATRIBUTOS QUE NOS PERMITEN ACCEDER A ELLOS PARA IMPLEMENTARLOS EN OTRAS CLASES
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo_libro() {
        return titulo_libro;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnio_de_publicacion() {
        return anio_de_publicacion;
    }

    public String getGenero_libro() {
        return genero_libro;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }
    
    
    
}
