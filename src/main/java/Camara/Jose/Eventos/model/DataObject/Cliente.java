package Camara.Jose.Eventos.model.DataObject;

import java.util.List;

public class Cliente {
	
	/**
	 * Atributos de clase
	 */
	private int id;
	private String dni;
	private String nombre;
	private String apellidos;
	private String email;
	private String direccion;
	private int telefono;
	private String tipo;
	private List<Evento> eventos;
	
	/**
	 * Constructor con parametros
	 * @param dni Dni del cliente
	 * @param nombre Nombre del cliente
	 * @param apellidos Apellidos del cliente
	 * @param email Email del cliente
	 * @param direccion Dirección del cliente
	 * @param telefono Teléfono del cliente
	 * @param tipo Tipo de cliente
	 * @param eventos Eventos organizados por el cliente
	 */
	public Cliente(String dni, String nombre, String apellidos, String email,
			String direccion, int telefono,	String tipo, List<Evento> eventos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tipo = tipo;
		this.eventos = eventos;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Cliente() {}

	/**
	 * Obtiene la id del cliente
	 * @return Id del cliente
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setea el Id del cliente
	 * @param id Id a asignar
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el dni del cliente
	 * @return Dni del cliente
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Setea el dni del cliente
	 * @param dni Dni a asignar
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Obtiene el nombre del cliente
	 * @return Nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre del cliente
	 * @param nombre Nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los apellidos del cliente
	 * @return Apellidos del cliente
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Setea los apellidos del cliente
	 * @param apellidos Apellidos a asignar
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Obtiene el email del cliente
	 * @return Email del cliente
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setea el email del cliente
	 * @param email Email a asignar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene la dirección del cliente
	 * @return Dirección del cliente
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Setea la direccion del cliente
	 * @param direccion Dirección a asignar
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene el telefono del cliente
	 * @return Teléfono del cliente
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Setea el telefono del cliente
	 * @param telefono Telefono a asignar
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el tipo de cliente
	 * @return Tipo de cliente
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Setea el tipo de cliente
	 * @param tipo Tipo a asignar
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene la lista de eventos
	 * @return Lista con los eventos del cliente
	 */
	public List<Evento> getEventos() {
		return eventos;
	}

	/**
	 * Setea la lista de eventos
	 * @param eventos Lista con los eventos a añadir
	 */
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	/**
	 * Método que muestra los datos del cliente (dni, nombre y tipo)
	 */
	@Override
	public String toString() {
		return tipo + " - " + nombre + " - " + dni;
	}
	
	
}
