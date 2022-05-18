package Camara.Jose.Eventos.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class MensajeConfirm extends Mensaje {
	
	/**
	 * Atributo de clase
	 */
	private ButtonType bt;
	
	/**
	 * Constructor con parametros
	 * @param mensaje Mensaje a mostrar
	 */
	public MensajeConfirm(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Obtiene el contenido del botón
	 * @return Valor dependiendo de si el usuario pulsó el boton cancela o aceptar 
	 */
	public ButtonType getBt() {
		return bt;
	}
	
	/**
	 * Muestra una pantalla de alerta de tipo Confirmation
	 */
	@Override
	public void muestraMensaje() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText(this.mensaje);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.setHeaderText(null);
		bt=alert.showAndWait().get();
	}

}
