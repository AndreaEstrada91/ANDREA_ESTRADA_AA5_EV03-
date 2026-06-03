import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.ProductoDAO;
import modelo.Producto;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String nombre = request.getParameter("nombre");
            String categoria = request.getParameter("categoria");
            String precioStr = request.getParameter("precio");
            String stockStr = request.getParameter("stock");

            if (nombre == null || categoria == null || precioStr == null || stockStr == null ||
                nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\": \"error\", \"message\": \"Faltan campos obligatorios en el formulario\"}");
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setCategoria(categoria);
            nuevoProducto.setPrecio(precio);
            nuevoProducto.setStock(stock);

            ProductoDAO productoDAO = new ProductoDAO();
            boolean guardadoExitoso = productoDAO.insertar(nuevoProducto);

            if (guardadoExitoso) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"status\": \"success\", \"message\": \"Artesanía registrada exitosamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\": \"error\", \"message\": \"No se pudo guardar en la base de datos\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\": \"error\", \"message\": \"Precio y stock deben ser numéricos\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\": \"error\", \"message\": \"Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}