/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.conexionBaseDatos;

import aerolineaspatito.compraBoletosClases.Aerolineas;
import aerolineaspatito.compraBoletosClases.Fechas;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class ConexionBD {
    public Connection conn;
    public Statement st;
    String url;
    public ConexionBD()  {
        try {
            url = "jdbc:mysql://localhost:3306/aerolineaspatito";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
public ArrayList<String> SelectAereopuertosEstado(){
    ArrayList <String> estados = new ArrayList();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        String codigo="hola";
        ResultSet rs = st.executeQuery("SELECT nombreEstado FROM aeropuertosMexico");
        
        while (rs.next()) {
            boolean condicion=true;
            for (int i = 0; i < estados.size(); i++){
                if( rs.getString("nombreEstado").equals(estados.get(i)) ){
                    condicion=false;
                    break;
                }
            }
            if (condicion) {
                estados.add(rs.getString("nombreEstado"));
            }
            //if(rs.getString("nombreEstado").equals(estados.get(0)))
             //   estados.add(rs.getString("nombreEstado"));
        }
        
        conn.close();
        System.out.println("Insertado");  
        return estados;
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return estados;
        }
}
public String SelectAereopuertosEstado(int idAeropuerto){
   String estados = new String();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT nombreEstado, nombreMunicipio FROM aeropuertosMexico WHERE idAeropuertos ='"+idAeropuerto+"'");
        
        while (rs.next()) {
            estados = rs.getString("nombreEstado")+" "+rs.getString("nombreMunicipio");   
        }
        
        conn.close();
        System.out.println("Insertado");  
        return estados;
    } 
    catch (SQLException e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
        }
    return estados;
}

public ArrayList<Aerolineas> SelectAereopuertosMunicipio(String estado){
    ArrayList <Aerolineas> estados = new ArrayList();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        String codigo="hola";
        ResultSet rs = st.executeQuery("SELECT * FROM aeropuertosMexico WHERE nombreEstado='"+estado+"'");
        
        while (rs.next()) {
            boolean condicion=true;
            for (int i = 0; i < estados.size(); i++){
                if( rs.getString("nombreMunicipio").equals(estados.get(i)) ){
                    condicion=false;
                    break;
                }
            }
            if (condicion) {
                Aerolineas temp = new Aerolineas(estado, rs.getString("nombreMunicipio"), Integer.valueOf(rs.getString("idAeropuertos")));
                estados.add(temp);
            }
            //if(rs.getString("nombreEstado").equals(estados.get(0)))
             //   estados.add(rs.getString("nombreEstado"));
        }
        
        conn.close();
        System.out.println("Insertado");  
        return estados;
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return estados;
        }
    
    
        
     
}
public ArrayList<Fechas> SelectFechas(String fecha){
    ArrayList <Fechas> estados = new ArrayList();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM fechas WHERE dia='"+fecha+"'");
        
        while (rs.next()) {
                Fechas temp = new Fechas(rs.getString("dia"), Integer.valueOf(rs.getString("idFecha")),rs.getString("hora") );
                estados.add(temp);

        }
        
        conn.close();
        System.out.println("Insertado");  
        return estados;
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return estados;
        }    
     
}
    public ArrayList<String> selectVuelosDisponibles(Aerolineas estadoSalida, Aerolineas estadoDestino, ArrayList<Fechas> fecha ){
       ArrayList <String> estados = new ArrayList();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        for (int i = 0; i < fecha.size(); i++) {
             ResultSet rs = st.executeQuery("SELECT idVuelo, precio, idFecha FROM vuelo WHERE idOrigen='"+
                     estadoSalida.getId()+"' AND idDestino='"+estadoDestino.getId()+"' "
                             + "AND idFecha='"+fecha.get(i).getId()+"'");
        
            while (rs.next()) {
                    System.out.println("dia: "+fecha.get(i).getFecha()+" hora: "+fecha.get(i).getHora()+" idVuelo: "
                            +rs.getString("idVuelo")+" precio: "+rs.getString("precio")+" idFecha: "+rs.getString("idFecha"));
                  estados.add("dia: "+fecha.get(i).getFecha()+" hora: "+fecha.get(i).getHora()+" No de vuelo: "
                            +rs.getString("idVuelo")+"/"+rs.getString("idFecha")+" precio: "+rs.getString("precio"));

            }
        }
       
        
        conn.close();
        System.out.println("Insertado");  
        return estados;
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return estados;
        }
    
    }
    
    public String SelectFecha(int idFecha){
        try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT dia FROM fechas WHERE idFecha='"+idFecha+"'");
        Date fecha=null;
        while (rs.next()) {
                fecha = rs.getDate("dia");
        }
        
        conn.close();
        System.out.println("Insertado");  
        return fecha.toString();
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return null;
        }    
    }
    
    public int insertVentas(String nombre,int edad, String telefono,String direccion, String correo,String equipaje, int clase, String sexo, int idSE, int idVuelo){
        
        int id=insertIntoDatosComprador(nombre, edad, telefono, direccion, correo, equipaje, clase,idSE, sexo);
        int re=0;
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            String t ="INSERT INTO ventas (idComprador, idVuelo, cancelado) VALUES('"+id+"','"+idVuelo+"','"+0+"')";
            //st.execute("INSERT INTO ventas (idComprador, idVuelo, cancelado) VALUES('"+id+"','"+idVuelo+"','"+0+"')");
            int numero =st.executeUpdate( t, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()){
                re=rs.getInt(1);
            }
            rs.close();

            conn.close();
            return re;
        }catch(Exception e){
             System.err.println("Got an exception! "); 
            System.err.println(e);   
        }
        return 0;
    }
    
    public int idServicioEspecial(String servicioEspecial){
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            int id = 0;
            ResultSet rs = st.executeQuery("SELECT idTipoServicioEspecial "
                    + "FROM tiposserviciosespeciales WHERE nombreTipoServicio = '"+servicioEspecial+"'");
            while(rs.next()){
               id = rs.getInt("idTipoServicioEspecial");
            }
            conn.close();
            return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            
            return 0;
        }
    }
     public String SelectServicioEspecial(int idServicioEspecial){
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            String id = new String();
            ResultSet rs = st.executeQuery("SELECT nombreTipoServicio FROM tiposserviciosespeciales WHERE  idTipoServicioEspecial  = '"+idServicioEspecial+"'");
            while(rs.next()){
               id = rs.getString("nombreTipoServicio");
            }
            conn.close();
            return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            
            return null;
        }
    }
     
    public int insertarMascota(int idComprador, String nombreMascota, String peso, String tanoJaula,int vacunado){
         try{
              
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
           //String t ="INSERT INTO mascotas (idComprador, nombreMascota, peso, tanoJaula, vacunado) VALUES('"+idComprador+"','"+nombreMascota+"','"+0+"')";
            int id = st.executeUpdate("INSERT INTO mascotas (idComprador, nombreMascota, peso, tanoJaula, vacunado) VALUES('"+idComprador + "','" +nombreMascota 
                    + "','" + peso +"','" +  tanoJaula + "','" +vacunado +"')", Statement.RETURN_GENERATED_KEYS);
            
           ResultSet rs = st.getGeneratedKeys();
            if (rs.next()){
                id=rs.getInt(1);
            }
            
            rs.close();
            conn.close();
           return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            return 0;
           
        }
    }
     
    public int idClase(String clase){
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            int id = 0;
            ResultSet rs = st.executeQuery("SELECT idClase FROM clases WHERE clase = '"+clase.trim()+"'");
            while(rs.next()){
               id = rs.getInt("idClase");
            }
            conn.close();
            return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            
            return 0;
        }
    }
    
    public String SelectClase(int clase){
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            String id = new String();
            ResultSet rs = st.executeQuery("SELECT clase FROM clases WHERE idClase= '"+clase+"'");
            while(rs.next()){
               id = rs.getString("clase");
            }
            conn.close();
            return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            
            return null;
        }
    }
    public String SelectVuelo(int idVuelo){
        try{
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            String id = new String();
            ResultSet rs = st.executeQuery("SELECT * FROM vuelo WHERE idVuelo= '"+idVuelo+"'");
            while(rs.next()){
               id = ""+rs.getInt("idFecha")+" "+rs.getInt("idOrigen")+" "+rs.getInt("idDestino");
            }
            conn.close();
            return id;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            
            return null;
        }
    }
     private int insertIntoDatosComprador(String nombre,int edad, String telefono,String direccion, 
             String correo,String equipaje, int clase,int idSE, String sexo){
       int cancelado = 0;
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        int id = 0;/*
        st.execute("INSERT INTO datoscompradores ( nombre, edad, sexo, correo, direccion, "
                + "telefono, pesoEquipaje, idClase, idServicioEspecial, cancelado) "
                + "VALUES "
                + "('"+nombre+"','"+edad+"','"+sexo+"','"+correo+"','"+direccion+"','"
                +telefono+"','"+equipaje+"','"+clase+"','"+idSE+"','"+cancelado+"')");
        
        ResultSet rs = st.executeQuery("SELECT idComprador FROM datoscompradores");
        while(rs.next()){
               id = rs.getInt("idComprador");
            }*/
        int numero =st.executeUpdate( "INSERT INTO datoscompradores ( nombre, edad, sexo, correo, direccion, "
                + "telefono, pesoEquipaje, idClase, idServicioEspecial, cancelado) "
                + "VALUES "
                + "('"+nombre+"','"+edad+"','"+sexo+"','"+correo+"','"+direccion+"','"
                +telefono+"','"+equipaje+"','"+clase+"','"+idSE+"','"+cancelado+"')", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()){
                id=rs.getInt(1);
            }
            rs.close();

            conn.close();
        
        
        
            conn.close();
            return id;

    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return 0;
        }
    
    }
     
    public int selectPrecio(int idVuelo){

        try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
       // int id = 0;
        int precio = 0;
        
        ResultSet rs = st.executeQuery("SELECT precio FROM vuelo WHERE idVuelo ='"+idVuelo+"'");
        while(rs.next()){
            precio = rs.getInt("precio");
        } 
        conn.close();
        return precio;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return 0;
        }
    }
    
    public boolean ValidarFolioMascotas(String folio){
        try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        
        ResultSet rs = st.executeQuery("SELECT idVenta FROM VENTAS");
        while(rs.next()){
           int id = rs.getInt("idVenta");
           if(folio.equals(""+id+"")){
               return true;
           }
        }
        return false;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            return false;
        }
    }
    
    public String[] SelectMascotasComprador(int idVenta){
        try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        int idComprador = 0;
        int idClase = 0;
        int idSE = 0;
        int idVuelo = 0;
        String datos[] = new String[3];
        ResultSet rs2 = st.executeQuery("SELECT idComprador, idVuelo FROM ventas WHERE idVenta ='"+idVenta+"'");
        while(rs2.next()){
            idComprador = rs2.getInt("idComprador");
            idVuelo = rs2.getInt("idVuelo");
        }
        if(idComprador != 0 && idVuelo != 0){
          ResultSet  rs = st.executeQuery("SELECT * FROM datosCompradores WHERE idComprador='"+idComprador+"'");
            while(rs.next()){
                datos[0] = "Nombre:" + rs.getString("nombre") + "\n" + "Edad: " + ""+rs.getInt("edad")+ "" + "\n" 
                        + "Sexo: " + rs.getString("sexo")+"\n"+"Correo: " + rs.getString("correo") 
                        + "\n" + "Direccion: " + rs.getString("direccion") +"\n"+ "Teléfono: "+ rs.getString("telefono") 
                        + "\n" + "Peso de quipaje: " + rs.getString("pesoEquipaje") + "\n" + "Id de vuelo: " + idVuelo;
                datos[1] = rs.getString("pesoEquipaje");
                datos[2] = String.valueOf(idComprador);
            }
            return datos;
            
        }else   
            return null;
        }catch(Exception e){
            System.err.println("Got an exception! "); 
            System.err.println(e);
            return null;
        }
    }
   
    // public insertServicioEspecial()
    public void cerrarConexion(){
        try{
            conn.close();
        }catch(Exception e) { 
                System.err.println("Got an exception! "); 
                System.err.println(e);
        }
    }
    public ArrayList<String> buscarMunicipios(String idBoleto){
    ArrayList <String> municipios = new ArrayList();
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        String id = null, idVuelo = null, idOrigen = null, idDestino = null;       
        ResultSet rs3 = st.executeQuery("SELECT idVuelo FROM ventas WHERE idVenta='"+idBoleto+"'");
        while(rs3.next()){
            idVuelo=rs3.getString("idVuelo");
        }       
        System.out.println("idVuelo: " + idVuelo);
        
        ResultSet rs4 = st.executeQuery("SELECT idOrigen FROM vuelo WHERE idVuelo='"+idVuelo+"'");
        while(rs4.next()){
            idOrigen=rs4.getString("idOrigen");
        }
        ResultSet rs5 = st.executeQuery("SELECT idDestino FROM vuelo WHERE idVuelo='"+idVuelo+"'");
       
        System.out.println("idOrigen: " + idOrigen);
        while(rs5.next()){
            idDestino=rs5.getString("idDestino");
        }
        System.out.println("SALE DE: " + idOrigen + "   Va para: " + idDestino);
        municipios.add(idOrigen);
        municipios.add(idDestino);
        //ResultSet rs6 = st.executeQuery("SELECT idVuelo, idOrigen, idDestino FROM vuelo WHERE idOrigen='"+idOrigen+"' AND idDestino='"+idDestino+"'");
        conn.close();
        return municipios;
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
        }
        return null;
}
    public ArrayList<String> selectVuelosDisponiblesCambio(String idOrigen, String idDestino){
        System.out.println("HOLA");
        ArrayList <String> estados = new ArrayList();
        try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT idVuelo, precio, vuelo.idFecha, dia, hora FROM vuelo INNER JOIN fechas ON fechas.idFecha=vuelo.idFecha WHERE idOrigen='"+idOrigen+"' AND idDestino='"+idDestino+"'");
        /*
        SELECT idVuelo, precio, vuelo.idFecha, dia, hora FROM vuelo 
        INNER JOIN fechas ON fechas.idFecha=vuelo.idFecha
        WHERE idOrigen="1" AND idDestino="7"
        */
        while (rs.next()) {
            System.out.println(" idVuelo: "+rs.getString("idVuelo")+" precio: "+rs.getString("precio")+" idFecha: "+rs.getString("vuelo.idFecha") + " fecha: " + rs.getString("dia") + " hora: " + rs.getString("hora"));
                // estados.add("idVuelo: "+rs.getString("idVuelo")+" precio: "+rs.getString("precio")+" idFecha: "+rs.getString("vuelo.idFecha") + " fecha: " + rs.getString("dia") + " hora: " + rs.getString("hora"));
             estados.add("dia: " + rs.getString("dia") + " hora: " + rs.getString("hora") + " No de vuelo: " + rs.getString("idVuelo")+"/"+rs.getString("vuelo.idFecha")+" precio: " + rs.getString("precio"));       
        }
        conn.close();
        System.out.println("Insertado");  
        return estados;
        } 
        catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return estados;
        }
    
    }
    
    public double obtenerPeso(int idVenta){
        try {
            String id = null;
            int idComprador = 0;
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            st = conn.createStatement();
            
            ResultSet rs2 = st.executeQuery("SELECT idComprador FROM ventas WHERE idVenta = '" +idVenta+"'");
            while(rs2.next()){
             idComprador=rs2.getInt("idComprador");
            }
            ResultSet rs = st.executeQuery("SELECT pesoEquipaje FROM datoscompradores WHERE idComprador = '" +idComprador+"'");
            
            while(rs.next()){
             id=rs.getString("pesoEquipaje");
            }
            
            return Double.parseDouble(id);
            
            
        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
            return 0;
        }
    }
    
    public void cambiar(String idBoleto, String asiento, String nuevoVuelo){
    try {
        conn = DriverManager.getConnection(url,"root","");
        st = conn.createStatement();
        String id = null, idVuelo = null, idOrigen = null, idDestino = null;
        ResultSet rs = st.executeQuery("SELECT idComprador FROM ventas WHERE idVenta='"+idBoleto+"'");
        while(rs.next()){
            id=rs.getString("idComprador");
        }
        int rs2 = st.executeUpdate("UPDATE datoscompradores SET idServicioEspecial='"+asiento+"' WHERE idComprador='"+id+"'");
        int rs3 = st.executeUpdate("UPDATE ventas SET idVuelo='"+nuevoVuelo+"' WHERE idVenta='"+idBoleto+"'");
        conn.close();
        System.out.println("Cambiado.");  
    } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
        }
}
    public void Cancelar(String idBoleto){
        try {
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
            int rs = st.executeUpdate("UPDATE ventas SET cancelado='"+1+"' WHERE idVenta='"+idBoleto+"'");
            conn.close();
            System.out.println("Cancelado.");
            
        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
        }
    }
    
    public boolean comprobar(String idBoleto){
        String id = null;
        try {
            conn = DriverManager.getConnection(url,"root","");
            st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT cancelado FROM ventas WHERE idVenta='"+idBoleto+"'");
        
        while(rs.next()){
            id=rs.getString("cancelado");
        }      
        System.out.println("ID: " + id);
        conn.close();
        if(id.equals("0")){
            return true;
        }
        else
            return false;

        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e); 
        }    
        return false;
    }
}


