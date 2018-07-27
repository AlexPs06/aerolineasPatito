/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.compraBoletosControllers;

import aerolineaspatito.AerolineasPatito;
import aerolineaspatito.compraBoletosClases.DatosComprador;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luis_
 */
public class CompraBoletoController implements Initializable {
   // ObservableList<String> discapacidadLista=FXCollections.observableArrayList("Silla de ruedas", "");
    ObservableList<String> asientosLista=FXCollections.observableArrayList("Ventana", "Medio", "Pasillo");
    ObservableList<String> claseLista=FXCollections.observableArrayList("Turista", "Ejecutivo", "VIP");
    
    int cantidadBoletos = 0;
    int idVueloida = 0;
    int idVueloregreso = 0;
    boolean seb = false;
    String inca = null;
    boolean condicion;
    @FXML
    private ToggleGroup servicios;
    @FXML
    private ToggleGroup sexo;
    /*@FXML
    private ToggleGroup sE;*/
    @FXML
    private Button cancelar;
    @FXML
    private RadioButton servicioSi;
    @FXML
    private RadioButton servicioNo;
    @FXML
    private TextField nombre;
    @FXML
    private TextField edad;
    @FXML
    private RadioButton hombre;
    @FXML
    private RadioButton mujer;
    @FXML
    private RadioButton otro;
    @FXML
    private TextField telefono;
    @FXML
    private TextField correo;
    @FXML
    private TextField equipaje;
    @FXML
    private Button aceptar;
    @FXML
    private ComboBox<String> claseBox;
    @FXML
    private ComboBox<String> asientoBox;
    @FXML
    private TextField direccion;
    @FXML
    private Button regresar;
    @FXML
    private RadioButton asiento;
    @FXML
    private RadioButton incapacidad;
    ConexionBD con = new ConexionBD();
    ArrayList<DatosComprador> datosComprador;
    CantidadBoletosController lol;
    int contadorBoton = 0;
    @FXML
    private Label precio;
    @FXML
    private ToggleGroup sE;
    @FXML
    private ImageView imagenFlecha;
    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView imagenBoleto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
       //Image imagenBoleto=new Image(getClass().getResource("/aerolineaspatito/compraBoletosFXML/").toExternalForm());
       
        this.anchor.setStyle(getClass().getResource("/aerolineaspatito/compraBoletosFXML/stiloComprarboleto.css").toExternalForm());
        Image imagen = new Image (getClass().getResource("/aerolineaspatito/compraBoletosFXML/volverNuevo.png").toExternalForm());
       imagenBoleto=new ImageView(imagen);
       
 
       this.imagenFlecha = new ImageView(imagen);
        asientoBox.setItems(asientosLista);
        claseBox.setItems(claseLista);
        datosComprador=new ArrayList<>();
        //discapacidadBox.setItems(discapacidadLista);
    }
        
    
    public void recibirParametros(CantidadBoletosController stage1, int idVueloIda, int idVueloRegreso, int cantidad, boolean condicion){
            this.idVueloida=idVueloIda;
            int cost = con.selectPrecio(idVueloIda);
            precio.setText("Precio: " + cost);
            this.condicion=condicion;
            if (!condicion) {
                
                this.idVueloregreso= idVueloRegreso;
                int cost2 = con.selectPrecio(idVueloRegreso);
                precio.setText("Precio: $" + cost+cost2);
            }
            this.cantidadBoletos=cantidad;
            
            lol=stage1;
    } 
    
    @FXML
    private void regresar(ActionEvent event) {
         try {
            AerolineasPatito a = new AerolineasPatito();
            a.abrirVentana("/aerolineaspatito/compraBoletosFXML/cantidadBoletos");
            a.cerrarVentana(regresar);
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try {
            AerolineasPatito a = new AerolineasPatito();
            a.abrirVentana("/aerolineaspatito/Menu");
            a.cerrarVentana(cancelar);
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
    boolean aceptado=true;
    boolean servicioExtra=false;

    String sexo=null;
    try {
        RadioButton temp =(RadioButton) this.sexo.getSelectedToggle(); 
        sexo =temp.getText();
        if(seb = true){
            
        }
    } catch (Exception e) {
        aceptado=false;
        //new Alert(Alert.AlertType.ERROR, "Favor de elegir un sexo", ButtonType.OK).showAndWait();
    }
    

    String nombre = this.nombre.getText();
    String edad = this.edad.getText();
    String telefono=this.telefono.getText();
    String direccion=this.direccion.getText();
    String correo=this.correo.getText();
    String equipaje=this.equipaje.getText();
    

    String clase=this.claseBox.getValue();
    
    //String discapacidad=this.discapacidadBox.getValue();
    
    
    
    
  
        if (nombre == null || sexo==null || edad == null || telefono== null || direccion== null ||
    correo== null || equipaje== null || clase== null){
            new Alert(Alert.AlertType.ERROR, "Los campos nombre, sexo, edad, felefono, direccion, correo, equipaje, clase son obligatorios ", ButtonType.OK).showAndWait();

        }else{
            int idSE = 0; 
            
                if(inca == null){
                    if(this.asientoBox.getValue() == null){
                        idSE = con.idServicioEspecial("NoSE");
                    }else{
                        String asiento=this.asientoBox.getValue();
                        idSE = con.idServicioEspecial("Asiento, "+asiento);
                        System.out.println("Asiento, " + asiento);
                    }
                }else{
                    idSE = con.idServicioEspecial(inca);
                    System.out.println("SR");
                }
                
                System.out.println("ID " + idSE);
                //con.insertIntoDatosComprador(nombre, edad, telefono, direccion, correo, equipaje, clase, asiento, discapacidad, sexo);
                if (!tieneLetras(telefono) && !tieneLetras(edad) && ! tieneLetras(equipaje) ) {
                    if (!tieneNumeros(nombre)) {
                        DatosComprador cliente = new DatosComprador(nombre,Integer.parseInt(edad), sexo, correo, direccion, telefono, equipaje, new ConexionBD().idClase(clase), idSE, 0);
                        if (Integer.parseInt(equipaje)<=25 ) {
                            this.datosComprador.add(cliente);
                            validar();
                        }
                        else{
                            
                            ButtonType buton = new Alert(Alert.AlertType.ERROR, "Equipaje maximo excedido ¿desea continuar? se le cobrara un porcentaje extra por kilo de equipaje extra", ButtonType.YES, ButtonType.NO).showAndWait().get();
                                if (buton.getText().equals("Sí")) {
                                    int extra = (Integer.parseInt(equipaje) - 25) * 50;
                                    this.datosComprador.add(cliente);
                                    new Alert(Alert.AlertType.ERROR, "El costo extra es: " + extra, ButtonType.OK).showAndWait();

                                    
                                    validar();

                                }
                            
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "campo nombre invalido", ButtonType.OK).showAndWait();
                    
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "campo telefono, edad o equipaje invalidos", ButtonType.OK).showAndWait();
                
                }
                    
        }
       
        
        
        
    }
    public boolean tieneNumeros(String cadena){
        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isLetter(cadena.charAt(i))) {
                if (cadena.charAt(i)!=' ') {
                    return true;    
                }
                
            }
        }
        return false;
    }
    
    public boolean tieneLetras(String cadena){
        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void serviciosExtra(ActionEvent event) {
        
        RadioButton temp =(RadioButton) this.servicios.getSelectedToggle();
        String eleccion =temp.getText();
        if (eleccion.equals("Si")) {
            this.asiento.setVisible(true);
            this.incapacidad.setVisible(true); 
            seb = true;
        }else{
            this.asiento.setVisible(false);
            this.asientoBox.setVisible(false);
            this.incapacidad.setVisible(false);
            seb = false;
        }
        
    }
    @FXML
    private void seleccionarServicio(ActionEvent event){
        try{
                RadioButton temp2 = (RadioButton) this.sE.getSelectedToggle();
                      String elec = temp2.getText();
                      if(elec.equals("Elegir asiento"))
                          this.asientoBox.setVisible(true);
                      else if(elec.equals("Incapacidad")){
                          this.asientoBox.setVisible(false);
                          inca = "Discapacidad";
                      }
            }catch(Exception ex){
                new Alert(Alert.AlertType.ERROR, "Favor de elegir un servicio especial", ButtonType.OK).showAndWait();
            }
    }

    private void validar() {
            if(contadorBoton == cantidadBoletos-1){
                
                for (int i = 0; i < this.datosComprador.size(); i++) {
                    System.out.println(this.datosComprador.get(i).toString());
                }
                AerolineasPatito a = new AerolineasPatito();
                try{
                    CompraBoletoController instaci1 = null;
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Stage stage = new Stage();
                    AnchorPane root =(AnchorPane)fxmlLoader.load(getClass().getResource("/aerolineaspatito/compraBoletosFXML/Previsualizar.fxml").openStream());
                    PrevisualizarController op=(PrevisualizarController)fxmlLoader.getController();
                    op.recibirParametros(instaci1,datosComprador,idVueloida,idVueloregreso );
                    stage.getIcons().add(new Image(getClass().getResource("/aerolineaspatito/logoPatitoi.png").toExternalForm()));

                    Scene sc=new Scene(root);
                    stage.setScene(sc);
                    stage.show();
                    a.cerrarVentana(aceptar);
                }catch(Exception e){
                    System.out.println("Nel" + e);
                }
            }
            else{
                this.nombre.setText(null);
                this.edad.setText(null);
                this.telefono.setText(null);
                this.direccion.setText(null);
                this.correo.setText(null);
                this.equipaje.setText(null);
                this.sexo.selectToggle(null);
                this.sE.selectToggle(null);
                this.servicioNo.setSelected(false);
                this.servicioSi.setSelected(false);
                this.claseBox.setValue(null);
                inca = null;
                this.asientoBox.setValue(null);
                this.incapacidad.setVisible(false);
                this.asiento.setVisible(false);
                this.asientoBox.setVisible(false);
            }
            contadorBoton++; 
    }
    
}
