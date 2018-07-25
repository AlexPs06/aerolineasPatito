/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.compraBoletosControllers;

import aerolineaspatito.AerolineasPatito;
import aerolineaspatito.compraBoletosClases.DatosComprador;
import aerolineaspatito.compraBoletosClases.GenerarPdf;
import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author luis_
 */
public class PrevisualizarController implements Initializable {
    CompraBoletoController compra=null;
    ArrayList<DatosComprador> clientes=null;
    int idVueloida=0;
    int idVueloRegreso=0;
    int x = 0;
    @FXML
    private TextArea datosUsuarios;
    @FXML
    private Label costo;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private Label VueloRedondo;
    @FXML
    private Label vueloIda;
    @FXML
    private Label vueloRegreso;
    @FXML
    private Label fechas;
    @FXML
    private TextField cantidad;

    String regreso;
    String origen;
    String []vueloRegresoArreglo;
    String []vueloIdaArreglo;
    int costoIda =  0;
    int costoRegreso = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        costo.setText("El costo total es de: $" + 15);
    }    

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void aceptar(ActionEvent event) {
        try{
            AerolineasPatito a = new AerolineasPatito();
            double cantidad = Double.parseDouble(this.cantidad.getText());
            double cambio = cantidad - x;
            if(cambio >= 0){
                new Alert(Alert.AlertType.INFORMATION, "Cambio: " + cambio, ButtonType.OK).showAndWait();
                ConexionBD con  = new ConexionBD();
                for (int i = 0; i < this.clientes.size(); i++) {
                   int id= con.insertVentas(this.clientes.get(i).getNombre(), this.clientes.get(i).getEdad(),this.clientes.get(i).getTelefono(),this.clientes.get(i).getDireccion(), this.clientes.get(i).getCorreo(), this.clientes.get(i).getPesoEquipaje(), this.clientes.get(i).getIdClase(), this.clientes.get(i).getSexo(), this.clientes.get(i).getIdServicioEspecial(), idVueloida);
                    GenerarPdf pdf = new GenerarPdf();
                    
                    System.out.println("esto es id" +id);
                    String nombre = clientes.get(i).getNombre();
                    int edad =clientes.get(i).getEdad();
                    String sexo =clientes.get(i).getSexo();
                    String correo=clientes.get(i).getCorreo();
                    String direccion = clientes.get(i).getDireccion();
                    String telefono = clientes.get(i).getTelefono();
                    String pesoEquipaje = clientes.get(i).getPesoEquipaje();
                    int idClase = clientes.get(i).getIdClase(); 
                    int idServicioEspecial = clientes.get(i).getIdServicioEspecial();
                    String servicio = con.SelectServicioEspecial(idServicioEspecial); 
                    String clase =con.SelectClase(idClase); 
                    int costoExtra=costoExtra(clase, pesoEquipaje);     
                    pdf.generarPdf(origen, regreso, "", "",String.valueOf(id) , clase, servicio, nombre, String.valueOf(edad), sexo, telefono, direccion, pesoEquipaje, correo, (costoExtra+costoIda+costoRegreso)+"", con.SelectFecha(Integer.parseInt(vueloIdaArreglo[0]) ));
                    //pdf.generarPdf("", "", "", "", "da", "", "", "", "", "", "", "", "", "", "", "");

                    if (idVueloRegreso!=0) {
                        con.insertVentas(this.clientes.get(i).getNombre(), this.clientes.get(i).getEdad(),this.clientes.get(i).getTelefono(),this.clientes.get(i).getDireccion(), this.clientes.get(i).getCorreo(), this.clientes.get(i).getPesoEquipaje(), this.clientes.get(i).getIdClase(), this.clientes.get(i).getSexo(), this.clientes.get(i).getIdServicioEspecial(), idVueloRegreso);                    
                        pdf.generarPdf(regreso, origen , "", "", String.valueOf(id), clase, servicio, nombre, String.valueOf(edad), sexo, telefono, direccion, pesoEquipaje, correo, (costoExtra+costoIda+costoRegreso)+"", con.SelectFecha(Integer.parseInt(vueloRegresoArreglo[0]) ));
                    
                    }
                    
                }
                
                a.abrirVentana("/aerolineaspatito/Menu");
                a.cerrarVentana(aceptar);
            }else{
                new Alert(Alert.AlertType.ERROR, "La cantidad ingresada es insuficiente", ButtonType.OK).showAndWait();
            }
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "Ingrese un numero", ButtonType.OK).showAndWait();
        }
    }
    public void recibirParametros(CompraBoletoController compra,ArrayList<DatosComprador> clientes,int idVueloida, int idVueloRegreso ){
        ConexionBD con = new ConexionBD();
        this.compra=compra;
        this.clientes=clientes;
        this.idVueloRegreso=idVueloRegreso;
        this.idVueloida=idVueloida;
        this.datosUsuarios.setText("");
        costoIda =con.selectPrecio(idVueloida);
        costoRegreso=0;
        if (idVueloRegreso!=0) {
            costoRegreso= con.selectPrecio(idVueloRegreso);        
        }
        int costoFinalExtra=0;
        for (int i = 0; i < this.clientes.size(); i++) {
            String nombre = clientes.get(i).getNombre();
            int edad =clientes.get(i).getEdad();
            String sexo =clientes.get(i).getSexo();
            String correo=clientes.get(i).getCorreo();
            String direccion = clientes.get(i).getDireccion();
            String telefono = clientes.get(i).getTelefono();
            String pesoEquipaje = clientes.get(i).getPesoEquipaje();
            int idClase = clientes.get(i).getIdClase(); 
            int idServicioEspecial = clientes.get(i).getIdServicioEspecial();
            String servicio = con.SelectServicioEspecial(idServicioEspecial); 
            String clase =con.SelectClase(idClase); 
            
            int costoExtra=costoExtra(clase, pesoEquipaje);
            costoFinalExtra=costoFinalExtra+costoExtra;
            this.datosUsuarios.appendText(""
                    + "Nombre: "+nombre +"\n"
                    + "Edad: "+edad +"\n"
                    + "Sexo: "+sexo +"\n"
                    + "Correo "+correo +"\n"
                    + "Direccion "+direccion +"\n"
                    + "Telefono "+telefono +"\n"
                    + "Peso Equipaje "+pesoEquipaje +"\n"
                    + "Clase "+clase+"\n"
                    + "Servicio especial: "+servicio +"\n"
                    +"Costo extra que tiene el boleto : $"+costoExtra+"\n"
                    +"Costo de boleto: $"+(costoExtra+costoIda+costoRegreso)        
                    +"\n"
                    );
        }
        colocarVuelo(idVueloida, idVueloRegreso, costoFinalExtra,costoIda, costoRegreso);
    }
    public int costoExtra(String clase, String pesoEquipaje ){
        int costoExtra=0;
        int peso = Integer.parseInt(pesoEquipaje);
        switch(clase){
            case "Ejecutivo":
                costoExtra=costoExtra+500;
                break;
            case "VIP":
                costoExtra=costoExtra+800;
                break;
            }
        if (peso>25) {
            costoExtra= costoExtra+((peso-25)*50);
        }
        return costoExtra;
    }
    private void colocarVuelo(int idVueloida, int idVueloRegreso, int costoFinalExtra, int costoIda, int costoRegreso){
        
        ConexionBD con = new ConexionBD();
        vueloIdaArreglo=con.SelectVuelo(idVueloida).split(" ");
        if (idVueloRegreso!=0) {
            vueloRegresoArreglo=con.SelectVuelo(idVueloRegreso).split(" ");
            this.VueloRedondo.setText("Vuelo redondo");
            this.fechas.setText("Fecha ida: " + con.SelectFecha(Integer.parseInt(vueloIdaArreglo[0])) 
                    + "\n" + "Fecha regreso: " + con.SelectFecha(Integer.parseInt(vueloRegresoArreglo[0])));
        }
        else{
            this.VueloRedondo.setText("Vuelo normal");
            this.fechas.setText("Fecha: "  + con.SelectFecha(Integer.parseInt(vueloIdaArreglo[0])));
        }
        origen = con.SelectAereopuertosEstado(Integer.parseInt(vueloIdaArreglo[1]));
        regreso = con.SelectAereopuertosEstado(Integer.parseInt(vueloIdaArreglo[2]));
        this.vueloIda.setText("Origen:  "+origen);
        this.vueloRegreso.setText("Destino: "+regreso);
  
         x=costoFinalExtra+ ((costoIda+costoRegreso)*this.clientes.size()); 
        this.costo.setText("El costo final es: $"+x);
        
    }
}
