/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.interfazregistropedidos.igu;

import com.mycompany.interfazregistropedidos.logica.ProductoPedido;


public interface Pedible {

    public void cargarTabla();
    public void actualizarPrecioTotal();

    public ProductoPedido buscarProdPedido(String nombreProductoPedido);

    public void guardarProdPedEditado(ProductoPedido prodPedidoEditado);
    
    
}
