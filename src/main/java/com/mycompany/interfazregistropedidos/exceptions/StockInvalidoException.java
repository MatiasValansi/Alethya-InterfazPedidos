/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.exceptions;

    /*
    Creo una excpepcíon personalizada para la cantidad inválida de stock
    */
    public class StockInvalidoException extends Exception {
        public StockInvalidoException(String mensaje) {
            super(mensaje);
        }
    }