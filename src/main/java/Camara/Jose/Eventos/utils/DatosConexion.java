package Camara.Jose.Eventos.utils;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="conexion")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosConexion implements Serializable{
	
	/**
	 * Atributos de clase
	 */
	private static final long serialVersionUID = 1L;
	private String server;
	private String database;
	private String username;
	private String password;
	
	/**
	 * Constructor por defecto
	 */
	public DatosConexion() {}

	/**
	 * Obtiene el servidor
	 * @return Nombre del servidor
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Setea el servidor
	 * @param server Servidor a asignar
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Obtiene la base de datos
	 * @return Nombre de la base de datos
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Setea la base de datos
	 * @param database Bbdd a asignar
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * Obtiene el nombre de usuario
	 * @return Nombre del usuario
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setea el nombre de usuario
	 * @param username Usuario a asignar
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Obtiene la contraseña
	 * @return Contraseña
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setea la contraseña
	 * @param password Contraseña a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
