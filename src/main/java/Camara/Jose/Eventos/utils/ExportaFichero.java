package Camara.Jose.Eventos.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.model.DAO.ClienteDAO;
import Camara.Jose.Eventos.model.DAO.EventoDAO;
import Camara.Jose.Eventos.model.DataObject.Evento;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExportaFichero {

	/**
	 * Guarda un objeto de tipo en un archivo en disco
	 * 
	 * @param aguardar Evento a guardar
	 * @param file Nombre del archivo donde guardar la informacion
	 * @return True o false si se realizó con exito o no
	 */
	public static boolean guardaEventos(Stage stage) {
		boolean result = false;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar Eventos");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
		File file = fileChooser.showSaveDialog(stage);
		if(file!=null){
			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				fw = new FileWriter(file, false);
				bw = new BufferedWriter(fw);
				result=true;
				for(Evento e : new EventoDAO().getAll()) {	
					e.setCliente(new ClienteDAO().get(e.getIdCliente()));
					bw.write(e.toString()+"\n");
				}
			} catch (Exception e) {
				Logging.warningLogging(e+"");
			} finally {
				try {
					bw.close();
				} catch (Exception e2) {
					Logging.warningLogging(e2+"");
				}
			}
		}
		return result;
	}
}
