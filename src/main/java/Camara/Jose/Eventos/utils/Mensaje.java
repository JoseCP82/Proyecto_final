package Camara.Jose.Eventos.utils;

public abstract class Mensaje {
	
	/**
	 * Atributo de clase
	 */
	protected String mensaje;
	
	/**
	 * Constructor con parametros
	 * @param mensaje Mensaje a mostrar
	 */
	public Mensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Mensaje() {
		this("Contenido no disponible.");
	}
	
	/**
	 * Metodo abstrato en el que se mostrará un mensaje
	 */
	abstract public void muestraMensaje();
}
