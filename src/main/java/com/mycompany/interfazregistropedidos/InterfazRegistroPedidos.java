package com.mycompany.interfazregistropedidos;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.interfazregistropedidos.igu.Principal;
import javax.swing.UIManager;

public class InterfazRegistroPedidos {
    
     public static void main(String[] args) {
       
      
       /*
         Importo la librería FlatLaf para que el diseño sea moderno
       */
       
       try {
           UIManager.setLookAndFeel(new FlatIntelliJLaf());
       } catch( Exception ex ) {
           System.err.println( "Failed to initialize LaF" );
       }
       
       
       Principal principal = new Principal();
       principal.setVisible(true);
    }
    
}
