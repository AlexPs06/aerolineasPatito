/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aerolineaspatito.compraBoletosClases;

/**
 *
 * @author pablo
 */
public class DatosComprador {
    String nombre;
    int edad;
    String sexo;
    String correo;
    String direccion;
    String telefono;
    String pesoEquipaje;
    int idClase;
    int idServicioEspecial;
    int cancelado;

    public DatosComprador() {
    }

    public DatosComprador(String nombre, int edad, String sexo, String correo, 
        String direccion, String telefono, String pesoEquipaje, int idClase, 
        int idServicioEspecial, int cancelado) {
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.pesoEquipaje = pesoEquipaje;
        this.idClase = idClase;
        this.idServicioEspecial = idServicioEspecial;
        this.cancelado = cancelado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPesoEquipaje() {
        return pesoEquipaje;
    }

    public int getIdClase() {
        return idClase;
    }

    public int getIdServicioEspecial() {
        return idServicioEspecial;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPesoEquipaje(String pesoEquipaje) {
        this.pesoEquipaje = pesoEquipaje;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public void setIdServicioEspecial(int idServicioEspecial) {
        this.idServicioEspecial = idServicioEspecial;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    @Override
    public String toString() {
        return "DatosComprador{" + "nombre=" + nombre + ", edad=" + edad + ", sexo=" + sexo + ", correo=" + correo + ", direccion=" + direccion + ", telefono=" + telefono + ", pesoEquipaje=" + pesoEquipaje + ", idClase=" + idClase + ", idServicioEspecial=" + idServicioEspecial + ", cancelado=" + cancelado + '}';
    }
    
    
}
