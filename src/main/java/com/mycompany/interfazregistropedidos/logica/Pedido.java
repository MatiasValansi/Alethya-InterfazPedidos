package com.mycompany.interfazregistropedidos.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int remito; //Será el ID
    private Date fecha;
    private int cantProductosTotales;//Es la sumatoria de la cant de Productos de todos los Productos
    private double total;
    private MetodoPago metodoDePago;
    private boolean facturaPago;
    public static int contId = 1;//Será para numerar los pedidos. A diferencia de remito que es el ID, es para que al presionar el botón AñadirPrrducto en la interfaz ArmarPedido, poder acceder al N° del Id.
    //private int MINIMO = 0;

    
    //Relaciones
    @OneToOne
    private Cliente cliente; //Esto hab´ra que modificar en el 2° video pq es la analogia de Mascot OneToOne Duenio
    

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ProductoPedido> productos;
    
    /*
    private List<Producto> productos;
    OBS:
    No se necesita una relación directa entre Pedido y Producto 
    si estás utilizando una clase intermedia. 
    La clase intermedia ProductoPedido ya se encargará de vincular los productos 
    a un pedido específico.
    */
    
    public Pedido() {
        this.productos = new ArrayList<ProductoPedido>();
    }    
    
    public Pedido( Date fecha, int cantProductosTotales, double total, MetodoPago metodoDePago, boolean facturaPago, Cliente cliente) {
        //this.remito = remito; ---> El Remito se autogenera
        this.fecha = fecha;
        this.cantProductosTotales = cantProductosTotales;
        this.total = total;
        this.metodoDePago = metodoDePago;
        this.facturaPago = facturaPago;
        this.cliente = cliente;
        this.productos = new ArrayList<ProductoPedido>();
        contId++; 
    }   

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantProductosTotales() {
        return cantProductosTotales;
    }

    public void setCantProductosTotales(int cantProductosTotales) {
        this.cantProductosTotales = cantProductosTotales;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public MetodoPago getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoPago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public boolean isFacturaPago() {
        return facturaPago;
    }

    public void setFacturaPago(boolean facturaPago) {
        this.facturaPago = facturaPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ProductoPedido> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoPedido> productos) {
        this.productos = productos;
    }

    
    

    /* HECHO CON LUCIO
    private void setProductos(List<Producto> productos) {
        this.productos = new ArrayList<Producto>();
        
        for (Producto cadaProducto : productos) {
            this.productos.add(cadaProducto);
        }
    }
    */

    public  int getRemito() {
        return remito;
    }

    public void setIdRemito(int remito) {
        this.remito = remito;
    }
    
    public static int obtenerIdRemito(){
        return contId;
    }
    
    /*
    Calcula el precio total de todos los productos que contiene el pedido
    */
    public double calcularPrecioTotal(){
        
        double precioTotal = 0;
        
        for (ProductoPedido cadaProducto : productos) {
            precioTotal += cadaProducto.calcularPrecio();
        }
        
        return precioTotal;
    }
    
    /*
    Calcula la cantidad de productos totales que hay en el pedido.
    Por cada Producto, acumula la cantidad del mismo.
    */
    public int calcularCantProductosTotales(){
        
        int cantProdTotales = 0;
        
        for (ProductoPedido cadaProducto : productos) {
            cantProdTotales += cadaProducto.getCantProducto();
        }
        
        return cantProdTotales;
    }
    
    /*
    Método para añadir un producto al pedido.
    */
    public void agregarProducto(ProductoPedido productoPedidoAAgregar){
        this.productos.add(productoPedidoAAgregar);
        //Asocio el Pedido en ProductoPedido
        productoPedidoAAgregar.asociarPedido(this);
    }
    
    /*
    Método para añadir productos al pedido.
    */
    public void agregarProductos(List<ProductoPedido> productosPedidoAAgregar){
        
        for (ProductoPedido cadaProductoPedido : productosPedidoAAgregar) {
             this.agregarProducto(cadaProductoPedido);
        }
       
    }
    
    public void elimiarProducto(ProductoPedido productoPedido) {
        this.productos.remove(productoPedido);
        productoPedido.desasociarPedido();
    }
    
    
    
}


