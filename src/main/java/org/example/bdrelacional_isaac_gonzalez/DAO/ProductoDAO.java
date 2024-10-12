package org.example.bdrelacional_isaac_gonzalez.DAO;

import org.example.bdrelacional_isaac_gonzalez.clases.ConexionBBDD;
import org.example.bdrelacional_isaac_gonzalez.domain.Producto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public void guardarProducto(Producto producto) throws SQLException, IOException, ClassNotFoundException {
        Connection con = ConexionBBDD.conectar();
        String sql = "INSERT INTO productos (precio, marca, nombre, tipo) VALUES (?, ?, ?, ?)";

        if (con != null){
           PreparedStatement sentencia = con.prepareStatement(sql);
           sentencia.setInt(1, producto.getPrecio());
           sentencia.setString(2, producto.getMarca());
           sentencia.setString(2, producto.getNombre());
           sentencia.setString(2, producto.getTipo());
           sentencia.executeUpdate();
        }
    }

    public void eliminarProducto(Producto producto) throws SQLException, IOException, ClassNotFoundException {
        Connection con = ConexionBBDD.conectar();
        String sql = "DELETE FROM productos WHERE nombre = ?";

        if (con != null){
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, producto.getNombre());
            sentencia.executeUpdate();
        }
    }

    public void modificarProducto(Producto productoAntiguo, Producto productoNuevo) throws SQLException, IOException, ClassNotFoundException {
        Connection con = ConexionBBDD.conectar();
        String sql = "UPDATE productos SET precio = ?, marca = ?, nombre = ?, tipo = ? WHERE id = ?";

        if (con != null){
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, productoNuevo.getPrecio());
            sentencia.setString(2, productoNuevo.getMarca());
            sentencia.setString(2, productoNuevo.getNombre());
            sentencia.setString(2, productoNuevo.getTipo());
            sentencia.setInt(5, productoAntiguo.getId());
            sentencia.executeUpdate();
        }
    }

    public List<Producto> obtenerProductos() throws SQLException, IOException, ClassNotFoundException {
        Connection con = ConexionBBDD.conectar();
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        if (con != null){
            PreparedStatement sentencia = con.prepareStatement(sql);
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
        }
        return productos;
    }
}
