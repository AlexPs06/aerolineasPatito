/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.agregarMacostaControllers;

import aerolineaspatito.AerolineasPatito;
import aerolineaspatito.compraBoletosClases.GenerarPdf;
import aerolineaspatito.conexionBaseDatos.ConexionBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



/**
 * FXML Controller class
 *
 * @author pablo
 */
public class AgregarMascotaController implements Initializable {
    AerolineasPatito a = new AerolineasPatito();
    
    @FXML
    private TextArea ta;
    @FXML
    private Button buscar;
    @FXML
    private CheckBox vacuna;
    @FXML
    private Button cancelar;
    @FXML
    private TextField peso;
    @FXML
    private ChoiceBox tam;
    @FXML
    private TextField Nombre;
    @FXML
    private Button aceptar;
    @FXML
    private TextField folio;
    @FXML
    private Label kg;
    String folius;
    String datos[];
    @FXML
    private Label label;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarFolio(ActionEvent event) {
        ConexionBD bd = new ConexionBD();
        try{    
               folius = folio.getText();
                boolean cancelado = bd.comprobar(folius);
                if(cancelado == true){
                    boolean existente = bd.ValidarFolioMascotas(folius);
                    if(folius.length() > 0){
                        if(existente = true){
                            ta.setVisible(true);
                            datos  = bd.SelectMascotasComprador(Integer.parseInt(folius));
                            ta.setText(datos[0]);
                            aceptar.setVisible(true);
                            label.setVisible(true);
                            tam.setVisible(true);
                            Nombre.setVisible(true);
                            peso.setVisible(true);
                            vacuna.setVisible(true);
                            kg.setVisible(true);
                            tam.setItems(FXCollections.observableArrayList("Chica", "Mediana", "Grande"));
                        }else{
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Error al buscar folio");
                                    alert.setHeaderText("Parece que hay un error");
                                    alert.setContentText("Debe ingresar un folio valido");

                                    alert.showAndWait();
                        }
                    }else{
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Error al buscar folio");
                                    alert.setHeaderText("Parece que hay un error");
                                    alert.setContentText("Debe ingresar un folio");

                                    alert.showAndWait();

                    }
                   
                }else
                     new Alert(Alert.AlertType.ERROR, "El boleto ha sido cancelado", ButtonType.OK).showAndWait();
        }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error al buscar folio");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Error Fatal");

                            alert.showAndWait();
        }
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        try {
            a.abrirVentana("/aerolineaspatito/Menu");
            a.cerrarVentana(cancelar);
        } catch (Exception e) {
        }
 
        
    }
    
    
    

    @FXML
    private void aceptar(ActionEvent event) {
        ConexionBD bd = new ConexionBD();
        if(tam.getValue() == null){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error al agregar a mascota");
                            alert.setHeaderText("Parece que hay un error");
                            alert.setContentText("Elija una dimension de jaula valida");

                            alert.showAndWait();
        }else if(Nombre.getText().length() == 0){
            new Alert(Alert.AlertType.ERROR, "Debe ingresar un nombre", ButtonType.OK).showAndWait();
        }else{
                String cb = String.valueOf(tam.getValue());
                if(vacuna.isSelected()){
                    String cartilla = "vacunado";
                    System.out.println("Cartilla: " + cartilla);
                }else{
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Error al agregar a mascota");
                                    alert.setHeaderText("Parece que hay un error");
                                    alert.setContentText("La mascota debe estar vacunada contra la rabia");

                                    alert.showAndWait();
                }
                try{
                    Double weight = Double.parseDouble(peso.getText());
                    double finalWeight = bd.obtenerPeso(Integer.parseInt(folius));
                    System.out.println("FW: " + finalWeight);
                    double cobrar = 0;
                    if (finalWeight <= 25){
                        ButtonType buton = new Alert(Alert.AlertType.INFORMATION, "Equipaje maximo excedido ¿desea continuar? se le cobrara un porcentaje extra por kilo de equipaje extra", ButtonType.YES, ButtonType.NO).showAndWait().get();
                                if (buton.getText().equals("Sí")) {
                                    cobrar = (finalWeight + weight - 25) * 50;
                                    int id = bd.insertarMascota(Integer.parseInt(datos[2]), Nombre.getText(), String.valueOf(finalWeight),String.valueOf(tam.getValue()), 0);        
                                    GenerarPdf  pdf = new GenerarPdf();
                                                                        System.out.println("esto es folio "+folius);

                                    pdf.generarPdfMacotas(folius + "P" + id, Nombre.getText(), "Vacunado",String.valueOf(finalWeight) , String.valueOf(tam.getValue()), String.valueOf(cobrar));
                                    new Alert(Alert.AlertType.INFORMATION, "El costo extra es: " + cobrar, ButtonType.OK).showAndWait();
                                    a.abrirVentana("/aerolineaspatito/Menu");
                                    a.cerrarVentana(aceptar);
                                }else{
                                    
                                }
                    }else{
                         finalWeight += weight;
                         if(finalWeight > 25){
                             ButtonType buton = new Alert(Alert.AlertType.INFORMATION, "Equipaje maximo excedido ¿desea continuar? se le cobrara un porcentaje extra por kilo de equipaje extra", ButtonType.YES, ButtonType.NO).showAndWait().get();
                                if (buton.getText().equals("Sí")) {
                                    cobrar = weight * 50;
                                    new Alert(Alert.AlertType.INFORMATION, "El costo extra es: " + cobrar, ButtonType.OK).showAndWait();
                                   int id = bd.insertarMascota(Integer.parseInt(datos[2]), Nombre.getText(), String.valueOf(weight),String.valueOf(tam.getValue()), 0);
                                    GenerarPdf  pdf = new GenerarPdf();
                                    /*(String origen,String destino,String horaSalida,String horaLlegada,String numVuelo,String nombre,String vacunas,String peso,String jaula,String precio){*/
                                    System.out.println("esto es folio "+folius);
                                    pdf.generarPdfMacotas(folius+ "P" + id, Nombre.getText(), "Vacunado",String.valueOf(weight) , String.valueOf(tam.getValue()), String.valueOf(cobrar));
                                    a.abrirVentana("/aerolineaspatito/Menu");
                                    a.cerrarVentana(aceptar);
                                }
                                else{
                                                                        System.out.println("esto es folio "+folius);

                                   int id = bd.insertarMascota(Integer.parseInt(datos[2]), Nombre.getText(), String.valueOf(weight),String.valueOf(tam.getValue()), 0);
                                    GenerarPdf  pdf = new GenerarPdf();
                                    pdf.generarPdfMacotas(folius+ "P" + id, Nombre.getText(), "Vacunado",String.valueOf(weight) , String.valueOf(tam.getValue()), String.valueOf(cobrar));
                                    a.abrirVentana("/aerolineaspatito/Menu");
                                    a.cerrarVentana(aceptar);
                                }
                         }else{
                             
                         }
                    }
                }catch(Exception e){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error al agregar a mascota");
                                    alert.setHeaderText("Parece que hay un error");
                                    alert.setContentText("El peso debe ser un número");

                                    alert.showAndWait();
                                    System.out.println("E: " +e);
                }
                System.out.println("CB:" + cb);
        }
    }
    
}
