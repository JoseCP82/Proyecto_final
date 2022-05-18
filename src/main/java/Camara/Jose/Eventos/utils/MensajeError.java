package Camara.Jose.Eventos.utils;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class MensajeError extends Mensaje {

	/**
	 * Constructor con parametros
	 * @param mensaje Mensaje a mostrar
	 */
	public MensajeError(String mensaje) {
		super(mensaje);
	}

	/**
	 * Muestra una pantalla de alerta de tipo Error
	 */
	@Override
	public void muestraMensaje() {
		Alert a = new Alert(Alert.AlertType.ERROR);
		a.initStyle(StageStyle.TRANSPARENT);
		a.setHeaderText(null);
		a.setContentText(this.mensaje);
		a.showAndWait();
	}

}
