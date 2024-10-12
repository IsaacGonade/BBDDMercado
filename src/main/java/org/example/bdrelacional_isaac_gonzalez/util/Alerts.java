package org.example.bdrelacional_isaac_gonzalez.util;

import javafx.scene.control.Alert;

public class Alerts {
    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
