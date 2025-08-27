package com.mycompany.interfazregistropedidos.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cliente implements Serializable {
    
    @Id
    private String dni;
    private String nombre;
    private String celular;
    private String mail;
    private String direccion;
    private String localidad;
    private String provincia;
    private String condicionIva;
    
    //Relaciones
    @OneToMany
    private List<Pedido> pedidos;

    public Cliente() {
    }

    //El Cliente es creado SIN Pedidos, es decir, su lista estará vacía
    
    @SuppressWarnings("Convert2Diamond")
    public Cliente(String dni, String nombre, String celular, String mail, String direccion, String localidad, String provincia, String condicionIva) {
        this.dni = dni;
        this.nombre = nombre;
        this.celular = celular;
        this.mail = mail;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.condicionIva = condicionIva;
        this.pedidos = new ArrayList<Pedido>();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCondicionIva() {
        return condicionIva;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCondicionIva(String condicionIva) {
        this.condicionIva = condicionIva;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
        
    public int calcTotalProductos(Pedido pedidoACalcular){
        return pedidoACalcular.calcularCantProductosTotales();
    }
    
}
