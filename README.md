# 🧾 Alethya Pedidos

**Alethya Pedidos** es una aplicación de escritorio desarrollada en **Java Swing** con persistencia en **MySQL** mediante **JPA (EclipseLink)**. Está pensada para la gestión interna de pedidos, productos y clientes en un comercio, automatizando tareas repetitivas y mejorando el control de stock y facturación.

---

## ⚙️ Tecnologías utilizadas

- **Java 17**  
- **Java Swing (GUI)**  
- **JPA + EclipseLink**  
- **MySQL 8**  
- **Maven**  
- **WampServer**  
- **FlatLaf 3.5.4**  
- **Launch4J** (para generar el ejecutable `.exe`)  

---

## ✅ Funcionalidades Actuales

### 🛍️ Gestión de Productos
- Alta, baja y modificación de productos.
- Visualización de todos los productos en una tabla.
- Control de stock, código (String como ID), descripción y precio.

### 👤 Gestión de Clientes
- Registro, modificación y eliminación de clientes.
- Visualización completa en tabla con todos sus datos personales.

### 📦 Armar Pedido
- Asignación de uno o más productos a un cliente.
- Selección de forma de pago (con o sin factura).
- Cálculo automático del total y cantidad de productos.
- Generación automática del **remito (ID del pedido)**.
- Control persistente de la relación Pedido ↔ Productos ↔ Cliente.

### 📂 Exportación y Deploy
- La app se empaqueta como `.exe` junto con su base de datos MySQL.
- Incluye un instalador completo con Java, WampServer y el sistema listo para usar.

---

## 🌱 Funcionalidades futuras (en desarrollo o por implementar)

> Estas ideas están pensadas para mejorar la experiencia de usuario y profesionalizar aún más la aplicación.

### 📊 Exportar a Excel
- Desde cada vista de listado (Clientes, Productos, Pedidos).
- Exportar las tablas de la base de datos a archivos `.xlsx`.

### 🧾 Exportar Pedido a PDF
- Al armar un pedido, generar automáticamente un PDF tipo factura/remito con:
  - Nombre del cliente
  - Fecha
  - Número de remito
  - Productos comprados
  - Total a pagar
  - Forma de pago y si requiere factura

### 🚫 Validaciones y manejo de errores
- Añadir excepciones y alertas gráficas si:
  - Falta completar campos obligatorios (nombre del cliente, código del producto, etc.)
  - Se intenta guardar un pedido sin productos.

### 🔍 Búsquedas inteligentes
- En la vista de "Armar Pedido", permitir:
  - Buscar clientes por DNI, nombre o apellido.
  - Buscar productos por código o su nombre mientras se escribe.

---

## 📁 Estructura del proyecto

- `logica/` → Clases del dominio (Cliente, Producto, Pedido, etc.)
- `persistencia/` → Controladoras JPA + conexión
- `igu/` → Interfaces gráficas desarrolladas en Swing
- `META-INF/persistence.xml` → Configuración de la persistencia
- `pom.xml` → Configuración Maven con dependencias y main class
- `.sql` → Dump de base de datos `alethya_pedidos.sql` para importación rápida

---

## 🚀 Instalación

1. Ejecutar el instalador generado con Inno Setup.
2. Este incluye:
   - WampServer (con MySQL)
   - La base de datos preconfigurada
   - La aplicación `.exe` (no requiere abrir con Java)
3. Iniciar WampServer y ejecutar la app normalmente.

---

## 🙋 Sobre el autor

Desarrollado por **Matías Valansi** como proyecto de uso real para automatizar la gestión de pedidos en un comercio familiar.

📌 Objetivo: Es un proyecto en el cual se pone en práctica lo aprendido en el 1° año de la carrera de Analista de Sistemas. 
Java Avanzado, Modelo de Capas, CRUD, Bases de Datos incluyendo librerías tales como Java Swing y JPA.
Aún se encuentra en fase beta.

---


