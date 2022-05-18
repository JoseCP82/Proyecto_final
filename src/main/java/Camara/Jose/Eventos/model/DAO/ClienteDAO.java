package Camara.Jose.Eventos.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Camara.Jose.Eventos.interfaces.IDAO;
import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.model.DataObject.Cliente;
import Camara.Jose.Eventos.model.DataObject.Evento;
import Camara.Jose.Eventos.utils.Connect;

public class ClienteDAO implements IDAO<Cliente, Integer>{

	/**
	 * Atributos de clase
	 */
	private Connection conexion;
	
	/**
	 * Constructor por defecto
	 */
	public ClienteDAO() {
		this.conexion = Connect.getConnect();
	}
	
	/**
	 * Inserta un cliente en la base de datos
	 * @param Cliente a insertar
	 * @return True o false si se realizó con exito o no
	 */
	@Override
	public boolean insert(Cliente obj) {
		boolean result = false;
		String sql = "insert into cliente (dni,nombre,apellidos,email,direccion,telefono,tipo) values (?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, obj.getDni());
			ps.setString(2, obj.getNombre());
			ps.setString(3, obj.getApellidos());
			ps.setString(4, obj.getEmail());
			ps.setString(5, obj.getDireccion());
			ps.setInt(6, obj.getTelefono());
			ps.setString(7, obj.getTipo());
			ps.executeUpdate();
			result=true;
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
		}
		return result;
	}

	/**
	 * Obtiene un cliente de la base de datos
	 * @param Id del cliente a buscar
	 * @return Cliente encontrado o null si no existe
	 */
	@Override
	public Cliente get(Integer id) {
		Cliente cliente=null;
		String sql = "select * from cliente where id=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			cliente = new Cliente();
			rs.next();
			cliente.setId(rs.getInt(1));
			cliente.setDni(rs.getString(2));
			cliente.setNombre(rs.getString(3));
			cliente.setApellidos(rs.getString(4));
			cliente.setEmail(rs.getString(5));
			cliente.setTipo(rs.getString(6));
			cliente.setDireccion(rs.getString(7));
			cliente.setTelefono(rs.getInt(8));
			cliente.setEventos((List<Evento>) new EventoDAO().getAll());
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			cliente=null;
		}
		return cliente;		
	}
	
	/**
	 * Obtiene un cliente de la base de datos
	 * @param Dni del cliente a buscar
	 * @return Cliente encontrado o null si no existe
	 */
	public Cliente get(String dni) {
		Cliente cliente=null;
		String sql = "select dni,nombre,apellidos,email,tipo,direccion,telefono,id from cliente where dni=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();
			cliente= new Cliente();
			rs.next();
			cliente.setDni(rs.getString(1));
			cliente.setNombre(rs.getString(2));
			cliente.setApellidos(rs.getString(3));
			cliente.setEmail(rs.getString(4));
			cliente.setTipo(rs.getString(5));
			cliente.setDireccion(rs.getString(6));
			cliente.setTelefono(rs.getInt(7));
			cliente.setId(rs.getInt(8));
			cliente.setEventos((List<Evento>) new EventoDAO().getAll());
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			cliente=null;
		}
		return cliente;
	}

	/**
	 * Obtiene una colección de clientes de la base de datos
	 * @return Colección de clientes encontrados o null si no existe
	 */
	@Override
	public Collection<Cliente> getAll() {
		Collection<Cliente> result = new ArrayList<Cliente>();
		String sql = "select * from cliente";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Cliente aux = new Cliente();
				aux.setId(rs.getInt("id"));
				aux.setDni(rs.getString("dni"));
				aux.setNombre(rs.getString("nombre"));
				aux.setApellidos(rs.getString("apellidos"));
				aux.setDireccion(rs.getString("direccion"));
				aux.setEmail(rs.getString("email"));
				aux.setTelefono(rs.getInt("telefono"));
				aux.setTipo(rs.getString("tipo"));
				aux.setEventos((List<Evento>) new EventoDAO().getAll());
				result.add(aux);
			}
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
		}
		return result;
	}

	/**
	 * Actualiza los datos de un cliente existente en la base de datos
	 * @param Cliente a actualizar
	 * @return 1 o 0 si se realizó con exito o no
	 */
	@Override
	public int update(Cliente obj) {
		int result=0;
		String sql = "update cliente set nombre=\""+obj.getNombre()+"\""+",apellidos=\""+obj.getApellidos()+
				"\""+",direccion=\""+obj.getDireccion()+"\""+",telefono="+obj.getTelefono()+
				",email=\""+obj.getEmail()+"\""+",tipo=\""+obj.getTipo()+"\"" + " where dni=\""+obj.getDni()+"\"";
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
	 * Elimina un cliente existente en la base de datos
	 * @param Cliente a eliminar
	 * @return 1 o 0 si se realizó con exito o no 
	 */
	@Override
	public int delete(Cliente obj) {
		int result = 0;
		try {
			Statement st =conexion.createStatement();
			st.executeUpdate("delete from cliente where dni=\""+obj.getDni()+"\"");
			result=1;
		} catch (SQLException e) {
			Logging.warningLogging(e+"");
			result=0;
		}
		return result;
	}

}
