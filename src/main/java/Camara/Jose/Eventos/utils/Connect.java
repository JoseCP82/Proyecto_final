package Camara.Jose.Eventos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Camara.Jose.Eventos.logging.Logging;

public class Connect {
	
	/**
	 * Atributos de clase
	 */
	private static Connection con;
	private static Connect _newInstance;
	
	/**
	 * Metodo para realizar la conexion
	 */
	private Connect() {
		try {
			DatosConexion dc = load();
			con=DriverManager.getConnection(dc.getServer()+"/"+dc.getDatabase(), dc.getUsername(), dc.getPassword());
		} catch (SQLException e) {
			new MensajeError("No se pudo crear la conexion").muestraMensaje();
			Logging.warningLogging(e+"");
			con=null;
		}
	}
	
	/**
	 * Metodo que realiza la instancia de la clase
	 * @return Devuelve el objeto con inicializado
	 */
	public static Connection getConnect() {
		if(_newInstance==null) {
			_newInstance=new Connect();
		}
		return con;
	}
	
	/**
	 * Obtiene de un archivo xml los datos para necesario para realizar la conexion con la bbdd 
	 * @return Objeto con los datos para realizar la conexion
	 */
	public DatosConexion load() {
		DatosConexion dc = new DatosConexion();
		JAXBContext contexto;
		try {
			contexto=JAXBContext.newInstance(DatosConexion.class);
			Unmarshaller um = contexto.createUnmarshaller();
			dc = (DatosConexion)um.unmarshal(Connect.class.getResource("/conexion/conexion.xml"));
		} catch (JAXBException e) {
			Logging.warningLogging(e+"");
		}
		return dc;
	}
}
