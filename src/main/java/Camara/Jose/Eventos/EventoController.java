package Camara.Jose.Eventos;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;
import Camara.Jose.Eventos.logging.Logging;
import Camara.Jose.Eventos.model.DAO.ClienteDAO;
import Camara.Jose.Eventos.model.DAO.EventoDAO;
import Camara.Jose.Eventos.model.DataObject.Cliente;
import Camara.Jose.Eventos.model.DataObject.Evento;
import Camara.Jose.Eventos.utils.ExportaFichero;
import Camara.Jose.Eventos.utils.Mensaje;
import Camara.Jose.Eventos.utils.MensajeConfirm;
import Camara.Jose.Eventos.utils.MensajeError;
import Camara.Jose.Eventos.utils.MensajeInfo;
import Camara.Jose.Eventos.utils.ValidacionEvento;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EventoController implements Initializable {

	/**
	 * Atributos de clase bindeados con Scene Builder
	 */
	@FXML private Button btnGuardar;
	@FXML private Button btnEliminar;
	@FXML private Button btnActualizar;
	@FXML private Button btnClose;
	@FXML private Button btnReset;
	@FXML private TableView<Evento> tblEventos;
	@FXML private TableColumn<Evento, String> colDireccion;
	@FXML private TableColumn<Evento, LocalDateTime> colFecha;
	@FXML private TableColumn<Evento, String> colDescripcion;
	@FXML private TableColumn<Evento, Float> colPrecio;
	@FXML private TableColumn<Evento, Cliente> colIdCliente;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtId;
	@FXML private DatePicker dtpFecha;
	@FXML private TextField txtDireccion;
	@FXML private TextArea txtDescripcion;
	@FXML private TextField txtPrecio;
	@FXML private ComboBox<Cliente> cbxClientes;

	/**
	 * Atributos de clase
	 */
	private Collection<Cliente> clientes;
	private ObservableList<Cliente> listaClientes;

	private Collection<Evento> eventos;
	private ObservableList<Evento> listaEventos;

	private Evento evento;
	
	/**
	 * Inicia los elementos usados en la interfaz gráfica
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientes = new ClienteDAO().getAll();
		listaClientes = FXCollections.observableArrayList();
		listaClientes.addAll(clientes);
		cbxClientes.setItems(listaClientes);

		eventos = new EventoDAO().getAll();
		for(Evento e : eventos) {
			e.setCliente(new ClienteDAO().get(e.getIdCliente()));
		}
		
		listaEventos = FXCollections.observableArrayList();
		listaEventos.addAll(eventos);
		tblEventos.setItems(listaEventos);

		colDireccion.setCellValueFactory(new PropertyValueFactory<Evento, String>("direccion"));
		colFecha.setCellValueFactory(new PropertyValueFactory<Evento, LocalDateTime>("fecha"));
		colDescripcion.setCellValueFactory(new PropertyValueFactory<Evento, String>("descripcion"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<Evento, Float>("precio"));
		colIdCliente.setCellValueFactory(new PropertyValueFactory<Evento, Cliente>("cliente"));
		seleccionaEvento();
	}

	/**
	 * Actualiza los datos mostrados en el tableview de la interfaz gráfica
	 */
	public void seleccionaEvento() {
		tblEventos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Evento>() {
			@Override
			public void changed(ObservableValue<? extends Evento> observable, Evento oldValue, Evento newValue) {
				txtId.setText(String.valueOf(newValue.getId()));
				txtDireccion.setText(newValue.getDireccion());
				txtDescripcion.setText(newValue.getDescripcion());
				txtPrecio.setText(String.valueOf(newValue.getPrecio()));
				dtpFecha.setPromptText(newValue.getFecha().toString());
				cbxClientes.setValue(new ClienteDAO().get(newValue.getIdCliente()));
				
				if(newValue!=null) {
					btnEliminar.setDisable(false);
					btnGuardar.setDisable(true);
					btnActualizar.setDisable(false);
				}
			}
		});
	}

	/**
	 * Obtiene los datos recogidos en la interfaz gráfica, crea y añade a la bbdd un evento nuevo
	 */
	@FXML
	public void insertaEvento() {
		String direccion = txtDireccion.getText();
		String descripcion = txtDescripcion.getText();
		LocalDate fecha = null;
		if(dtpFecha.getValue()!=null) {
			fecha=dtpFecha.getValue();
		}
		String clienteCbx=null;
		if(cbxClientes.getValue()!=null) {
			clienteCbx = cbxClientes.getPromptText();
		}
		Mensaje ms = new MensajeConfirm("¿Seguro que desea guardar?");
		ms.muestraMensaje();
		if(((MensajeConfirm) ms).getBt() == ButtonType.OK) {
			if(ValidacionEvento.validaEvento(fecha, direccion, clienteCbx, 
					txtPrecio.getText(), descripcion)) {
				int idCliente = cbxClientes.getValue().getId();
				float precio = Float.parseFloat(txtPrecio.getText());
				Cliente cliente = new ClienteDAO().get(idCliente);
				evento = new Evento(direccion, fecha, descripcion, precio, idCliente, cliente);
				if (new EventoDAO().insert(evento)) {
					new MensajeInfo("Evento insertado con éxito.").muestraMensaje();
					initialize(null, null);
					Logging.infoLogging("Evento insertado con éxito.");
				} else {
					new MensajeError("El evento no se pudo insertar.").muestraMensaje();
					Logging.warningLogging("El evento no se pudo insertar.");
				}
				reset();
			}
		}
	}
	
	/**
	 * Busca un evento en la bbdd determinado por el usuario y lo muestra 
	 */
	@FXML
	public void buscaEvento() {		
		if(!txtBuscar.getText().equals("")) {
			clientes = new ClienteDAO().getAll();
			listaClientes.clear();
			listaClientes = FXCollections.observableArrayList();
			listaClientes.addAll(clientes);
			cbxClientes.setItems(listaClientes);
			eventos = new EventoDAO().getAllDate(txtBuscar.getText());
			for(Evento e : eventos) {
				e.setCliente(new ClienteDAO().get(e.getIdCliente()));
			}
			if (eventos != null) {
				listaEventos.clear();
				listaEventos = FXCollections.observableArrayList();
				listaEventos.addAll(eventos);
				tblEventos.setItems(listaEventos);
				colDireccion.setCellValueFactory(new PropertyValueFactory<Evento, String>("direccion"));
				colFecha.setCellValueFactory(new PropertyValueFactory<Evento, LocalDateTime>("fecha"));
				colDescripcion.setCellValueFactory(new PropertyValueFactory<Evento, String>("descripcion"));
				colPrecio.setCellValueFactory(new PropertyValueFactory<Evento, Float>("precio"));
				colIdCliente.setCellValueFactory(new PropertyValueFactory<Evento, Cliente>("cliente"));
			} else {
				new MensajeInfo("No existen eventos aún.").muestraMensaje();
				Logging.warningLogging("No existen eventos aún.");
				reset();
			}
		} else {
			new MensajeInfo("Debe introducir una fecha.").muestraMensaje();
			Logging.infoLogging("Campo buscar vacio");
		}
	}
	
	/**
	 * Actualiza los datos de un evento dados por el usuario
	 */
	@FXML
	public void actualizaEvento() {
		String direccion = txtDireccion.getText();
		String descripcion = txtDescripcion.getText();
		LocalDate fecha = null;		
		if(!dtpFecha.getPromptText().equals(" ")) {
			fecha= LocalDate.parse(dtpFecha.getPromptText());
		}
		String clienteCbx=null;
		if(cbxClientes.getValue()!=null) {
			clienteCbx = cbxClientes.getPromptText();
		}
		Mensaje ms = new MensajeConfirm("¿Seguro que desea actualizar?");
		ms.muestraMensaje();
		if(((MensajeConfirm) ms).getBt() == ButtonType.OK) {
			if(ValidacionEvento.validaEvento(fecha, direccion, clienteCbx, 
					txtPrecio.getText(), descripcion)) {
				evento = new EventoDAO().get(Integer.parseInt(txtId.getText()));
				evento.setDireccion(txtDireccion.getText());
				evento.setFecha(fecha);
				evento.setDescripcion(txtDescripcion.getText());
				evento.setPrecio(Float.parseFloat(txtPrecio.getText()));		
				if (new EventoDAO().update(evento) == 1) {
					new MensajeInfo("Evento actualizado con éxito.").muestraMensaje();
					initialize(null, null);
					Logging.infoLogging("Evento actualizado con éxito.");
				} else {
					new MensajeError("El evento no se pudo actualizar.").muestraMensaje();
					Logging.warningLogging("El evento no se pudo actualizar.");
				}
				reset();
			}
		}
	}
	
	/**
	 * Elimina de la bbdd un evento dado por el usuario
	 */
	@FXML
	public void eliminaEvento() {
		EventoDAO ed = new EventoDAO();
		Evento evento = ed.get(Integer.parseInt(txtId.getText()));
		if (evento != null) {
			MensajeConfirm ms = new MensajeConfirm("¿Seguro que desea eliminar?");
			ms.muestraMensaje();
			if(ms.getBt() == ButtonType.OK) {
				if (ed.delete(evento) == 1) {
					new MensajeInfo("Evento eliminado con éxito.").muestraMensaje();
					initialize(null, null);
					Logging.infoLogging("Evento eliminado con éxito.");
				} else {
					new MensajeError("El evento no se pudo eliminar.").muestraMensaje();
					Logging.warningLogging("El evento no se pudo eliminar.");
				}
				reset();
			}
		}
	}

	/**
	 * Muestra la vista Historial
	 */
	@FXML
	public void generaHistorial() {	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("historial.fxml"));
			Parent root = loader.load();
			//HistorialController hc = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			Logging.warningLogging(e+"");
		}
	}
	
	/**
	 * Setea a su valor por defecto los elementos de la interfaz gráfica
	 */
	@FXML
	public void nuevo() {
		txtBuscar.setText("");
		txtBuscar.setPromptText("Buscar");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtDireccion.setText("");
		cbxClientes.setValue(null);
		cbxClientes.setPromptText("Seleccione...");
		dtpFecha.setValue(null);
		dtpFecha.setPromptText("Seleccione...");
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
		btnGuardar.setDisable(false);
		initialize(null, null);
	}
	
	/**
	 * Setea a su valor por defecto los elementos de la interfaz gráfica
	 */
	@FXML
	public void reset() {
		txtBuscar.setText("");
		txtBuscar.setPromptText("Buscar");
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtDireccion.setText("");
		cbxClientes.setValue(null);
		cbxClientes.setPromptText("Seleccione...");
		dtpFecha.setValue(null);
		dtpFecha.setPromptText("Seleccione...");
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
		btnGuardar.setDisable(true);
		initialize(null, null);
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
	 * Cambia a la vista "clientes"
	 * @throws IOException
	 */
	@FXML
	private void switchToClientes() throws IOException {
		App.setRoot("clientes");
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
		Mensaje ms = new MensajeConfirm("¿Seguro que desea salir?");
		ms.muestraMensaje();
		if(((MensajeConfirm) ms).getBt() == ButtonType.OK) {
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
	 * Guarda todos los eventos en un archivo
	 */
	@FXML
	private void exportaEventos() {
		Stage stage = (Stage) this.btnClose.getScene().getWindow();
		if(ExportaFichero.guardaEventos(stage)) {
			new MensajeInfo("Archivo guardado correctamente.").muestraMensaje();
			Logging.infoLogging("Archivo guardado correctamente.");
		}
		else {
			new MensajeError("Ocurrió un error al guardar el archivo").muestraMensaje();
			Logging.warningLogging("Ocurrió un error al guardar el archivo");
		}
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