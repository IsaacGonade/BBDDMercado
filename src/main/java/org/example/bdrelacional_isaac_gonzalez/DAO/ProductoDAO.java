package org.example.bdrelacional_isaac_gonzalez.DAO;

import org.example.bdrelacional_isaac_gonzalez.domain.Producto;
import org.example.bdrelacional_isaac_gonzalez.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductoDAO {

    private Connection conexion;
    //creo la conexion y la conecto a la base de datos
    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }
    //metodo para guardar un nuevo producto
    public void guardarProducto(Producto producto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT INTO productos (precio, marca, nombre, tipo) VALUES (?, ?, ?, ?)";
           PreparedStatement sentencia = conexion.prepareStatement(sql);
           sentencia.setInt(1, producto.getPrecio());
           sentencia.setString(2, producto.getMarca());
           sentencia.setString(3, producto.getNombre());
           sentencia.setString(4, producto.getTipo());
           sentencia.executeUpdate();
    }
    //metodo para eliminar
    public void eliminarProducto(Producto producto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "DELETE FROM productos WHERE nombre = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, producto.getNombre());
        sentencia.executeUpdate();
    }
    //metodo para modificar un producto
    public void modificarProducto(Producto productoAntiguo, Producto productoNuevo) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE productos SET precio = ?, marca = ?, nombre = ?, tipo = ? WHERE id = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, productoNuevo.getPrecio());
            sentencia.setString(2, productoNuevo.getMarca());
            sentencia.setString(3, productoNuevo.getNombre());
            sentencia.setString(4, productoNuevo.getTipo());
            sentencia.setInt(5, productoAntiguo.getId());
            sentencia.executeUpdate();
    }

    //metodo para ver los prodcutos en la listView
    public List<Producto> obtenerProductos() throws SQLException, IOException, ClassNotFoundException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setId(resultado.getInt(1));
                producto.setPrecio(resultado.getInt(2));
                producto.setMarca(resultado.getString(3));
                producto.setNombre(resultado.getString(4));
                producto.setTipo(resultado.getString(5));

                productos.add(producto);
            }
        return productos;
    }
}
