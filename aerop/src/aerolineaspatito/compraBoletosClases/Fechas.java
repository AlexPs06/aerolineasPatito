/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.compraBoletosClases;

/**
 *
 * @author theco
 */
public class Fechas {
    String fecha;
    int id;
    String hora;

    public Fechas(String fecha, int id, String hora) {
        this.fecha = fecha;
        this.id = id;
        this.hora = hora;
    }

    public Fechas() {
    }

    public String getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }

    public String getHora() {
        return hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Fechas{" + "fecha=" + fecha + ", id=" + id + ", hora=" + hora + '}';
    }
    
}
