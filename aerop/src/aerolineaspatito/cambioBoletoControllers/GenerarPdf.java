/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.cambioBoletoControllers;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;



/**
 *
 * @author Fabricio
 */
public class GenerarPdf {
    /*tipo de fuente y tamaño*/
    private Font fuente=new Font(Font.FontFamily.COURIER,10,Font.BOLD);
    
    String carpeta="/Users/"+System.getProperty("user.name")+"/Desktop/BOLETOS/boleto";
    String carpeta2="/Users/"+System.getProperty("user.name")+"/Desktop/MASCOTAS/boleto";

    public GenerarPdf() {
    }
    
    
    public void generarPdf(String origen,String destino,String horaSalida,String horaLlegada,
            String numVuelo,String clase,String servicios,String nombre,String edad,
                String sexo,String numT,String direc,String peso,String correo,String precio, String dia){
     
        try{
            /*define documento, tamaño y margenes*/
           
            crearCarpetaBoletos();
            
            Document documento=new Document(PageSize.A4,36,36,10,10);
            PdfWriter.getInstance(documento,new FileOutputStream(carpeta+numVuelo+".pdf"));
            documento.open();
            String ruta = getClass().getResource("/aerolineaspatito/compraBoletosClases/logoPatito.png").toExternalForm();
            System.out.println(ruta);
            Image imagen = Image.getInstance(ruta);
            imagen.scaleAbsolute(80,60);
            imagen.setAlignment(Element.ALIGN_LEFT);
            documento.add(imagen);
            documento.add(getHeatder(origen,destino,horaSalida,horaLlegada,numVuelo));
            documento.add(getInfoCliente(nombre,edad,
                sexo,numT,direc,peso,correo));
            documento.add( getInfoVuelo(clase,servicios));
            documento.add(getPrecio(precio));
            documento.close();
        }catch(Exception ex){
            System.out.println("Error al guardar PDF");
        }
    }
    
    public void generarPdfMacotas(String numVuelo,String nombre,String vacunas,String peso,String jaula,String precio){
        try{
           
           
            crearCarpetaMascotas();
             /*define documento, tamaño y margenes*/
            Document documento=new Document(PageSize.A4,36,36,10,10);
            PdfWriter.getInstance(documento,new FileOutputStream(carpeta2+numVuelo+".pdf"));
            documento.open();
            String ruta = getClass().getResource("/aerolineaspatito/compraBoletosClases/logoPatito.png").toExternalForm();
            Image imagen = Image.getInstance(ruta);
            imagen.scaleAbsolute(80,60);
            imagen.setAlignment(Element.ALIGN_LEFT);
            documento.add(imagen);
            documento.add(getHeatder(numVuelo));
            documento.add(getInfoMascota(nombre,vacunas,peso,jaula));
            ruta = getClass().getResource("/aerolineaspatito/compraBoletosClases/iconoMascotas.png").toExternalForm();
            Image imagen2 = Image.getInstance(ruta);
            imagen2.scaleAbsolute(50,50);
            imagen2.setAlignment(Element.ALIGN_RIGHT);
            documento.add(imagen2);
            documento.add(getPrecio(precio));
            documento.close();
        }catch(Exception ex){
            System.out.println("Error al guardar PDF");
        }
    }
    
    
    
    private Paragraph getHeatder(String origen,String destino,String horaSalida,String horaLlegada,String numVuelo){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(origen+" / "+destino+"\n"+ horaSalida+
                " / "+horaLlegada+"\n#Vuelo: "+numVuelo);
        c.setFont(fuente);
        p.add(c);
        return p;
    }
    private Paragraph getHeatder(String numVuelo){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_CENTER);
        c.append("\n#Vuelo: "+numVuelo);
        c.setFont(fuente);
        p.add(c);
        return p;
    }
    
        private Paragraph getInfoCliente(String nombre,String edad,
                String sexo,String numT,String direc,String peso,String correo){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_LEFT);
        c.append("Titular:\nNombre: "+nombre+"\nsexo: "+sexo+
                "\nEdad: "+edad+"\nTEL: "+numT+"\nCorreo: "+correo+"\nDireccion: "+direc
                +"\nPeso total de equipaje: "+peso+"kg");
        c.setFont(fuente);
        p.add(c);
        return p;
    }
        
        private Paragraph getInfoMascota(String nombre,String vacunas,String peso,String jaula){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_LEFT);
        c.append("Datos de la mascota:\nNombre: "+nombre+"\nVacunas: "+vacunas+
                "\nPeso: "+peso+" kg\nTamaño de jaula: "+jaula);
        c.setFont(fuente);
        p.add(c);
        return p;
    }
        
        
        
    private Paragraph getInfoVuelo(String clase,String servicios){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_RIGHT);
        c.append("clase / servicios\n"+clase+" / "+servicios);
        c.setFont(fuente);
        p.add(c);
        return p;
    }
    
    private Paragraph getPrecio(String precio){
        Paragraph p=new Paragraph();
        Chunk c=new Chunk();
        /*alineacion de texto*/
        p.setAlignment(Element.ALIGN_BOTTOM);
        c.append("precio: "+precio+" $");
        c.setFont(fuente);
        p.add(c);
        return p;
    }
    public void crearCarpetaBoletos(){
       String carpetaDireccion="C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\BOLETOS";
       File crearCarp=new File(carpetaDireccion);
       
       if(!crearCarp.exists()){
           new Alert(Alert.AlertType.INFORMATION, "se creara una nueva carpeta en el escritorio llamada BOLETOS, donde se almasenaran todos lo boletos generados ", ButtonType.OK).showAndWait();
        crearCarp.mkdirs();
       }
       
    }
    
    public void crearCarpetaMascotas(){
       String carpetaDireccion="C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\MASCOTAS";
       File crearCarp=new File(carpetaDireccion);
       
       if(!crearCarp.exists()){
        new Alert(Alert.AlertType.INFORMATION,"se creara una nueva carpeta en el escritorio llamada MASCOTAS, donde se almasenaran todos lo boletos generados " , ButtonType.OK).showAndWait();

           
        crearCarp.mkdirs();
       }
       
    }
    
    
}
