
package com.mycompany.interfazregistropedidos.logica;
import java.io.FileWriter;
import java.io.IOException;


/**
 Esta clase fue creada con el objetivo de poder exportar archivos CSV
 */
public class Excel {

public static void exportarCSV(){
        String archivoCSV = "datos.csv";
        String separador = "|";

        try (FileWriter writer = new FileWriter(archivoCSV)) {
            // Encabezados
            writer.append("Nombre").append(separador).append("Edad").append(separador).append("Ciudad").append("\n");

            // Datos
            writer.append("Juan").append(separador).append("25").append(separador).append("Buenos Aires").append("\n");
            writer.append("Ana").append(separador).append("30").append(separador).append("Córdoba").append("\n");

            System.out.println("Archivo CSV generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
}
