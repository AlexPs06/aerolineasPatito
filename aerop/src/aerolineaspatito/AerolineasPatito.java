/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author luis_
 */
public class AerolineasPatito extends Application {
    
    public void abrirVentana(String ventana) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ventana+".fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Menú");
        stage.setScene(new Scene(root1));
        stage.show();
    }
 
    
    public void cerrarVentana(Button boton){
        Stage stageClose = (Stage) boton.getScene().getWindow();
        stageClose.close();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MenuLogin.fxml"));
        
        Scene scene = new Scene(root);
        //hola puto
        stage.setScene(scene);
        stage.show();
        /*subio*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
