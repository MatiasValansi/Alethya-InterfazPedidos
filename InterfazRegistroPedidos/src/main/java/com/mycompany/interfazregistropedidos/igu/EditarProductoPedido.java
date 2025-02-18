/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.interfazregistropedidos.igu;

import com.mycompany.interfazregistropedidos.exceptions.StockInvalidoException;
import com.mycompany.interfazregistropedidos.logica.ControladoraLogica;
import com.mycompany.interfazregistropedidos.logica.Pedido;
import com.mycompany.interfazregistropedidos.logica.Producto;
import com.mycompany.interfazregistropedidos.logica.ProductoPedido;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author matie
 */
public class EditarProductoPedido extends javax.swing.JFrame {

    ControladoraLogica controladora;
    String nombreProductoPedido;
    Pedible pantallaArmarPedidoFondo;
    int MINIMO = 0;
    
    /*
    Recibe como parametro el nombre del ProductoPedido a editar y la pantalla del ArmarPedido para que una vez editado este, se actualice la tabla
    */
    public EditarProductoPedido(String nombreProductoPedido, Pedible pantallaArmarPedidoFondo) {
        this.controladora = new ControladoraLogica();
        this.pantallaArmarPedidoFondo = pantallaArmarPedidoFondo;
        this.nombreProductoPedido = nombreProductoPedido;
        initComponents();
        this.cargarDatosEdicion(this.nombreProductoPedido);
       }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCantProducto = new javax.swing.JLabel();
        spinCantProducto = new javax.swing.JSpinner();
        lblNombreProducto = new javax.swing.JLabel();
        btnGuardarCambios = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblCantProducto.setText("Seleccione Cantidad de");

        lblNombreProducto.setText("NOMBRE_PRODUCTO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCantProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombreProducto)
                .addGap(42, 42, 42)
                .addComponent(spinCantProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantProducto)
                    .addComponent(spinCantProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardarCambios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        ProductoPedido prodPedidoEditado = pantallaArmarPedidoFondo.buscarProdPedido(this.nombreProductoPedido);//controladora.buscarProductosPedido(this.idProductoPedido);

        try {
            
        int cantProdPedido = (int) this.spinCantProducto.getValue();
        if (cantProdPedido > prodPedidoEditado.obtenerCantStock() || cantProdPedido <= MINIMO) {
            throw new StockInvalidoException("Stock Inválido");
        }
        prodPedidoEditado.getProducto().descontarStock(cantProdPedido);
        
            
        if (prodPedidoEditado != null) {
            prodPedidoEditado.setCantProducto((int) this.spinCantProducto.getValue());
        }
        pantallaArmarPedidoFondo.guardarProdPedEditado(prodPedidoEditado);//el ProductoPedido editado actualiza a dicho ProductoEditado en la lista de productosEditados de ArmarPedido
        this.mostrarCartel();
        this.setVisible(false);
        pantallaArmarPedidoFondo.cargarTabla(); //Se actualizará la tabla del ArmarPedido con la cantidad del ProductoPedido actualizada
        pantallaArmarPedidoFondo.actualizarPrecioTotal();    
            
        } catch (StockInvalidoException e) {
            
            JOptionPane optionPane = new JOptionPane( "La cantidad del producto debe ser mayor a 0 y menor al stock disponible: " + prodPedidoEditado.obtenerCantStock());//Cuerpo del mensaje 
            optionPane.setMessageType (JOptionPane.ERROR_MESSAGE); //Muestro la ventana 
            JDialog dialog = optionPane.createDialog("Error de Stock"); //Titulo
            dialog.setAlwaysOnTop (true); // Que aparezca la ventana sobre el resto
            dialog.setVisible(true); //Que sea visible
        
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnGuardarCambios;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCantProducto;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JSpinner spinCantProducto;
    // End of variables declaration//GEN-END:variables

    private void cargarDatosEdicion(String nombreProductoPedido) {
        ProductoPedido prodPedidoAEditar = pantallaArmarPedidoFondo.buscarProdPedido(nombreProductoPedido);//controladora.buscarProductosPedido(idProductoPedido);
        
        this.lblNombreProducto.setText(prodPedidoAEditar.getProducto().getNombre());
        this.spinCantProducto.setValue(prodPedidoAEditar.getCantProducto());
    }

    private void mostrarCartel() {
        JOptionPane optionPane = new JOptionPane("Se editó el producto " + this.lblNombreProducto.getText() + " correctamente");//Cuerpo del mensaje 
        optionPane.setMessageType (JOptionPane.INFORMATION_MESSAGE); //Muestro la ventana 
        JDialog dialog = optionPane.createDialog("Guardado de Edición Exitoso"); //Titulo
        dialog.setAlwaysOnTop (true); // Que aparezca la ventana sobre el restp
        dialog.setVisible(true); //Que sea visible
    }

    private void cargarVentanaArmarPedido(ArmarPedido pantalla) {
        pantalla.setVisible(true);
        pantalla.setLocationRelativeTo(null);
    }
    
    

}
