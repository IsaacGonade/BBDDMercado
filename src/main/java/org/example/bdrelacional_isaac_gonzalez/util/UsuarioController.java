package org.example.bdrelacional_isaac_gonzalez.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bdrelacional_isaac_gonzalez.DAO.UsuarioDAO;
import org.example.bdrelacional_isaac_gonzalez.domain.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {

    @FXML
    private Button iniciarButton;

    @FXML
    private TextField passTF;

    @FXML
    private TextField usuTF;

    @FXML
    private AnchorPane ventanaUsu;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usuario = new Usuario();

    public void UsuarioController() {
        try {
            usuarioDAO.conectar();
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            Alerts.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            Alerts.mostrarError("Error al cargar la configuración");
        }

        System.out.println(System.getProperty("user.home"));
    }

    @FXML
    void onIniciar(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        try {
            // Verificar que los campos no estén vacíos
            if (usuTF.getText().isEmpty() || passTF.getText().isEmpty()) {
                Alerts.mostrarError("Por favor, completa ambos campos");
            } else {
                usuario.setUsuario(usuTF.getText());
                usuario.setPass(passTF.getText());
                if (usuarioDAO.validarUsuario(usuario)){ //Compruebo si el usuario es administrador del sistema
                    usuarioDAO.desconectar();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(R.getUI("Gestion.fxml"));
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(loader.load()));
                    newStage.sizeToScene();
                    newStage.show();
                    Stage currentStage = (Stage) ventanaUsu.getScene().getWindow();
                    currentStage.close();
                } else if (!usuarioDAO.validarUsuario(usuario)){ //Si no existe ese usuario:
                    Alerts.mostrarError("Usuario o contraseña erroneos");
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL mostrando un mensaje al usuario
            Alerts.mostrarError("Ocurrió un error al entrar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            usuarioDAO.conectar();
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            Alerts.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            Alerts.mostrarError("Error al cargar la configuración");
        }
    }
}
