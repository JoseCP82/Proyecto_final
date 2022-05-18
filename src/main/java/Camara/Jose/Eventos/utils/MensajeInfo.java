package Camara.Jose.Eventos.utils;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class MensajeInfo extends Mensaje {

	/**
	 * Constructor con parametros
	 * @param mensaje Mensaje a mostrar
	 */
	public MensajeInfo(String mensaje) {
		super(mensaje);
	}

	/**
	 * Muestra una pantalla de alerta de tipo Information
	 */
	@Override
	public void muestraMensaje() {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.initStyle(StageStyle.TRANSPARENT);
		a.setHeaderText(null);
		a.setContentText(this.mensaje);
		a.showAndWait();
		
	}

}
