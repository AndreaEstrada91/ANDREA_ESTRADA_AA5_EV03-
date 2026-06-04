package controller;

import config.Conexion;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * EVIDENCIA SENA: GA7-220501096-AA5-EV01
 * DISEÑO Y DESARROLLO DE SERVICIOS WEB - CASO AUTENTICACIÓN
 * * Componente encargado de exponer los endpoints de la API REST
 * para gestionar el Registro de usuarios y el Inicio de sesión.
 */
@WebServlet("/api/auth/*") // Enrutamiento URL base para el servicio web
public class AuthServlet extends HttpServlet {

    /**
     * Método POST encargado de procesar las peticiones de autenticación.
     * Recibe parámetros HTTP, interactúa con la BD y responde en formato JSON.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. CONFIGURACIÓN DE RESPUESTA: Definir formato JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Cabecera CORS básica para permitir conexiones del Frontend sin bloqueos
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo(); // Captura la subruta (/register o /login)

        // 2. CAPTURA DE DATOS: Leer los parámetros enviados por el cliente
        String usuarioParam = request.getParameter("usuario");
        String passwordParam = request.getParameter("password");

        // 3. VALIDACIÓN DE CAMPOS: Comprobar que la petición traiga la información completa
        if (usuarioParam == null || passwordParam == null || usuarioParam.isEmpty() || passwordParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Código HTTP 400
            out.print("{\"status\": \"error\", \"message\": \"Faltan campos obligatorios: usuario y contraseña.\"}");
            return;
        }

        // =========================================================================
        // CASO A: LÓGICA DE REGISTRO DE NUEVO USUARIO (/api/auth/register)
        // =========================================================================
        if (pathInfo != null && pathInfo.equals("/register")) {
            String sql = "INSERT INTO usuarios (usuario, password) VALUES (?, ?)";
            
            try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, usuarioParam);
                ps.setString(2, passwordParam);
                ps.executeUpdate();
                
                response.setStatus(HttpServletResponse.SC_CREATED); // Código HTTP 201
                out.print("{\"status\": \"success\", \"message\": \"Registro de usuario satisfactorio en el sistema.\"}");
                
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Código HTTP 500
                out.print("{\"status\": \"error\", \"message\": \"El usuario ya existe o error interno: " + e.getMessage() + "\"}");
            }
        } 
        
        // =========================================================================
        // CASO B: LÓGICA DE INICIO DE SESIÓN / LOGIN (/api/auth/login)
        // REQUISITO CENTRAL DE LA GUÍA DE APRENDIZAJE
        // =========================================================================
        else if (pathInfo != null && pathInfo.equals("/login")) {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
            
            try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, usuarioParam);
                ps.setString(2, passwordParam);
                
                ResultSet rs = ps.executeQuery();
                
                // Si la consulta devuelve una fila, significa que las credenciales coinciden
                if (rs.next()) {
                    // LINEAMIENTO: Mensaje de autenticación satisfactoria
                    response.setStatus(HttpServletResponse.SC_OK); // Código HTTP 200
                    out.print("{\"status\": \"success\", \"message\": \"Autenticación satisfactoria. Acceso concedido.\"}");
                } else {
                    // LINEAMIENTO: Devolver error en la autenticación en caso contrario
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código HTTP 401
                    out.print("{\"status\": \"error\", \"message\": \"Error en la autenticación. Usuario o contraseña incorrectos.\"}");
                }
                
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\": \"error\", \"message\": \"Error de conexión en el servidor: " + e.getMessage() + "\"}");
            }
        } 
        
        // CASO C: Enrutamiento inválido
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Código HTTP 404
            out.print("{\"status\": \"error\", \"message\": \"Servicio web solicitado no existe.\"}");
        }
    }
}