/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.compraBoletosControllers;

import aerolineaspatito.AerolineasPatito;
import aerolineaspatito.compraBoletosClases.Aerolineas;
import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luis_
 */
public class CantidadBoletosController implements Initializable {
    boolean condicion =true;
    
    //String String = new String();
   CantidadBoletosController instaci1;
    @FXML
    private Label cantidad;
    @FXML
    private Button aceptar;
    AerolineasPatito a = new AerolineasPatito();
    @FXML
    private Button regresar;
    @FXML
    private ComboBox<Aerolineas> municipioSalidaBox;
    @FXML
    private ComboBox<Aerolineas> municipioDestinoBox;
    @FXML
    private ComboBox<String> estadoSalidaBox;
    @FXML
    private ComboBox<String> estadoDestinoBox;
    ObservableList<String> estadosLista=FXCollections.observableArrayList("Chiapas","Oaxaca","Sonora");
    ObservableList<String> municipiosLista=FXCollections.observableArrayList("Tuxtla","Oaxaca", "Hermosillo");
    ObservableList<String> horaLista=FXCollections.observableArrayList("2:00", "4:00","6:00", "8:00", "10:00","12:00", "14:00","16:00", "18:00", "20:00","22:00","24:00");
    @FXML
    private Label letras;
    @FXML
    private HBox mostrar;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ListView<String> listaMostrar;
    @FXML
    private DatePicker diaSalida;
    @FXML
    private DatePicker diaLlegada;
    
    private ArrayList<Aerolineas> estados = new ArrayList();
    @FXML
    private ScrollPane scroll1;
    @FXML
    private ListView<String> listaMostrar1;
    @FXML
    private Button pasarDatos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConexionBD con;
        con = new ConexionBD();
        ArrayList<String> estados = con.SelectAereopuertosEstado();
        ObservableList<String> estadosLista=FXCollections.observableArrayList(estados);
        estadoSalidaBox.setItems(estadosLista);
        //municipioSalidaBox.setItems(municipiosLista);
        estadoDestinoBox.setItems(estadosLista);
       // municipioDestinoBox.setItems(municipiosLista);
        
       instaci1=this;
        // TODO
    }    
    @FXML
    private int pasarParametro()throws IOException{
        ConexionBD bd = new ConexionBD();
        String[] partes;
        String[] partes2;
        String idVuelo2 = "0";
        String cadenaEntera, cadenaEntera2;
        cadenaEntera=this.listaMostrar.getSelectionModel().getSelectedItem();
        if (cadenaEntera!=null) {
            partes=cadenaEntera.split(" ");
            String fecha=partes[1];
            String hora=partes[3];
            String idVuelo=partes[7].substring(0, partes[7].length()-2);
            String idFecha=partes[7].substring(2);
            String precio=partes[9];
            System.out.println("fecha: " + fecha + "    hora: " + hora + "  idVuelo: " + idVuelo + "    idFecha: " + idFecha + "    precio: " + precio);
            if (!condicion) {
                cadenaEntera2=this.listaMostrar1.getSelectionModel().getSelectedItem();
                if (cadenaEntera2!=null) {
                    partes2=cadenaEntera2.split(" ");
                    String fecha2=partes2[1];
                    String hora2=partes2[3];
                    idVuelo2=partes2[7].substring(0, partes2[7].length()-2);
                    String idFecha2=partes2[7].substring(2);
                    String precio2=partes2[9];
                    System.out.println("(De regreso)fecha: " + fecha2 + "    hora: " + hora2 + "  idVuelo: " + idVuelo2 + "    idFecha: " + idFecha2 + "    precio: " + precio2);
                }else{
                    new Alert(Alert.AlertType.ERROR, "Seleccione un vuelo de regreso", ButtonType.OK).showAndWait();
                    return 0;
                }     
            }
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = new Stage();
        AnchorPane root =(AnchorPane)fxmlLoader.load(getClass().getResource("/aerolineaspatito/compraBoletosFXML/compraBoletos.fxml").openStream());
        CompraBoletoController op=(CompraBoletoController)fxmlLoader.getController();
        op.recibirParametros(instaci1,Integer.valueOf(idVuelo),Integer.valueOf(idVuelo2) , Integer.valueOf(this.cantidad.getText()),condicion );
        Scene sc=new Scene(root);
        stage.getIcons().add(new Image("/aerolineaspatito/logoPatitoi.png"));
        stage.setScene(sc);
        stage.alwaysOnTopProperty();
       stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
        a.cerrarVentana(aceptar);     
        }else{
             new Alert(Alert.AlertType.ERROR, "Seleccione un vuelo de ida", ButtonType.OK).showAndWait();
        }
         
         
                  
       return 0;
    }
    
    
    @FXML
    private void menosCinco(ActionEvent event) {
        int cantidad = Integer.valueOf(this.cantidad.getText());
        cantidad=cantidad-5;
        if (cantidad<1) {
            cantidad=1;
        }
        this.cantidad.setText(String.valueOf(cantidad));
        
    }

    @FXML
    private void sumaCinco(ActionEvent event) {
        
        int cantidad = Integer.valueOf(this.cantidad.getText());
        cantidad=cantidad+5;
        this.cantidad.setText(String.valueOf(cantidad));
        
    }

    @FXML
    private void menos(ActionEvent event) {
        int cantidad = Integer.valueOf(this.cantidad.getText());
        cantidad=cantidad-1;
        if (cantidad<1) {
            cantidad=1;
        }
        this.cantidad.setText(String.valueOf(cantidad));
        
    }

    @FXML
    private void suma(ActionEvent event) {
        int cantidad = Integer.valueOf(this.cantidad.getText());
        cantidad=cantidad+1;
        this.cantidad.setText(String.valueOf(cantidad));
    }

    @FXML
    private void aceptar(ActionEvent event) {
       String error1 = this.estadoSalidaBox.getValue();
       Aerolineas error2 =  this.municipioSalidaBox.getValue(); 
       String error3 = this.estadoDestinoBox.getValue(); 
       Aerolineas error4 = this.municipioDestinoBox.getValue(); 
       LocalDate fechaIda=this.diaSalida.getValue();
       LocalDate fechaRegreso=this.diaLlegada.getValue();
       this.listaMostrar.setMinWidth(800);
       String fechita, fechita2;
       
       
       if(error1==null ||error2==null ||error3==null ||error4==null || fechaIda==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos obligatorio.");
            alert.setContentText("Introduce el lugar de salida, destino, fecha.");
            alert.showAndWait();
            
            pintarCuadrosRequeridos();
            if(!condicion){
                if(fechaRegreso==null){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Campos obligatorio.");
                    alert.setContentText("Introduce también la fecha.");
                    alert.showAndWait();           
                }  
            }
       }
       else{
            if(!condicion){                   
                if(fechaRegreso==null){
                    pintarCuadrosRequeridos();
                    System.out.println("Error");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Campos obligatorio.");
                    alert.setContentText("Introduce también la fecha de regreso.");
                    alert.showAndWait();           
                }
                else{
                    try {
                        fechita=fechaIda.toString();
                        fechita2=fechaRegreso.toString();
                        ConexionBD con = new ConexionBD();

                        ObservableList<String> vuelosDisponibles =FXCollections.observableArrayList(con.selectVuelosDisponibles(error2, error4, con.SelectFechas(fechita)));
                        this.listaMostrar.setItems(vuelosDisponibles);
                        if(vuelosDisponibles.size()<1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("No hay boleto");
                            alert.setHeaderText("No se encontró boleto");
                            alert.setContentText("No hay boletos para ese destino en esa fecha");
                            alert.showAndWait();                      
                        }
                        else{
                        vuelosDisponibles =FXCollections.observableArrayList(con.selectVuelosDisponibles(error4, error2, con.SelectFechas(fechita2)));
                        this.listaMostrar1.setItems(vuelosDisponibles);                            
                            if(vuelosDisponibles.size()<1){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("No hay boleto");
                                alert.setHeaderText("No se encontró boleto de regreso");
                                alert.setContentText("No hay boletos para ese destino en esa fecha");
                                alert.showAndWait();               
                            }
                            else{
                                this.scroll.setVisible(true);
                                this.mostrar.setVisible(true);
                                this.letras.setVisible(true);
                                this.scroll1.setVisible(true);
                                this.pasarDatos.setVisible(true);
                                System.out.println("Cosa de ida: " + this.listaMostrar.getSelectionModel().getSelectedItem());
                                System.out.println("Cosa de regreso: " + this.listaMostrar1.getSelectionModel().getSelectedItem());
                                System.out.println("2.- Estado Salida: " + error1 + "   Municipio Salida: " + error2 + "    Estado Destino: " + error3 + "  Municipio Destino: " + error4 + "   Fecha: " + fechita + "  Fecha vuelta: " + fechita2);

                            }
                            
                        }


                    } catch (Exception ex) {
                      //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                  }                    
                }
            }
            else{
                    try {
                        String[] partes;
                        String cadenaEntera;
                        fechita=fechaIda.toString();
                        ConexionBD con = new ConexionBD();
                        ObservableList<String> vuelosDisponibles =FXCollections.observableArrayList(con.selectVuelosDisponibles(error2, error4, con.SelectFechas(fechita)));
                        System.out.println("esto es tamaño "+vuelosDisponibles.size());
                        
                        if(vuelosDisponibles.size()<1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("No hay boleto");
                            alert.setHeaderText("No se encontró boleto");
                            alert.setContentText("No hay boletos para ese destino en esa fecha");
                            alert.showAndWait();     
                        }
                        else{
                            this.scroll.setVisible(true);
                            this.mostrar.setVisible(true);
                            this.letras.setVisible(true);
                            this.listaMostrar.setItems(vuelosDisponibles);
                            this.listaMostrar.setVisible(true);
                            cadenaEntera=this.listaMostrar.getSelectionModel().getSelectedItem();
                            partes=cadenaEntera.split(" ");
                            String fecha=partes[1];
                            String hora=partes[3];
                            String idVuelo=partes[7].substring(0, partes[7].length()-2);
                            String idFecha=partes[7].substring(2);
                            String precio=partes[9];
                            System.out.println("fecha: " + fecha + "    hora: " + hora + "  idVuelo: " + idVuelo + "    idFecha: " + idFecha + "    precio: " + precio);
                            this.pasarDatos.setVisible(true);
                            System.out.println("3.- Estado Salida: " + error1 + "   Municipio Salida: " + error2 + "    Estado Destino: " + error3 + "  Municipio Destino: " + error4 + "Fecha: " + fechita);
                            pintarCuadrosRequeridos();                            
                        }

                    } 
                    catch (Exception ex) {
                      //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                  }                
            }
       }
   
    }
    private void pintarCuadrosRequeridos(){
        String ruta="/aerolineaspatito/compraBoletosCSS/cantidadboletosError.css";
     this.estadoSalidaBox.getStylesheets().add(getClass().getResource(ruta).toExternalForm());  
     this.municipioSalidaBox.getStylesheets().add(getClass().getResource(ruta).toExternalForm());   
     this.estadoDestinoBox.getStylesheets().add(getClass().getResource(ruta).toExternalForm());
     this.municipioDestinoBox.getStylesheets().add(getClass().getResource(ruta).toExternalForm());
     this.diaSalida.getStylesheets().add(getClass().getResource(ruta).toExternalForm());
     this.diaLlegada.getStylesheets().add(getClass().getResource(ruta).toExternalForm());
   
    }
    @FXML
    private void regresar(ActionEvent event) {
        try {
            a.abrirVentana("/aerolineaspatito/Menu");
            a.cerrarVentana(regresar);
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void EstadoSalida(ActionEvent event) {
        ConexionBD con = new ConexionBD();
        estados = con.SelectAereopuertosMunicipio(this.estadoSalidaBox.getValue());
        ObservableList estadosLista=FXCollections.observableArrayList(estados);
        municipioSalidaBox.setItems(estadosLista);
        
        this.estadoDestinoBox.setValue(null);       
    }

    @FXML
    private void EstadoDestino(ActionEvent event) {
        ConexionBD con = new ConexionBD();
        estados = con.SelectAereopuertosMunicipio(this.estadoDestinoBox.getValue());
        ObservableList estadosLista=FXCollections.observableArrayList(estados);
        municipioDestinoBox.setItems(estadosLista);        
        
        this.municipioDestinoBox.setValue(null);
      
    }

    @FXML
    private void visualizar(ActionEvent event) {
        if (condicion) {
            this.diaLlegada.setVisible(condicion);
            condicion=false;
        }else{
            this.diaLlegada.setVisible(condicion);
            condicion=true;
            scroll1.setVisible(false);
        }
        
    }

    @FXML
    private void municipioSalidaBox(ActionEvent event) {
        this.estadoDestinoBox.setValue(null);
        this.municipioDestinoBox.setValue(null);
        
    }

    @FXML
    private void MunicipioDestinoBox(ActionEvent event) {
        
    }

    
    
}
