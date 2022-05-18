package Camara.Jose.Eventos.utils;

import Camara.Jose.Eventos.logging.Logging;

public class ValidacionCliente {
	
	/**
	 * Comprueba el formato del numero telefonico introducido
	 * @param dni Numero de telefono
	 * @return True o false si es correcto o no
	 */
	public static boolean validaTelefono(String telefono) {		
		return telefono.matches("[0-9]{9}");
	}
	
	/**
	 * Comprueba el formato del email introducido
	 * @param email Email
	 * @return True o false si es correcto o no
	 */
	public static boolean validaEmail(String email) {
		return email.matches("^[\\w-]+(\\.[\\w-])*@[a-z A-Z 0-9]+(\\.[a-z A-Z 0-9])*\\.[a-z]{2,}");
	}
	
	/**
	 * Comprueba el formato del dni introducido
	 * @param dni Numero de Dni
	 * @return True o false si es correcto o no
	 */
	public static boolean validaDni(String dni) {		
		return dni.matches("[0-9]{8}[A-Z]");
	}
	
	/**
	 * Comprueba si el numero de Dni es válido (real)
	 * @param numero Numero de Dni a validar
	 * @return True o false si es correcto o no
	 */
	public static boolean compruebaDni(String dni) {
		boolean result = false;
		char[] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J',
	            'Z','S','Q','V','H','L','C','K','E'};
	    if(!dni.equals("")) {
	    	int numero = Integer.parseInt(dni.substring(0, dni.length()-1));
			char letra = dni.charAt(dni.length()-1);
			if(letras[numero%23]==letra) {
				result=true;
			}
	    }	
		return result;
	}
	
	/**
	 * Comprueba si los datos del cliente corresponden con su formato
	 */
	public static boolean validaCliente(String dni, String nombre, String apellidos, String email, 
			String telefono, String direccion, String tipo) {
		boolean resDni=false, resNombre=false, resApellidos=false, resEmail=false,
				resTelefono=false, resDireccion=false, resTipo=false;
		if(!dni.equals("") && !nombre.equals("") && !apellidos.equals("") && !email.equals("") &&
				!telefono.equals("") && !direccion.equals("") && !tipo.equals("")) {
			resNombre=true;
			resApellidos=true;
			resEmail=true;
			resTelefono=true;
			resDireccion=true;
			resTipo=true;
			if(validaDni(dni)) {
				resDni=true;
				if(!compruebaDni(dni)) {
					new MensajeError("El número de DNI no es válido.").muestraMensaje();
					resDni=false;
					Logging.infoLogging("El número de DNI no es válido.");
				}
			}
			else {
				new MensajeError("El formato de DNI no es correcto.").muestraMensaje();
				Logging.infoLogging("El formato de DNI no es correcto.");
			}
			if(!validaEmail(email)) {
				new MensajeError("El formato de email no es correcto.").muestraMensaje();
				resEmail=false;
				Logging.infoLogging("El formato de email no es correcto.");
				
			}
			if(!validaTelefono(telefono)) {
				new MensajeError("El formato de teléfono no es correcto.").muestraMensaje();
				resTelefono=false;
				Logging.infoLogging("El formato de teléfono no es correcto.");
			}
		}
		else {
			 new MensajeError("No pueden quedar campos sin rellenar.").muestraMensaje();
			 Logging.infoLogging("No pueden quedar campos sin rellenar.");
		}
		return resDni && resNombre && resApellidos && resEmail && resTelefono && resDireccion && resTipo;
	}
}
