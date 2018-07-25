/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito;

import aerolineaspatito.compraBoletosClases.GenerarPdf;
import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author luis_
 */
public class MenuLoginController implements Initializable {
    
    @FXML
    private Button loguear;
    @FXML
    private TextField usuario;
    @FXML 
    private TextField pass;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        AerolineasPatito a = new AerolineasPatito();
        try{
            String user = (usuario.getText());
            if(user.length() > 0){
                if(user.equals("admin")){
                    String contra = pass.getText();
                    if(contra.length() > 0){
                        if(contra.equals("1234")){ 
                           a.abrirVentana("Menu");
                            GenerarPdf pdf = new GenerarPdf();
                           pdf.generarPdf("gfd", "dg", "gd", "gdg", "da", "dg", "g", "g", "gf", "gff", "gdf", "gdf", "gdf", "gdf", "d", "fgd");

                           a.cerrarVentana(loguear);
                        }else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error al ingresar");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Su contraseña no es correcta");

                            alert.showAndWait();
                        }
                    }else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error al ingresar");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Ingrese una contraseña");

                            alert.showAndWait();
                    }
                }else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error al ingresar");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Su usuario no es correcto");

                            alert.showAndWait();
                }
            }else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error al ingresar");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Ingrese un usuario");

                            alert.showAndWait();
            }
                
        }catch(Exception e){
                        Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error al ingresar");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Error Fatal");

                            alert.showAndWait();
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*ConexionBD bd = new ConexionBD();
        int id = bd.idServicioEspecial("Asiento, derecha");
        
        bd.insertVentas("Plebo", 12, 96, "Chiapas", "Hola@hola", "20.6", 1, "Derecha", "No", "Masculino", id, idVuelo);
        
        //System.out.println("ID: " + datos[0] + ", Precio: " + datos[1]);*/
    }    
    
}
