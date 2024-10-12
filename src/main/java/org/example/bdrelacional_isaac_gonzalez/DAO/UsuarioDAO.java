package org.example.bdrelacional_isaac_gonzalez.DAO;

import org.example.bdrelacional_isaac_gonzalez.domain.Usuario;
import org.example.bdrelacional_isaac_gonzalez.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UsuarioDAO {

    private Connection conexion;

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

    public boolean validarUsuario(Usuario usuario) throws SQLException{
        List<Usuario> listaUsuarios = obtenerUsuarios();
        boolean existe = false;
        for (Usuario usuario1 : listaUsuarios){
            if (usuario1.getUsuario().equals(usuario.getUsuario()) && usuario1.getPass().equals(usuario.getPass())){
                existe = true;
                break;
            }
        }
        return existe;
    }

    public List<Usuario> obtenerUsuarios() throws SQLException{
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(resultado.getString(1));
            usuario.setPass(resultado.getString(2));
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
