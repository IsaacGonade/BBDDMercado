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

    @FXML
    void onIniciar(ActionEvent event) throws IOException{
        try {
            // Verifica que los campos no estén vacíos
            if (usuTF.getText().isEmpty() || passTF.getText().isEmpty()) {
                Alerts.mostrarError("Por favor, completa ambos campos");
            } else {
                //introduce los datos puestos por el usuario
                usuario.setUsuario(usuTF.getText());
                usuario.setPass(passTF.getText());
                //comprueba que ese usuario está en la base de datos
                if (usuarioDAO.validarUsuario(usuario)){
                    //carga el siguiente formulario
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(R.getUI("Gestion.fxml"));
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(loader.load()));
                    newStage.sizeToScene();
                    newStage.show();
                    Stage currentStage = (Stage) ventanaUsu.getScene().getWindow();
                    currentStage.close();
                } else if (!usuarioDAO.validarUsuario(usuario)){
                    Alerts.mostrarError("Usuario o contraseña no son correctos");
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL mostrando un mensaje al usuario
            Alerts.mostrarError("Ocurrió un error al entrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metodo para conectar con la base de datos
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
