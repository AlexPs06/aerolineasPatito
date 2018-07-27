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
    private TextField numeroBoleto1;
    @FXML
    private Button volver;
    @FXML
    private Button aceptar;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ListView<String> listaMostrar;
    AerolineasPatito a = new AerolineasPatito();
   
    
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
        ArrayList <String> info = new ArrayList();
        ConexionBD con;
        con = new ConexionBD();
        String  numeroBoleto, asiento, clase=null, se=null, asiento2=null;
        String[] partir;
        numeroBoleto=numeroBoleto1.getText();
        asiento = asientoBox.getValue();
        String cadenaEntera, nuevoVuelo;
        cadenaEntera=this.listaMostrar.getSelectionModel().getSelectedItem();
        System.out.println("LISTA: " + cadenaEntera);
        if(cadenaEntera==null){
            errorVacio2();
        }
        else{
            partir=cadenaEntera.split("/");
            System.out.println("PARTIR 1: " + partir[0] + "   PARTIR 2: " + partir[1]);
            nuevoVuelo=partir[0].substring(partir[0].length()-1);
            System.out.println("NUEVO VUELO: " + nuevoVuelo + " BOLETO: " + numeroBoleto);
            if(numeroBoleto.equals(nuevoVuelo)){
                errorBoleto();
            }
            else{
                if(asiento.equals("Ventana")){
                    asiento2="36";
                }
                if(asiento.equals("Medio")){
                    asiento2="37";
                }
                if(asiento.equals("Pasillo")){
                    asiento2="38";
                }
                
                con.cambiar(numeroBoleto, asiento2, nuevoVuelo);
                String origen = con.obtenerSalida(numeroBoleto);
                String destino = con.obtenerDestino(numeroBoleto);
                info=con.obtenerInfoUser(numeroBoleto);
                String precio=con.obtenerPrecio(numeroBoleto);
                String dia=con.obtenerDia(numeroBoleto);
                if(info.get(0).equals("1")){
                    clase="Turista";
                }
                if(info.get(0).equals("2")){
                    clase="Ejecutivo";
                }
                if(info.get(0).equals("3")){
                    clase="VIP";
                }
                //SE
               if(info.get(1).equals("36")){
                    se="Asiento, Ventana";
                }
                if(info.get(1).equals("37")){
                    se="Asiento, Medio";
                }
                if(info.get(1).equals("38")){
                    se="Asiento, Pasillo";
                }
                if(info.get(1).equals("39")){
                    se="Discapacidad";
                }     
                if(info.get(1).equals("40")){
                    se="NoSE";
                }
                
                System.out.println("TODO:");
                System.out.println("Origen: " + origen + " Destino: " + destino + " numVuelo: " + numeroBoleto + " clase: " + clase + " servicios: " + se + " nombre: " + info.get(2) + " edad: "
                           + info.get(3) + " sexo: " + info.get(4) + " numT: " + info.get(5) + " direc: " + info.get(6) + " peso: " + info.get(7) + " correo: " + info.get(8) + " precio: " + precio + " dia: " + dia);             
                

                GenerarPdf pdf = new GenerarPdf();
                pdf.generarPdf(origen, destino, "", "",numeroBoleto , clase, se, info.get(2), info.get(3) , info.get(4), info.get(5), info.get(6), info.get(7), info.get(8), precio , dia);
    
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Cambiado");
                alert.setHeaderText("Boleto cambiado.");
                alert.setContentText("El boleto ha sido cambiado. Se generará el nuevo boleto");
                alert.showAndWait();
                
                
                try {
                    a.abrirVentana("/aerolineaspatito/Menu");
                    a.cerrarVentana(aceptar);
                } catch (IOException ex) {
                //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
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
    public void errorVacio2(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Campo obligatorio.");
        alert.setContentText("Selecciona un vuelo.");
        alert.showAndWait();
    }
    
    public void errorCaracter(String cosa){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Solo se aceptan caracteres numéricos.");
            alert.setContentText("\" " + cosa +" \" no válido.");
            alert.showAndWait();       
    }
    
    public void errorBoleto(){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Es el mismo vuelo.");
            alert.setContentText("No puedes seleccionar el mismo vuelo.");
            alert.showAndWait();       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        asientoBox.setItems(asientosLista);
        asientoBox.setValue("Ventana");  
    }     
    
}

