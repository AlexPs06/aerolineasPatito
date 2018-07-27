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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 *
 * @author luis_
 */
public class CancelarBoletoController implements Initializable {
    @FXML
    private TextField numeroBoleto2;      
    @FXML
    private Button volver;
    @FXML
    private Button cancelar;
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
    private void handleButtonAction(ActionEvent event) {
        ConexionBD con;
        con = new ConexionBD();
        String numeroBoleto;
        numeroBoleto=numeroBoleto2.getText();
        boolean pasa=con.comprobar(numeroBoleto);
        if(pasa){
            try{
                int nBoleto=Integer.parseInt(numeroBoleto);
                con.Cancelar(numeroBoleto);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Cancelado");
                alert.setHeaderText("Boleto cancelado.");
                alert.setContentText("El boleto " + numeroBoleto + " ha sido cancelado.");
                alert.showAndWait();

                try {
                    a.abrirVentana("/aerolineaspatito/Menu");
                    a.cerrarVentana(cancelar);
                } catch (IOException ex) {
                //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                alert.showAndWait();
            }
            catch(NumberFormatException exception){
                if(numeroBoleto.equals("")){
                    errorVacio();
                }
                else{
                    errorCaracter(numeroBoleto);
                }
            }              
        }
        else{
            new Alert(Alert.AlertType.ERROR, "Es boleto no se encuentra en el sistema.", ButtonType.OK).showAndWait();
        }
        
    
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
    }    
    
}

