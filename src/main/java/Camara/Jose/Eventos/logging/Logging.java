package Camara.Jose.Eventos.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import Camara.Jose.Eventos.utils.MensajeError;

public class Logging {
	
	/**
	 * Atributos de clase
	 */
	static Logger logger = Logger.getLogger(Logging.class.getName());
	
	/**
	 * Genera el log con nivel Info y muestra la información
	 * @param mensaje Mensaje a mostrar
	 */
	public static void infoLogging(String mensaje) {
		guardaLog();
		logger.setLevel(Level.INFO);
		logger.log(Level.INFO,mensaje);
		//logger.info(mensaje);
	}
	
	/**
	 * Genera el log con nivel Warning y muestra la información
	 * @param mensaje Mensaje a mostrar
	 */
	public static void warningLogging(String mensaje) {
		guardaLog();
		logger.setLevel(Level.WARNING);
		logger.log(Level.WARNING,mensaje);
		//logger.info(mensaje);
	}
	
	private static void guardaLog() {
		try {
			InputStream configFile=Logging.class.getResourceAsStream("logging.properties");
			LogManager.getLogManager().readConfiguration(configFile);
		}catch(SecurityException | IOException | NullPointerException e) {
			new MensajeError("Logging system not initialized").muestraMensaje();
		}
		
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}
}
