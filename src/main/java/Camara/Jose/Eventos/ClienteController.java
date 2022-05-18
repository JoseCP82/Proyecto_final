package Camara.Jose.Eventos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.model.DAO.ClienteDAO;
import Camara.Jose.Eventos.model.DataObject.Cliente;
import Camara.Jose.Eventos.utils.Mensaje;
import Camara.Jose.Eventos.utils.MensajeConfirm;
import Camara.Jose.Eventos.utils.MensajeError;
import Camara.Jose.Eventos.utils.MensajeInfo;
import Camara.Jose.Eventos.utils.ValidacionCliente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClienteController implements Initializable {

	/**
	 * Atributos de clase bindeados con Scene Builder
	 */
	@FXML private ComboBox<String> cbxTipo;
	@FXML private TextField txtDni;
	@FXML private TextField txtNombre;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtEmail;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtBuscaCliente;
	@FXML private Button btnGuardar;
	@FXML private Button btnEliminar;
	@FXML private Button btnActualizar;
	@FXML private Button btnClose;
	@FXML private Button btnReset;
	
	@FXML private TableView<Cliente> tblClientes;
	@FXML private TableColumn<Cliente, String> colDni;
	@FXML private TableColumn<Cliente, String> colNombre;
	@FXML private TableColumn<Cliente, String> colApellidos;
	@FXML private TableColumn<Cliente, String> colEmail;
	@FXML private TableColumn<Cliente, String> colDireccion;
	@FXML private TableColumn<Cliente, Integer> colTelefono;
	@FXML private TableColumn<Cliente, String> colTipo;
	
	/**
	 * Atributos de clase
	 */
	private Collection<Cliente> clientes;
	private ObservableList<Cliente> listaClientes;

	private Cliente cliente;

	/**
	 * Inicia los elementos usados en la interfaz gráfica
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxTipo.setItems(FXCollections.observableArrayList("Representante", "Ayuntamiento", "Particular", "Otros"));
		
		clientes = new ClienteDAO().getAll();
		listaClientes = FXCollections.observableArrayList();
		listaClientes.addAll(clientes);
		tblClientes.setItems(listaClientes);
		
		colDni.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dni"));
		colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
		colApellidos.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidos"));
		colEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefono"));
		colTipo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipo"));
		seleccionaCliente();
	}

	/**
	 * Actualiza los datos mostrados en el tableview de la interfaz gráfica
	 */
	public void seleccionaCliente() {
		tblClientes.getSelectionModel().selectedItemProperty().
				addListener(new ChangeListener<Cliente>() {

			@Override
			public void changed(ObservableValue<? extends Cliente> observable, 
					Cliente oldValue, Cliente newValue) {
				txtDni.setText(newValue.getDni());
				txtNombre.setText(newValue.getNombre());
				txtApellidos.setText(newValue.getApellidos());
				txtEmail.setText(newValue.getEmail());
				txtDireccion.setText(newValue.getDireccion());
				txtTelefono.setText(String.valueOf(newValue.getTelefono()));
				cbxTipo.setValue(newValue.getTipo());
				
				if(newValue!=null) {
					btnEliminar.setDisable(false);
					btnGuardar.setDisable(true);
					btnActualizar.setDisable(false);
				}
			}
		});
	}	
	
	/**
	 * Obtiene los datos recogidos en la interfaz gráfica, crea y añade a la bbdd un cliente 
	 */
	@FXML
	public void insertaCliente() {
		String dni = txtDni.getText();
		String nombre = txtNombre.getText();
		String apellidos = txtApellidos.getText();
		String email = txtEmail.getText();
		String direccion = txtDireccion.getText();
		String tipo = cbxTipo.getValue();
		
		Mensaje ms = new MensajeConfirm("¿Seguro que desea guardar?");
		ms.muestraMensaje();
		if(((MensajeConfirm) ms).getBt() == ButtonType.OK) {
			if(ValidacionCliente.validaCliente(dni, nombre, apellidos, email, txtTelefono.getText(), 
					direccion, tipo)) {
				int telefono = Integer.parseInt(txtTelefono.getText());
				ClienteDAO cd = new ClienteDAO();
				cliente = cd.get(dni);
				if(cliente == null) {
					cliente = new Cliente(dni, nombre, apellidos, email, direccion, telefono, tipo, null);
					if (new ClienteDAO().insert(cliente)) {
						new MensajeInfo("Cliente insertado con éxito.").muestraMensaje();
						cliente.setId(cd.get(dni).getId());						
						initialize(null, null);
						Logging.infoLogging("Cliente insertado con éxito.");
					} else {
						new MensajeError("El cliente no se pudo insertar.").muestraMensaje();
						Logging.warningLogging("El cliente no se pudo insertar.");
					}
				}
				else {
					new MensajeError("El cliente ya existe.").muestraMensaje();
					Logging.infoLogging("El cliente ya existe.");
				}
				reset();
			}
		}
	}

	/**
	 * Busca un cliente en la bbdd determinado por el usuario y lo muestra 
	 */
	@FXML
	public void buscaCliente() {
		if(!txtBuscaCliente.getText().equals("")) {
			cliente = new ClienteDAO().get(txtBuscaCliente.getText());
			if (cliente != null) {
				btnEliminar.setDisable(false);
				txtDni.setText(cliente.getDni());
				txtNombre.setText(cliente.getNombre());
				txtApellidos.setText(cliente.getApellidos());
				txtEmail.setText(cliente.getEmail());
				txtDireccion.setText(cliente.getDireccion());
				txtTelefono.setText(String.valueOf(cliente.getTelefono()));
				cbxTipo.setValue(cliente.getTipo());
				btnActualizar.setDisable(false);
				btnEliminar.setDisable(false);
			} else {
				new MensajeInfo("Cliente no encontrado.").muestraMensaje();
				Logging.warningLogging("Cliente no encontrado.");
				reset();
			}
		}
		else {
			new MensajeInfo("Debe introducir un número de DNI.").muestraMensaje();
			Logging.infoLogging("Campo buscar vacio");
		}
	}

	/**
	 * Actualiza los datos de un cliente dado por el usuario
	 */
	@FXML
	public void actualizaCliente() {
		String dni = txtDni.getText();
		String nombre = txtNombre.getText();
		String apellidos = txtApellidos.getText();
		String email = txtEmail.getText();
		String direccion = txtDireccion.getText();
		String telefono = txtTelefono.getText();
		String tipo = cbxTipo.getValue();
		Mensaje ms = new MensajeConfirm("¿Seguro que desea actualizar?");
		ms.muestraMensaje();
		if(((MensajeConfirm) ms).getBt() == ButtonType.OK) {
			if(ValidacionCliente.validaCliente(dni, nombre, apellidos, email, telefono, direccion, tipo)) {
				cliente = new ClienteDAO().get(txtDni.getText());
				cliente.setNombre(nombre);
				cliente.setApellidos(apellidos);
				cliente.setEmail(email);
				cliente.setDireccion(direccion);
				cliente.setTelefono(Integer.parseInt(telefono));
				cliente.setTipo(tipo);
				if (new ClienteDAO().update(cliente) == 1) {
					new MensajeInfo("Cliente actualizado con éxito.").muestraMensaje();
					initialize(null, null);
					Logging.infoLogging("Cliente actualizado con éxito.");
				} else {
					new MensajeError("El cliente no se pudo actualizar.").muestraMensaje();
					Logging.warningLogging("El cliente no se pudo actualizar.");
				}
				reset();
			}
		}
	}

	/**
	 * Elimina de la bbdd un cliente dado por el usuario
	 */
	@FXML
	public void eliminaCliente() {
		ClienteDAO cd = new ClienteDAO();
		cliente = cd.get(txtDni.getText());
		if (cliente != null) {
			MensajeConfirm ms = new MensajeConfirm("¿Seguro que desea eliminar?");
			ms.muestraMensaje();
			if(ms.getBt() == ButtonType.OK) {
				if (cd.delete(cliente) == 1) {
					new MensajeInfo("Cliente eliminado con éxito.").muestraMensaje();
					initialize(null, null);
					Logging.infoLogging("Cliente eliminado con éxito.");
				} else {
					new MensajeError("El cliente no se pudo eliminar.").muestraMensaje();
					Logging.warningLogging("El cliente no se pudo eliminar.");
				}
				reset();
			}
		}
	}
	
	/**
	 * Setea a su valor por defecto los elementos de la interfaz gráfica
	 */
	@FXML
	public void nuevo() {
		txtDni.setText("");
		txtNombre.setText("");
		txtApellidos.setText("");
		txtDireccion.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		cbxTipo.setValue(null);
		cbxTipo.setPromptText("Seleccione...");
		txtBuscaCliente.setText("");
		txtBuscaCliente.setPromptText("Buscar");
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
		btnGuardar.setDisable(false);
	}
	
	/**
	 * Setea a su valor por defecto los elementos de la interfaz gráfica
	 */
	@FXML
	public void reset() {	
		txtDni.setText("");
		txtNombre.setText("");
		txtApellidos.setText("");
		txtDireccion.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		cbxTipo.setValue(null);
		cbxTipo.setPromptText("Seleccione...");
		txtBuscaCliente.setText("");
		txtBuscaCliente.setPromptText("Buscar");
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
		btnGuardar.setDisable(true);
	}
	
	/**
	 * Cambia a la vista "eventos"
	 * @throws IOException
	 */
	@FXML
    private void switchToEventos() throws IOException {
        App.setRoot("eventos");
    }
	
	/**
	 * Cambia a la vista "inicio"
	 * @throws IOException
	 */
	@FXML
    private void switchToInicio() throws IOException {
        App.setRoot("inicio");
    }
	
	/**
	 * Cambia a la vista "opciones"
	 * @throws IOException
	 */
	@FXML
    private void switchToAyuda() throws IOException {
        App.setRoot("opciones");
    }
	
	/**
	 * Cambia a la vista "calendario"
	 * @throws IOException
	 */
	@FXML
    private void switchToCalendario() throws IOException {
        App.setRoot("calendario");
    }
	
	/**
	 * Finaliza la aplicación
	 */
	@FXML
	private void closeWindow() {
		MensajeConfirm ms = new MensajeConfirm("¿Seguro que desea salir?");
		ms.muestraMensaje();
		if(ms.getBt() == ButtonType.OK) {
			Logging.infoLogging("Aplicación finalizada.");
			Stage stage = (Stage) this.btnClose.getScene().getWindow();
			stage.close();
		}
	}
	
	/**
	 * Minimiza la aplicación a la barra de tareas
	 */
	@FXML
	private void miniWindow() {
		Stage stage = (Stage) this.btnClose.getScene().getWindow();
		stage.setIconified(true);
	}
	
	/**
	 * Abre el navegador web por defecto y carga la pagina github del desarollador
	 */
	@FXML
	private void muestraGit() {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/JoseCP82/Proyecto_final"));
        } catch (URISyntaxException ex) {
        	Logging.warningLogging(ex+"");
        }catch(IOException e){
        	Logging.warningLogging(e+"");
        }
	}
}
