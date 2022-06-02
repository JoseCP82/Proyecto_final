package Camara.Jose.Eventos.utils;

import java.time.LocalDate;

import Camara.Jose.Eventos.logging.Logging;

public class ValidacionEvento {
	
	/**
	 * Comprueba si los datos del evento corresponden con su formato
	 */
	public static boolean validaEvento(LocalDate fecha, String direccion, String cliente, 
			String precio, String descripcion) {
		boolean resFecha=false, resDireccion=false, resCliente=false, resPrecio=false, resDescripcion=false;
		if(fecha!=null && !direccion.equals("") && !cliente.equals("") 
				&& !precio.equals("") && !descripcion.equals("")) {
			resFecha=true;
			resDireccion=true;
			resCliente=true;
			resPrecio=true;
			resDescripcion=true;
			LocalDate fechaActual = LocalDate.now();
			if(fecha.compareTo(fechaActual)<0) {
				resFecha=false;
				new MensajeError("Fecha obsoleta.").muestraMensaje();
				Logging.infoLogging("Fecha obsoleta.");
			}
			
			try {
				float precioValido = Float.parseFloat(precio);
				if(precioValido<0) {
					resPrecio=false;
					new MensajeError("El precio no puede ser negativo.").muestraMensaje();
					Logging.infoLogging("El precio no puede ser negativo.");
				}
			} catch (NumberFormatException ex) {
				resPrecio=false;
				new MensajeError("El dato debe ser numérico.").muestraMensaje();
				Logging.infoLogging("El dato debe ser numérico.");
			}
		}
		else {
			new MensajeError("No pueden quedar campos sin rellenar.").muestraMensaje();
			Logging.infoLogging("No pueden quedar campos sin rellenar.");
		}
		return resFecha && resDireccion && resCliente && resPrecio && resDescripcion;
	}
}
