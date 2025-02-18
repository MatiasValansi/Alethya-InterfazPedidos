/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Producto implements Serializable {
    @Id
    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantStock;
    
    //Relaciones
    @OneToMany
    private List<ProductoPedido> pedidos; // OneToMany --> Un producto puede estar en uno o mas Pedidos

    public Producto() {
    } 

    public Producto(int codigo, String nombre, String descripcion, double precio, int cantStock, List<ProductoPedido> pedidos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantStock = cantStock;
        this.pedidos = pedidos;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantStock() {
        return cantStock;
    }

    public void setCantStock(int cantStock) {
        this.cantStock = cantStock;
    }

    public List<ProductoPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ProductoPedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public void descontarStock(int stockADescontar){
        this.cantStock -= stockADescontar;
    }
    
    
    
}
