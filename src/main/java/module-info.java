module Camara.Jose.Eventos {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.xml.bind;
	requires javafx.base;
	//requires mysql.connector.java;
	requires javafx.graphics;
	requires java.desktop;

    opens Camara.Jose.Eventos to javafx.fxml;
    opens Camara.Jose.Eventos.utils to java.xml.bind;
    opens Camara.Jose.Eventos.model.DataObject;
    exports Camara.Jose.Eventos;
}
