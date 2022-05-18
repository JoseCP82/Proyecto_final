package Camara.Jose.Eventos.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import Camara.Jose.Eventos.interfaces.IDAO;
import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.model.DataObject.Evento;
import Camara.Jose.Eventos.utils.Connect;

public class EventoDAO implements IDAO<Evento, Integer>{

	/**
	 * Atributos de clase
	 */
	private Connection conexion;
	
	/**
	 * Constructor por defecto
	 */
	public EventoDAO() {
		this.conexion = Connect.getConnect();
	}
	
	/**
	 * Inserta un evento en la base de datos
	 * @param evento a insertar
	 * @return True o false si se realizó con exito o no
	 */
	@Override
	public boolean insert(Evento obj) {
		boolean result = false;
		String sql = "insert into evento (direccion,fecha,precio,descripcion,idcliente) values (?,?,?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, obj.getDireccion());
			ps.setObject(2, obj.getFecha());
			ps.setFloat(3, obj.getPrecio());
			ps.setString(4, obj.getDescripcion());
			ps.setInt(5, obj.getIdCliente());
			ps.executeUpdate();
			result=true;
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
		}
		return result;
	}

	/**
	 * Obtiene un evento de la base de datos
	 * @param Id del evento a buscar
	 * @return evento encontrado o null si no existe
	 */
	@Override
	public Evento get(Integer id) {
		Evento evento=null;
		String sql = "select * from evento where id=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			evento = new Evento();
			rs.next();
			evento.setId(rs.getInt(1));
			evento.setDireccion(rs.getString(2));
			evento.setFecha(rs.getDate(3).toLocalDate());
			evento.setDescripcion(rs.getString(5));
			evento.setPrecio(rs.getFloat(4));
			evento.setIdCliente(rs.getInt(6));
			evento.setCliente(new ClienteDAO().get(rs.getInt(6)));
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			evento=null;
		}
		return evento;	
	}

	/**
	 * Obtiene un evento de la base de datos
	 * @param Fecha del evento a buscar
	 * @return Evento encontrado o null si no existe
	 */
	public Evento get(String fecha) {
		Evento evento=null;
		String sql = "select * from evento where fecha=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, fecha);
			ResultSet rs = ps.executeQuery();
			evento = new Evento();
			rs.next();
			evento.setId(rs.getInt(1));
			evento.setDireccion(rs.getString(2));
			evento.setFecha(rs.getDate(3).toLocalDate());
			evento.setDescripcion(rs.getString(5));
			evento.setPrecio(rs.getFloat(4));
			evento.setIdCliente(rs.getInt(6));
			evento.setCliente(new ClienteDAO().get(rs.getInt(6)));
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			evento=null;
		}
		return evento;
	}
	
	/**
	 * Obtiene una colección de eventos de la base de datos
	 * @return Colección de eventos encontrados o null si no existe
	 */
	@Override
	public Collection<Evento> getAll() {
		Collection<Evento> result = new ArrayList<Evento>();
		String sql = "select * from evento";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Evento aux = new Evento();
				aux.setId(rs.getInt("id"));
				aux.setDireccion(rs.getString("direccion"));
				aux.setFecha(rs.getDate(3).toLocalDate());
				aux.setPrecio(rs.getFloat("precio"));
				aux.setDescripcion(rs.getString("descripcion"));
				aux.setIdCliente(rs.getInt("idCliente"));
				result.add(aux);
			}
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
		}
		return result;
	}

	/**
	 * Obtiene una colección de eventos de la base de datos buscados por fecha
	 * @return Colección de eventos encontrados o null si no existe
	 */
	public Collection<Evento> getAllDate(String fecha) {
		Collection<Evento> result = new ArrayList<Evento>();
		String sql = "select * from evento where fecha=\""+fecha+"\"";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Evento aux = new Evento();
				aux.setId(rs.getInt("id"));
				aux.setDireccion(rs.getString("direccion"));
				aux.setFecha(rs.getDate(3).toLocalDate());
				aux.setPrecio(rs.getFloat("precio"));
				aux.setDescripcion(rs.getString("descripcion"));
				aux.setIdCliente(rs.getInt("idCliente"));
				result.add(aux);
			}
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
		}
		return result;
	}
	
	
	
	/**
	 * Actualiza los datos de un evento existente en la base de datos
	 * @param evento a actualizar
	 * @return 1 o 0 si se realizó con exito o no
	 */
	@Override
	public int update(Evento obj) {		
		int result=0;
		String sql = "update evento set direccion=\""+obj.getDireccion()+"\""+",fecha=\""+obj.getFecha()+"\""+",descripcion=\""+obj.getDescripcion()+"\""+
				",precio="+obj.getPrecio()+ " where id="+obj.getId();
		try {
			Statement st = conexion.createStatement();
			st.executeUpdate(sql);
			result=1;
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			result=0;			
		}
		return result;
	}

	/**
	 * Elimina un evento existente en la base de datos
	 * @param Evento a eliminar
	 * @return 1 o 0 si se realizó con exito o no 
	 */
	@Override
	public int delete(Evento obj) {
		int result = 0;
		try {
			Statement st =conexion.createStatement();
			st.executeUpdate("delete from evento where id=\""+obj.getId()+"\"");
			result=1;
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			result=0;
		}
		return result;
	}

}
