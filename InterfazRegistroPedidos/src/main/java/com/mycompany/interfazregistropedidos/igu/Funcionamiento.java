/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.interfazregistropedidos.igu;

/**
Esta interfaz incluye los métodos que se repiten en los distintos menúes, como "MostrarCartel" y "VolverAlMenu"
 */
public interface Funcionamiento {
    
    /*
    Mostrará un cartel al presionar un botón. 
    EJ: En el menú ArmarPedido, al presionar el botón 'Armar Pedido', lanzará un cartel indicando que el Pedido fue añadido con éxito
    */
    public void mostrarCartel();
    
    /*
    Reiniciará el menú
    */
    public void limpiar();
    
   /*
    Volverá al menú principal al presionar el botón
    */
    public void volverAlMenu();
}
