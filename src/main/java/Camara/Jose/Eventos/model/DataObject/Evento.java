package Camara.Jose.Eventos.model.DataObject;

import java.io.Serializable;
import java.time.LocalDate;

public class Evento implements Serializable {

	/**
	 * Atributos de clase
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String direccion;
	private LocalDate fecha;
	private String descripcion;
	private float precio;
	private int idCliente;
	private Cliente cliente;
	
	/**
	 * Constructor con parametros
	 * @param direccion Dirección del evento
	 * @param fecha Fecha del evento
	 * @param descripcion Descripción del evento
	 * @param precio Precio del evento
	 * @param idCliente Id del cliente del evento
	 * @param cliente que organiza el evento 
	 */
	public Evento(String direccion, LocalDate fecha, String descripcion, 
			float precio, int idCliente, Cliente cliente) {
		this.direccion = direccion;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.precio = precio;
		this.idCliente = idCliente;
		this.cliente = cliente;
	}
	
	/**
	 * Constructor con parametros
	 * @param direccion Dirección del evento
	 * @param fecha Fecha del evento
	 * @param descripcion Descripción del evento
	 * @param precio Precio del evento
	 * @param idCliente Id del cliente del evento
	 */
	public Evento(String direccion, LocalDate fecha, String descripcion, 
			float precio, int idCliente) {
		this.direccion = direccion;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.precio = precio;
		this.idCliente = idCliente;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Evento() {}

	/**
	 * Obtiene la id del evento
	 * @return Id del evento
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setea el Id del evento
	 * @param id Id a asignar
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene la direccion del evento
	 * @return Dirección del evento
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Setea la direccion del evento
	 * @param direccion Dirección a asignar
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene la fecha
	 * @return Fecha del evento
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Setea la fecha
	 * @param fecha Fecha a asignar
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene la descripción del evento
	 * @return Descripción del evento
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Setea la descripción del evento
	 * @param descripcion Descripción a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el precio del evento
	 * @return Precio del evento
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * Setea el precio del evento
	 * @param precio Precio a asignar
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}

	/**
	 * Obtiene el id del cliente que organiza el evento
	 * @return Id del cliente
	 */
	public int getIdCliente() {
		return idCliente;
	}
	
	/**
	 * Setea el Id del cliente
	 * @param idCliente Id a asignar
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Obtiene un objeto de tipo cliente
	 * @return Cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Setea un cliente
	 * @param cliente Cliente a setear
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo que devuelve una cadena con los datos del evento
	 */
	@Override
	public String toString() {
		return "Fecha: "+fecha+" - Dirección: "+direccion+" - Precio: "+precio+" - Cliente: "+cliente+" - Descripción: "+descripcion;
	}
	
	
}
