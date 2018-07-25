/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito;

import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author pablo
 */
public class MenuController implements Initializable {
    @FXML
    private Button venta;
    @FXML
    private Button cambio;
    @FXML
    private Button cancelar;
    @FXML
    private Button mascota;
    @FXML
    private Button cerrarSesion;
    AerolineasPatito a = new AerolineasPatito();
    /**
     * Initializes the controller class.
     */
    
    
    
    @FXML
    private void venderBoleto(ActionEvent event) throws IOException{
        a.abrirVentana("/aerolineaspatito/compraBoletosFXML/cantidadBoletos");
        a.cerrarVentana(venta);
    }
    @FXML
    private void cambiarBoleto(ActionEvent event) throws IOException{
        a.abrirVentana("/aerolineaspatito/cambioBoletoFXML/cambiarBoleto");
        a.cerrarVentana(cambio);
    }
    @FXML
    private void cancelarBoleto(ActionEvent event) throws IOException{
        a.abrirVentana("/aerolineaspatito/cambioBoletoFXML/cancelarBoleto");
        a.cerrarVentana(cancelar);
    }
    @FXML
    private void agregarMascota(ActionEvent event) throws IOException{
        a.abrirVentana("/aerolineaspatito/agregarMascotaFXML/agregarMascota");
        a.cerrarVentana(mascota);
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException{
        a.cerrarVentana(cerrarSesion);
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
