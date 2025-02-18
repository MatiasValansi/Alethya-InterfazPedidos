/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.persistencia;

import com.mycompany.interfazregistropedidos.logica.Cliente;
import com.mycompany.interfazregistropedidos.logica.Pedido;
import com.mycompany.interfazregistropedidos.logica.Producto;
import com.mycompany.interfazregistropedidos.logica.ProductoPedido;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.IllegalOrphanException;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladoraPersistencia {
    
    PedidoJpaController pedidoController = new PedidoJpaController();
    
    ProductoJpaController productoController = new ProductoJpaController();
    
    ClienteJpaController clienteController = new ClienteJpaController();

    ProductoPedidoJpaController productoPedidoController = new ProductoPedidoJpaController();
    
    
    public void guardarPedido(Pedido pedido) {
        //Creo el Pedido en la Base de Datos
        pedidoController.create(pedido);
    }

    /*
    Creará el Producto en la Base de Datos
    */
    public void guardarProducto(Producto producto)  {
        try {
            //Creo el Producto en la Base de Datos
            productoController.create(producto);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Creará el Cliente en la Base de Datos
    */
    public void guardarCliente(Cliente cliente) {
        try {
            //Creo el Cliente en la Base de Datos
            clienteController.create(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lanzó Excepción");
        }
    }
    
    public void guardarProductoPedido(ProductoPedido productoPedido) {
        productoPedidoController.create(productoPedido);
    }

    public List<Producto> buscarProductos() {
        
        return productoController.findProductoEntities();
        
    }

    public List<Cliente> buscarClientes() {
        return clienteController.findClienteEntities();
    }

    public List<Pedido> buscarPedidos() {
        return pedidoController.findPedidoEntities();
    }

    public List<ProductoPedido> buscarProductosPedidos() {
        return productoPedidoController.findProductoPedidoEntities();
    }

    public void eliminarCliente(int dniCliente) {
        try {
            clienteController.destroy(dniCliente);
        } catch (com.mycompany.interfazregistropedidos.exceptions.NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarPedido(int remitoPedido) {
        try {
            pedidoController.destroy(remitoPedido);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarProducto(int codigoProducto) {
        try {
            productoController.destroy(codigoProducto);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarProductoPedido(int idProductoPedido) {
        try {
            productoPedidoController.destroy(idProductoPedido);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente buscarCliente(int dniCliente) {
        return clienteController.findCliente(dniCliente);
    }

    public void editarCliente(Cliente clienteEditado) {
        try {
            clienteController.edit(clienteEditado);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Producto buscarProducto(int codigoProdAEditar) {
        return productoController.findProducto(codigoProdAEditar);
    }

    public void editarProducto(Producto productoEditado) {
        try {
            productoController.edit(productoEditado);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pedido buscarPedido(int numRemito) {
        return pedidoController.findPedido(numRemito);
    }

    public void editarPedido(Pedido pedidoEditado) {
        try {
            pedidoController.edit(pedidoEditado);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void limpiarTabla() {
    productoPedidoController.deleteAllProductoPedidos();
}

    public ProductoPedido buscarProductosPedido(int idProductoPedido) {
        return productoPedidoController.findProductoPedido(idProductoPedido);
    }

    public void editarProductoPedido(ProductoPedido prodPedidoEditado) {
        try {
            productoPedidoController.edit(prodPedidoEditado);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
