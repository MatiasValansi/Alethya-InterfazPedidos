/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.logica;

import com.mycompany.interfazregistropedidos.persistencia.ControladoraPersistencia;
import java.util.Date;
import java.util.List;

/**
 *
 * @author matie
 */
public class ControladoraLogica {
    
    ControladoraPersistencia controlPers = new ControladoraPersistencia();

    //Método Guardar de ArmarPedido
    public void guardarPedido(Date fecha, MetodoPago formaPago, double total, List<ProductoPedido> productos, String cuitCliente) {
     
        //Genero el Cliente
        
        Cliente cliente = this.buscarCliente(cuitCliente);
        //OBS: Se supone que el cliente ya está creado, por eso habrá que buscarlo a partir de su ID (DNI o Cuit)

        
        //Genero el Pedido con sus valores
        Pedido pedido = new Pedido(); 
        /*
        OBS: Al hacer este NEW, será distinto el ID del Pedido que se añadirá a la BD que el que se recibe como parametro 
        Para solucionarlo, en vez de recibir los´parametros del Pedido que quiero añadir a la BD y hacer el NEW acá, 
        debo recibir directamente el Pedido ya creado con estos atributos
        como paramétro, al igual que como resolví el problema de ID en ProductoPedido.
        
        public void guardarProductoPedido(ProductoPedido productoPedidoAGuardar) {
                //Recibo por parametro el ProductoPedido a guardar y no sus atributos así el ID que asigna JPA, lo guarda en dicho ProductoPedido enviado por parametro
        
        controlPers.guardarProductoPedido(productoPedidoAGuardar);
    }
        
        */
        
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser nula");
        } else {
            pedido.setFecha(fecha);
        }

        if (formaPago == null) {
            throw new IllegalArgumentException("Debe seleccionar un método de pago");
        } else {
            pedido.setMetodoDePago(formaPago);
        }

        if (total <= 0) {
            throw new IllegalArgumentException("El total del pedido debe ser mayor a 0");
        } else {
            pedido.setTotal(total);
        }

        if (cuitCliente == null || cuitCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un CUIT de cliente válido");
        }
        
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con CUIT " + cuitCliente + " no existe");
        } else {
            pedido.setCliente(cliente);
        }

        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("Debe añadir al menos un producto al pedido");
        } else {
            pedido.setProductos(productos);        
        }

        controlPers.guardarPedido(pedido); 
        //Guardo el Pedido solamente porque para que exista el Cliente, este ya debe estar en la BD.
        
        //OJO: Debo añadir el Pedido a la lista de pedidos del cliente
        //controlPers.añadirPedidoACliente(cliente);
        
    }
    
    /*
    Creo una sobrecarga de guardarPedido() para guardar un Pedido vacío en la BD y así poder usarlo para guardar un ProductoPedido en la BD.
    El ProductoPedido NO se puede guardar en la BD sin antes tener el Pedido y el Producto correspondientes en la BD.
    */
    public void guardarPedido(Pedido pedido, String cuitCliente) {
     
        //Genero el Cliente
        
        // Cliente cliente = this.buscarCliente(cuitCliente), ahora lo hago así nomas
        //OBS: Se supone que el cliente ya está creado, por eso habrá que buscarlo a partir de su ID (DNI o Cuit)
        Cliente cliente = new Cliente(); //Una vez hecho el método de busqueda, esto lo borro
        cliente.setDni(cuitCliente);
        
               
        controlPers.guardarPedido(pedido); 
        //Guardo el Pedido solamente porque para que exista el Cliente, este ya debe estar en la BD.
        
        //OJO: Debo añadir el Pedido a la lista de pedidos del cliente
        //controlPers.añadirPedidoACliente(cliente);
        
    }
    
    
    
    //Método Guardar de AgregarProducto
    public void guardarProducto(String codigoProducto, String nombre, String descripcion, int cantStock, double precio) {
        
        //Genero el Producto
        Producto producto = new Producto();
        producto.setDescripcion(descripcion);
        
        
        if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto no puede estar vacío");
        } else {
            producto.setCodigo(codigoProducto);
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        } else {
            producto.setNombre(nombre);
        }
        
        if (cantStock < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa");
        } else {
            producto.setCantStock(cantStock);
        }

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        } else {
            producto.setPrecio(precio);
        }
        
        controlPers.guardarProducto(producto);
    }

    //Método Guardar de AgregarCliente
    public void guardarCliente(String dni, String nombre, String celular, String email, String direccion, String localidad, String provincia, String consumidorFinal) {
        
        //Genero el Cliente
        Cliente cliente = new Cliente();
        
        
        cliente.setCelular(celular);
        cliente.setMail(email);
        cliente.setDireccion(direccion);
        cliente.setLocalidad(localidad);
        cliente.setProvincia(provincia);
        cliente.setCondicionIva(consumidorFinal);
        
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        } else {
            cliente.setDni(dni);
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        } else {
            cliente.setNombre(nombre);
        }
        if (!dni.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe contener solo números");
        }
        controlPers.guardarCliente(cliente);
        
    }
    
    
    public void guardarProductoPedido(int cantProdPedido, Pedido pedido, Producto producto) {
        
        //Genero el Producto Pedido y lo seteo desde el constructor parametrizado
        ProductoPedido productoPedido = new ProductoPedido(cantProdPedido, pedido, producto);
        
        controlPers.guardarProductoPedido(productoPedido);
    }
    
    /*
    Sobrecarga de guardarProductoPedido() sin la existencia de un Pedido;
    */
    public void guardarProductoPedido(ProductoPedido productoPedidoAGuardar) {
        
        //Genero el Producto Pedido y lo seteo desde el constructor parametrizado
        //ProductoPedido productoPedidoAGuardar = new ProductoPedido(cantProdPedido, producto);
        
        //Recibo por parametro el ProductoPedido a guardar y no sus atributos así el ID que asigna JPA, lo guarda en dicho ProductoPedido enviado por parametro
        
        controlPers.guardarProductoPedido(productoPedidoAGuardar);
    }

    /*
    Método buscarProductos() de VerProductos.
    Este método retornanar una lista con los Productos que estan cargados en la Base de Datos.
    */
    public List<Producto> buscarProductos() {
        return controlPers.buscarProductos();
    }

    public List<Cliente> buscarClientes() {
        return controlPers.buscarClientes();
    }

    public List<Pedido> buscarPedidos() {
        return controlPers.buscarPedidos();
    }

    public List<ProductoPedido> buscarProductosPedidos() {
        return controlPers.buscarProductosPedidos();
    }

    public void eliminarCliente(String dniCliente) {
        controlPers.eliminarCliente(dniCliente);
    }

    public void eliminarPedido(int remitoPedido) {
        Pedido pedidoAEliminar = this.buscarPedido(remitoPedido);
        
        if (pedidoAEliminar != null) {
                //El pedido de que sus productos restablezcan el stock.
                pedidoAEliminar.restaurarStockProductos();
                //Guardo los cambios en la BD para actualizar el stock de cada producto
                for (ProductoPedido cadaProdPedido : pedidoAEliminar.getProductos()) {
                    Producto productoRestaurado = cadaProdPedido.getProducto();
                    controlPers.editarProducto(productoRestaurado);
             }
        }
        
        //Una vez restaurado los stocks de cada producto del pedido, elimino el Pedido.
        controlPers.eliminarPedido(remitoPedido);
    }

    public void eliminarProducto(String codigoProducto) {
        controlPers.eliminarProducto(codigoProducto);
    }

    public void eliminarProductoPedido(int idProductoPedido) {
        controlPers.eliminarProductoPedido(idProductoPedido);
    }
     
    public Cliente buscarCliente(String dniCliente) {
        return controlPers.buscarCliente(dniCliente);
    }

    public void editarCliente(String dni, String nombre, String celular, String email, String direccion, String localidad, String provincia, String consumidorFinal) {
        
        //Genero el Cliente a Editar. OJO, debe tener el mismo ID del Cliente ya existente debido a que es el ID y lo buscará mediante el ID en la BD.
        Cliente clienteEditado = new Cliente(dni, nombre, celular, email, direccion, localidad, provincia, consumidorFinal);
     
        /* OTRA FORMA DE HACERLO
        clienteEditado.setDni(dni);
        clienteEditado.setNombre(nombre);
        clienteEditado.setCelular(celular);
        clienteEditado.setMail(email);
        clienteEditado.setDireccion(direccion);
        clienteEditado.setLocalidad(localidad);
        clienteEditado.setCondicionIva(consumidorFinal);        
        */
        
        controlPers.editarCliente(clienteEditado);
    }

    public Producto buscarProducto(String codigoProdAEditar) {
        return controlPers.buscarProducto(codigoProdAEditar);
    }

    public void editarProducto(String codigoProducto, String nombre, String descripcion, int cantStock, double precio) {
        //Intento hacerlo de otra forma distinta a editarCliente
        
        Producto productoEditado = this.buscarProducto(codigoProducto);
        productoEditado.setNombre(nombre);
        productoEditado.setDescripcion(descripcion);
        productoEditado.setCantStock(cantStock);
        productoEditado.setPrecio(precio);
        
        controlPers.editarProducto(productoEditado);
        
    }

    public Pedido buscarPedido(int numRemito) {
        return controlPers.buscarPedido(numRemito);
    }

    public void editarPedido(int remito, Date fecha, MetodoPago formaPago, double total, List<ProductoPedido> productos, String cuitCliente) {
        
        Pedido pedidoEditado = this.buscarPedido(remito);
        pedidoEditado.setFecha(fecha);
        pedidoEditado.setMetodoDePago(formaPago);
        pedidoEditado.setTotal(total);
        pedidoEditado.setProductos(productos);
        pedidoEditado.setCliente(this.buscarCliente(cuitCliente));
        
        controlPers.editarPedido(pedidoEditado);
        
    }
    
    public void editarPedido(Pedido pedidoEditado){
      
        //Recibe el Pedido ya editado
        System.out.println("LLEGA");
        controlPers.editarPedido(pedidoEditado);
}

    public void limpiarTabla() {
        controlPers.limpiarTabla();
    }

    public ProductoPedido buscarProductosPedido(int idProductoPedido) {
        return controlPers.buscarProductosPedido(idProductoPedido);
    }

    public void editarProductoPedido(int idProdPedido, int cantProdPedido, Pedido pedido, Producto producto) {
        ProductoPedido prodPedidoEditado = this.buscarProductosPedido(idProdPedido);
        
        prodPedidoEditado.setCantProducto(cantProdPedido);
        prodPedidoEditado.setPedido(pedido);
        prodPedidoEditado.setProducto(producto);
        
        controlPers.editarProductoPedido(prodPedidoEditado);
    }


    
}
