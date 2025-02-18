/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.interfazregistropedidos.igu;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
Esta interfaz incluye los métodos que se repiten en los distintos menúes en los que se ven los datos de la Base de Datos
 */
public interface Cargable {
    
    /*
    Este método será el encargado de cargar los datos de la base de datos en una tabla visible de la interfaz gráfica.
    */
    //void cargarTabla();
    
    /*
    Itero la lista para añadir cada elemento a la modeloTabla
    */
    //void agregarElementos(DefaultTableModel modeloTabla, List listaElementos);
    
    /*
    Volverá al menú principal al presionar el botón
    */
    public void volverAlMenu();
}
