package Camara.Jose.Eventos.interfaces;

import java.util.Collection;

public interface IDAO<T,K> {
	
	/**
	 * Insertar un objeto en la base de datos
	 * @param obj Objeto a insertar
	 * @return True o false si se realizó con exito o no
	 */
	boolean insert(T obj);
	
	/**
	 * Obtiene un objeto de la base de datos
	 * @param id Id del objeto a buscar
	 * @return Objeto encontrado o null si no existe
	 */
	T get(K id);
	
	/**
	 * Obtiene una coleccion de objetos encontrados en la base de datos
	 * @return Coleccion de objetos
	 */
	Collection<T> getAll();
	
	/**
	 * Actualiza los datos de un objeto existente en la base de datos
	 * @param obj Objeto a modificar
	 * @return 1 o 0 si se realizó con exito o no
	 */
	int update(T obj);
	
	/**
	 * Elimina un objeto de la base de datos
	 * @param obj Objeto a eliminar
	 * @return 1 o 0 si se realizó con exito o no
	 */
	int delete(T obj);
}