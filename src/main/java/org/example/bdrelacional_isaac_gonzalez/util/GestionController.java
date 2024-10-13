package org.example.bdrelacional_isaac_gonzalez.util;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.bdrelacional_isaac_gonzalez.DAO.ProductoDAO;
import org.example.bdrelacional_isaac_gonzalez.domain.Producto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionController implements Initializable {

    @FXML
    private Button LimpiarBt;

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
    private Label estadoLb;

    @FXML
    private TextField marcaTF;

    @FXML
    private TextField nombreTF;

    @FXML
    private TextField precioTF;

    @FXML
    private ListView<Producto> productosLV;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;


    //metodo para cargar los datos de la base de datos
    public void cargarDatos() {
        productosLV.getItems().clear();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            productosLV.setItems(FXCollections.observableList(productos));
            String[] tipos = new String[]{"Carne","Pescado","Verdura","Fruta","Lacteos"};
            cbTipo.setItems(FXCollections.observableArrayList(tipos));
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error cargando los datos de la aplicación");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void eliminarProducto(ActionEvent event) {
        Producto producto = productosLV.getSelectionModel().getSelectedItem();
        if (producto == null) {
            estadoLb.setText("ERROR: No se ha seleccionado ningún producto");
            return;
        }

        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Eliminar producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }

            productoDAO.eliminarProducto(producto);
            estadoLb.setText("MENSAJE: Producto eliminado con éxito");

            cargarDatos();
        } catch (SQLException sqle) {
            Alerts.mostrarError("No se ha podido eliminar el producto");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modificarProducto(ActionEvent event) {
        String marca = marcaTF.getText();
        if (marca.isEmpty()) {
            Alerts.mostrarError("La marca es un campo obligatorio");
            return;
        }
        String nombre = nombreTF.getText();
        int precio = Integer.parseInt(precioTF.getText());
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Producto producto = new Producto(precio, marca, nombre, tipo);

        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Modificar producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }

            productoDAO.modificarProducto(productoSeleccionado, producto);
            estadoLb.setText("Producto modificado con éxito");

            cargarDatos();
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error al modificar producto");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void guardarProducto(ActionEvent event) {
        String marca = marcaTF.getText();
        if (marca.isEmpty()) {
            Alerts.mostrarError("La marca es un campo obligatorio");
            return;
        }
        String nombre = nombreTF.getText();
        int precio = Integer.parseInt(precioTF.getText());
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Producto producto = new Producto(precio, marca, nombre, tipo);

        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Añadir producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }

            productoDAO.guardarProducto(producto);
            estadoLb.setText("Producto guardado con éxito");

            cargarDatos();
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error al guadar el producto");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    //metodo para meter los datos de un producto
    private void cargarProducto(Producto producto) {
        marcaTF.setText(producto.getMarca());
        nombreTF.setText(producto.getNombre());
        precioTF.setText(String.valueOf(producto.getPrecio()));
        cbTipo.setValue(producto.getTipo());
    }

    //metodo para tener los datos de un producto seleccionado de la lista por el raton
    @FXML
    void seleccionarProducto(MouseEvent event) {
        productoSeleccionado = productosLV.getSelectionModel().getSelectedItem();
        cargarProducto(productoSeleccionado);
    }


    //metodo para que el boton limpiar vacie los campos a rellenar
    @FXML
    void onLimpiar(ActionEvent event) {
        marcaTF.setText("");
        precioTF.setText("");
        nombreTF.setText("");
        cbTipo.setValue("Selecciona tipo");
        marcaTF.requestFocus();
    }


    //metodo para conectar con la base de datos
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            productoDAO.conectar();
        } catch (SQLException sqle) {
            Alerts.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            Alerts.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            Alerts.mostrarError("Error al cargar la configuración");
        }
        cargarDatos();
    }
}

