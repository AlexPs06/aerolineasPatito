/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.cambioBoletoControllers;

import aerolineaspatito.AerolineasPatito;
import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author luis_
 */
public class CambiarBoletoController implements Initializable {
    
    ObservableList<String> asientosLista=FXCollections.observableArrayList("Ventana", "Medio", "Pasillo");
    
    @FXML
    private ComboBox<String> asientoBox;
    
    @FXML
    private TextField numeroVuelo1;
    @FXML
    private TextField numeroBoleto1;
    @FXML
    private TextField nuevaHora1;                 
    @FXML
    private Button volver;
    @FXML
    private Button aceptar;
    @FXML
    private Label letras;
    @FXML
    private HBox mostrar;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private ListView<String> listaMostrar;
    @FXML
    private ListView<String> listaMostrar1;                   
    
    
    @FXML
    private void volver(ActionEvent event){
        try {
            AerolineasPatito a = new AerolineasPatito();
            a.abrirVentana("/aerolineaspatito/Menu");
            a.cerrarVentana(volver);
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void buscar(ActionEvent event){
        ArrayList <String> municipios = new ArrayList();
        ConexionBD con;
        con = new ConexionBD();  
        String numeroBoleto=numeroBoleto1.getText();
        boolean aceptado=false, pasa=false;
        try{
            int nBoleto=Integer.parseInt(numeroBoleto);
            aceptado=true;
            System.out.println("ENTró: "  + aceptado);
        }
        catch(NumberFormatException exception){
            System.out.println("NO ENTRA: " + aceptado);
            aceptado=false;
        }
        System.out.println("ACEPTADO: " + aceptado);
        if(aceptado==true){
            pasa=con.comprobar(numeroBoleto);
        }
        else{
            errorCaracter(numeroBoleto);
        }
        
        if(pasa==true && aceptado==true){
            System.out.println("ENTRË");
            try{
               int nBoleto=Integer.parseInt(numeroBoleto);
               municipios=con.buscarMunicipios(numeroBoleto);
               System.out.println("Municipios: "+ municipios.get(0) + "    " + municipios.get(1));
               String idSalida=municipios.get(0), idDestino=municipios.get(1);
               this.scroll.setVisible(true);
               this.aceptar.setVisible(true);
               ObservableList<String> vuelosDisponibles =FXCollections.observableArrayList(con.selectVuelosDisponiblesCambio(idSalida, idDestino));
               this.listaMostrar.setItems(vuelosDisponibles);   
           }
           catch(NumberFormatException exception){

           }           
        }
        else{
            if(aceptado==true)
                new Alert(Alert.AlertType.ERROR, "Es boleto no se encuentra en el sistema.", ButtonType.OK).showAndWait();
        }

    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        ConexionBD con;
        con = new ConexionBD();
        String  numeroBoleto, asiento, asiento2=null;
        String[] partir;
        numeroBoleto=numeroBoleto1.getText();
        asiento = asientoBox.getValue();
        String cadenaEntera, nuevoVuelo;
        cadenaEntera=this.listaMostrar.getSelectionModel().getSelectedItem();
        System.out.println("LISTA: " + cadenaEntera);
        partir=cadenaEntera.split("/");
        System.out.println("PARTIR 1: " + partir[0] + "   PARTIR 2: " + partir[1]);
        nuevoVuelo=partir[0].substring(partir[0].length()-1);
        System.out.println("NUEVO VUELO: " + nuevoVuelo);
        if(asiento.equals("Ventana")){
            asiento2="36";
        }
        if(asiento.equals("Medio")){
            asiento2="37";
        }
        if(asiento.equals("Pasillo")){
            asiento2="38";
        }    
        //System.out.println("TODO BIEN:        #Boleto: " + numeroBoleto + "  #Vuelo: " + numeroVuelo + " nuevaHora: " + nuevaHora + " Asiento: " + asiento);
       
                
    }
    
    public void errorVacio(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Campo obligatorio.");
        alert.setContentText("Introduce el número de boleto y vuelo.");
        alert.showAndWait();
    }
    
    public void errorCaracter(String cosa){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Solo se aceptan caracteres numéricos.");
            alert.setContentText("\" " + cosa +" \" no válido.");
            alert.showAndWait();       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        asientoBox.setItems(asientosLista);
        asientoBox.setValue("Ventana");  
    }    
    
}

