package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/artechsanias_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Timonypumba2019."; 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ ¡Conexión exitosa a MySQL!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error conectando a la BD: " + e.getMessage());
        }
        return conexion;
    }
}
