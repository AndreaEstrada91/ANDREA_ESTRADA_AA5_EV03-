import main.java.config.Conexion;
import main.java.dao.ProductoDAO;
import main.java.modelo.Producto;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Módulo ArTechsanías ===");
        
        // TRUCO: Creamos la base de datos por código por si tu Workbench sigue fallando
        try (Connection con = Conexion.conectar(); Statement st = con.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS artechsanias_db");
            st.executeUpdate("USE artechsanias_db");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS productos ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100), "
                    + "categoria VARCHAR(50), "
                    + "precio DOUBLE, "
                    + "stock INT)");
            System.out.println("Estructura de Base de Datos verificada correctamente.");
        } catch (Exception e) {
            System.out.println("Aviso Base Datos: " + e.getMessage());
        }

        ProductoDAO dao = new ProductoDAO();

        // --- PRUEBA 1: INSERCIÓN (Guardar) ---
        Producto nuevo = new Producto(0, "Bolso Crochet Glitter", "Moda", 85000.0, 15);
        if(dao.insertar(nuevo)) {
            System.out.println("[CRUD] Producto insertado con éxito.");
        }

        // --- PRUEBA 2: CONSULTA (Ver) ---
        System.out.println("\n[CRUD] Listado de Productos en Inventario:");
        List<Producto> productos = dao.listar();
        for (Producto p : productos) {
            System.out.println("- " + p.getNombre() + " | Cat: " + p.getCategoria() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
        }
    }
}