package org.example.bdrelacional_isaac_gonzalez.util;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.bdrelacional_isaac_gonzalez.DAO.ProductoDAO;
import org.example.bdrelacional_isaac_gonzalez.domain.Producto;

import java.sql.SQLException;
import java.util.List;

public class GestionController {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btEliminar;

    @FXML
    private Button btModificar;

    @FXML
    private Button btNuevo;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private Label lbEstado;

    @FXML
    private TextField marcaTF;

    @FXML
    private TextField nombreTF;

    @FXML
    private ListView<Producto> productosLV;

    private enum Accion{
        NUEVO, MODIFICAR
    }
    private Accion accion;

    private Producto productoSeleccionado;

    public void cargarDatos() {
        productosLV.getItems().clear();
        try {
            List<Producto> productos = ProductoDAO.obtenerProducto();
            productosLV.setItems(FXCollections.observableList(productos));
            String[] tipos = new String[]{"<Selecciona tipo>", "Carne","Pescado","Verdura","Fruta","Lacteos"};
            cbTipo.setItems(FXCollections.observableArrayList(tipos));
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error cargando los datos de la aplicación");
        }
    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void eliminarProducto(ActionEvent event) {

    }

    @FXML
    void modificarProducto(ActionEvent event) {

    }

    @FXML
    void nuevoProducto(ActionEvent event) {

    }

    @FXML
    void seleccionarProducto(MouseEvent event) {

    }

    private void limpiarCajas() {
        tfMatricula.setText("");
        tfModelo.setText("");
        tfMarca.setText("");
        cbTipo.setValue("<Selecciona tipo>");
        tfMatricula.requestFocus();
    }

}

