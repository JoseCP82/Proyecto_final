package Camara.Jose.Eventos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.utils.MensajeConfirm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class OpcionesController {

	/**
	 * Atributos de clase bindeados con Scene Builder
	 */
	@FXML Button btnClose;
	
	/**
	 * Cambia a la vista "eventos"
	 * @throws IOException
	 */
	@FXML
    private void switchToEventos() throws IOException {
        App.setRoot("eventos");
    }
	
	/**
	 * Cambia a la vista "inicio"
	 * @throws IOException
	 */
	@FXML
    private void switchToInicio() throws IOException {
        App.setRoot("inicio");
    }
	
	/**
	 * Cambia a la vista "clientes"
	 * @throws IOException
	 */
	@FXML
    private void switchToClientes() throws IOException {
        App.setRoot("clientes");
    }
	
	/**
	 * Cambia a la vista "calendario"
	 * @throws IOException
	 */
	@FXML
    private void switchToCalendario() throws IOException {
        App.setRoot("calendario");
    }
	
	/**
	 * Finaliza la aplicación
	 */
	@FXML
	private void closeWindow() {
		MensajeConfirm ms = new MensajeConfirm("¿Seguro que desea salir?");
		ms.muestraMensaje();
		if(ms.getBt() == ButtonType.OK) {
			Logging.infoLogging("Aplicación finalizada.");
			Stage stage = (Stage) this.btnClose.getScene().getWindow();
			stage.close();
		}
	}
	
	/**
	 * Minimiza la aplicación a la barra de tareas
	 */
	@FXML
	private void miniWindow() {
		Stage stage = (Stage) this.btnClose.getScene().getWindow();
		stage.setIconified(true);
	}
	
	/**
	 * Abre el navegador web por defecto y carga la pagina web del desarollador
	 */
	@FXML
	private void muestraWeb() {
		try {
			Desktop.getDesktop().browse(new URI("https://youtu.be/AkH17p4KzXM"));
        } catch (URISyntaxException ex) {
        	Logging.warningLogging(ex+"");
        }catch(IOException e){
        	Logging.warningLogging(e+"");
        }
	}
	
	/**
	 * Abre el navegador web por defecto y carga la pagina github del desarollador
	 */
	@FXML
	private void muestraGit() {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/JoseCP82/Proyecto_final"));
        } catch (URISyntaxException ex) {
        	Logging.warningLogging(ex+"");
        }catch(IOException e){
        	Logging.warningLogging(e+"");
        }
	}
	
	
}
