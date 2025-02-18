/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.interfazregistropedidos.logica.Pedido;
import com.mycompany.interfazregistropedidos.logica.Producto;
import com.mycompany.interfazregistropedidos.logica.ProductoPedido;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author matie
 */
public class ProductoPedidoJpaController implements Serializable {

    public ProductoPedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    //Creo un constructor propio
    public ProductoPedidoJpaController(){
        this.emf= Persistence.createEntityManagerFactory("AlethyaPedidosPU"); //Crear una admin de entidades a partir de nuestra unidad de Persistencia.
    }

    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Corregido por Chat GPT
    public void create(ProductoPedido productoPedido) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();

        // Validar que el ProductoPedido no sea duplicado
        if (productoPedido.getPedido() != null && productoPedido.getProducto() != null) {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(pp) FROM ProductoPedido pp WHERE pp.pedido = :pedido AND pp.producto = :producto", Long.class
            );
            query.setParameter("pedido", productoPedido.getPedido());
            query.setParameter("producto", productoPedido.getProducto());
            long count = query.getSingleResult();

            if (count > 0) {
                try {
                    //throw new RuntimeException("El ProductoPedido ya existe en la base de datos.");
                    this.edit(productoPedido);
                } catch (Exception ex) {
                    Logger.getLogger(ProductoPedidoJpaController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("El ProductoPedido ya existe en la base de datos y no se pudo editar.");
                }
            }
        }

        // Persistir ProductoPedido
        em.persist(productoPedido);

        // Actualizar relaciones inversas (en memoria, si es necesario)
        Pedido pedido = productoPedido.getPedido();
        if (pedido != null && !pedido.getProductos().contains(productoPedido)) {
            pedido.getProductos().add(productoPedido);
        }

        Producto producto = productoPedido.getProducto();
        if (producto != null && !producto.getPedidos().contains(productoPedido)) {
            producto.getPedidos().add(productoPedido);
        }

        // Confirmar transacción
        em.getTransaction().commit(); //Asigna el ID a ProductoPedido
    } finally {
        if (em != null) {
            em.close();
        }
    }
}

    
    /*
    public void create(ProductoPedido productoPedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = productoPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getRemito());
                productoPedido.setPedido(pedido);
            }
            Producto producto = productoPedido.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getCodigo());
                productoPedido.setProducto(producto);
            }
            em.persist(productoPedido);
            if (pedido != null) {
                pedido.getProductos().add(productoPedido);
                pedido = em.merge(pedido);
            }
            if (producto != null) {
                producto.getPedidos().add(productoPedido);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public void edit(ProductoPedido productoPedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoPedido persistentProductoPedido = em.find(ProductoPedido.class, productoPedido.getId());
            Pedido pedidoOld = persistentProductoPedido.getPedido();
            Pedido pedidoNew = productoPedido.getPedido();
            Producto productoOld = persistentProductoPedido.getProducto();
            Producto productoNew = productoPedido.getProducto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getRemito());
                productoPedido.setPedido(pedidoNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getCodigo());
                productoPedido.setProducto(productoNew);
            }
            productoPedido = em.merge(productoPedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getProductos().remove(productoPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getProductos().add(productoPedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getPedidos().remove(productoPedido);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getPedidos().add(productoPedido);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = productoPedido.getId();
                if (findProductoPedido(id) == null) {
                    throw new NonexistentEntityException("The productoPedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoPedido productoPedido;
            try {
                productoPedido = em.getReference(ProductoPedido.class, id);
                productoPedido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = productoPedido.getPedido();
            if (pedido != null) {
                pedido.getProductos().remove(productoPedido);
                pedido = em.merge(pedido);
            }
            Producto producto = productoPedido.getProducto();
            if (producto != null) {
                producto.getPedidos().remove(productoPedido);
                producto = em.merge(producto);
            }
            em.remove(productoPedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoPedido> findProductoPedidoEntities() {
        return findProductoPedidoEntities(true, -1, -1);
    }

    public List<ProductoPedido> findProductoPedidoEntities(int maxResults, int firstResult) {
        return findProductoPedidoEntities(false, maxResults, firstResult);
    }

    private List<ProductoPedido> findProductoPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoPedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProductoPedido findProductoPedido(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoPedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoPedido> rt = cq.from(ProductoPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
            /*
    Añado un método para eliminar todos los registros de la tabla ProductoPedido
    */
    public void deleteAllProductoPedidos() {
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM ProductoPedido").executeUpdate();
        em.getTransaction().commit();
    } finally {
        if (em != null) {
            em.close();
        }
        }
    }
    
}
