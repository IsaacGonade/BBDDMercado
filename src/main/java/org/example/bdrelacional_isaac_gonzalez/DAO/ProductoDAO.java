package org.example.bdrelacional_isaac_gonzalez.DAO;

import org.example.bdrelacional_isaac_gonzalez.domain.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDAO {

    public static boolean guardarUsuario(Connection con, Producto producto) throws SQLException {
        try {
            String sql = "INSERT INTO Productos (email, contraseña) VALUES (?, ?)";

            PreparedStatement sentencia = con.prepareStatement(sql);
            //sentencia.setString(1, producto.getEmail());
            //sentencia.setString(2, producto.getContraseña());

            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block

            System.out.println(e.getMessage());
            return false;


        }
    }
}
