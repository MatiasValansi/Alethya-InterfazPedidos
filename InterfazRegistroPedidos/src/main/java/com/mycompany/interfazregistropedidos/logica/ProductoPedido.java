/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductoPedido implements Serializable {
    
    /*
    Esta clase es creada debido a que necesito que en cada Producto añadido 
    al Pedido pueda elegir la cantidad de dicho producto.
    */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id; //Creo un ID propio para la tabla intermedia
    
    //private int idPedidoRemito;
    /*
    private int idProducto;
    
    omito el campo idProducto porque la clave foránea ya está gestionada 
    por la relación ManyToOne. Lo mismo con idPedidoRemito.
    */
    private int cantProducto;
    
    //Relaciones
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = true)
    private Pedido pedido; // Relación con Pedido
    
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false) //Creará una columna "id_producto" en la tabla ProductoPedido en la que irán los Id obtenidos a del Producto.  NO habrá una tabla Producto, sino id_producto
    private Producto producto; //ManyToOne

    public ProductoPedido() {
    }

    public ProductoPedido(int cantProducto, Pedido pedido, Producto producto) {
        this.cantProducto = cantProducto;
        this.pedido = pedido;
        this.producto = producto;
    }
    
    /*
    Este constructor me permitirá crear un ProductoPedido sin que exista anteriormente un Pedido
    */
    public ProductoPedido(int cantProducto, Producto producto) {
        this.cantProducto = cantProducto;
        this.producto = producto;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantProducto() {
        return cantProducto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    /*
    Calcula el precio total de este producto en el pedido.
    this.producto.getPrecio() * cantidad de ese producto
    */
    public double calcularPrecio(){
        return this.producto.getPrecio() * cantProducto;
    }

    public void asociarPedido(Pedido pedidoAAsociar) {
        this.setPedido(pedidoAAsociar);
    }

    void desasociarPedido() {
        this.setProducto(null);
    }

    public boolean mismoNombre(String nombreProductoPedido) {
        // OJO: Una vez corregido, cambiar .getNombre() por getDescripcion()
        return this.producto.getNombre().equalsIgnoreCase(nombreProductoPedido);
    }
    
    public int obtenerCantStock(){
        return this.producto.getCantStock();
    }
    
    
}
