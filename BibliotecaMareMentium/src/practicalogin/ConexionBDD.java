/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicalogin;

import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author cgale
 */
public class ConexionBDD {
    // Instancia única de la clase
    private static ConexionBDD instancia;
    
    // Objeto Connection para la conexión a la base de datos
    private Connection conexion;
    
    // URL de la base de datos PostgreSQL
    private final String url = "jdbc:postgresql://kala.db.elephantsql.com:5432/rhgiozcx";
    
    // Propiedades para la conexión (usuario y ocntraseña)
    private Properties properties = new Properties();
    
    // Objeto estático de Connection
    private static java.sql.Connection conn = null;

    // Construcctor privado para evitar la instanciación desde fuera de la clase
    private ConexionBDD () {
        // Establecer propiedades de usuario y contraseña
        properties.setProperty("user", "rhgiozcx");
        properties.setProperty("password", "PXFhw7JZXY-0CY9afgumUAFWq08fWHEU");
        
        try {
            // Intentar establecer la conexión a la base de datos
            conn = DriverManager.getConnection(url, properties);
        } catch (SQLException ex) {
            // Manejar cualquier excepción que ocurra durante la conexión
            Logger.getLogger(ConexionBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    //Método estático para obtener la conexión
    public static java.sql.Connection getConnection() {
        // Si la conexión no ha sido creada, crear una nueva instancia de ConexionBDD
        if (conn == null) {
               ConexionBDD c = new ConexionBDD();
               return c.conn;
        }
        else {
            // Si ya existe una conexión, devolverla
            return conn ;
        }
    }
}
