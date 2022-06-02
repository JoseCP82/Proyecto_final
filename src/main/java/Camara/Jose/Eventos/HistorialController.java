package Camara.Jose.Eventos;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import Camara.Jose.Eventos.model.DAO.EventoDAO;
import Camara.Jose.Eventos.model.DataObject.Cliente;
import Camara.Jose.Eventos.model.DataObject.Evento;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistorialController implements Initializable {

	/**
	 * Atributos de clase
	 */
	@FXML private Button btnCerrar;
	@FXML private TableView<Evento> tblHistorial;
	@FXML private TableColumn<Evento, String> colDireccion;
	@FXML private TableColumn<Evento, LocalDateTime> colFecha;
	@FXML private TableColumn<Evento, Float> colPrecio;
	@FXML private TableColumn<Evento, Cliente> colNombre;
	@FXML private RadioButton rdbAyto;
	@FXML private RadioButton rdbRepre;
	@FXML private RadioButton rdbPart;
	@FXML private RadioButton rdbOtros;	
	private Collection<Evento> eventos;
	private ObservableList<Evento> listaEventos;
	
	/**
	 * Inicia los elementos usados en la interfaz gráfica
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		eventos = new ArrayList<>();
		listaEventos = FXCollections.observableArrayList();
		ToggleGroup btnGroup = new ToggleGroup();
		rdbAyto.setToggleGroup(btnGroup);
		rdbRepre.setToggleGroup(btnGroup);
		rdbPart.setToggleGroup(btnGroup);
		rdbOtros.setToggleGroup(btnGroup);
		
		eventos=new EventoDAO().getHistorial("Ayuntamiento");
		listaEventos.addAll(eventos);
		tblHistorial.setItems(listaEventos);
		
		btnGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton)btnGroup.getSelectedToggle();               
                if (rb != null) {                                
                    tblHistorial.getItems().clear();
            		eventos=new EventoDAO().getHistorial(rb.getText());            		
            		listaEventos.addAll(eventos);            		
            		tblHistorial.setItems(listaEventos);      
                }
            }
        });
		
		colDireccion.setCellValueFactory(new PropertyValueFactory<Evento, String>("direccion"));
		colFecha.setCellValueFactory(new PropertyValueFactory<Evento, LocalDateTime>("fecha"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<Evento, Float>("precio"));
		colNombre.setCellValueFactory(new PropertyValueFactory<Evento, Cliente>("cliente"));
	}

	/**
	 * Cierra la ventana historial
	 */
	@FXML
	private void closeWindow() {
		Stage stage = (Stage) this.btnCerrar.getScene().getWindow();
		stage.close();
	}
}
