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
public class Aerolineas {
    String estado;
    String municipio;
    int id;

    public Aerolineas(String estado, String municipio, int id) {
        this.estado = estado;
        this.municipio = municipio;
        this.id = id;
    }

    public Aerolineas() {
    }

    public String getEstado() {
        return estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public int getId() {
        return id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  ""+municipio;
    }
    
            
}
