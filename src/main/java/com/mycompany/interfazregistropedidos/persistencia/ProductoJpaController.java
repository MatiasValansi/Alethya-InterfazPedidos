/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.persistencia;

import com.mycompany.interfazregistropedidos.logica.Producto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.interfazregistropedidos.logica.ProductoPedido;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.IllegalOrphanException;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.NonexistentEntityException;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matie
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    //Creo un constructor propio
    public ProductoJpaController(){
        this.emf= Persistence.createEntityManagerFactory("AlethyaPedidosPU"); //Crear una admin de entidades a partir de nuestra unidad de Persistencia.
    } 
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getPedidos() == null) {
            producto.setPedidos(new ArrayList<ProductoPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoPedido> attachedPedidos = new ArrayList<ProductoPedido>();
            for (ProductoPedido pedidosProductoPedidoToAttach : producto.getPedidos()) {
                pedidosProductoPedidoToAttach = em.getReference(pedidosProductoPedidoToAttach.getClass(), pedidosProductoPedidoToAttach.getId());
                attachedPedidos.add(pedidosProductoPedidoToAttach);
            }
            producto.setPedidos(attachedPedidos);
            em.persist(producto);
            for (ProductoPedido pedidosProductoPedido : producto.getPedidos()) {
                Producto oldProductoOfPedidosProductoPedido = pedidosProductoPedido.getProducto();
                pedidosProductoPedido.setProducto(producto);
                pedidosProductoPedido = em.merge(pedidosProductoPedido);
                if (oldProductoOfPedidosProductoPedido != null) {
                    oldProductoOfPedidosProductoPedido.getPedidos().remove(pedidosProductoPedido);
                    oldProductoOfPedidosProductoPedido = em.merge(oldProductoOfPedidosProductoPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getCodigo()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getCodigo());
            List<ProductoPedido> pedidosOld = persistentProducto.getPedidos();
            List<ProductoPedido> pedidosNew = producto.getPedidos();
            List<String> illegalOrphanMessages = null;
            for (ProductoPedido pedidosOldProductoPedido : pedidosOld) {
                if (!pedidosNew.contains(pedidosOldProductoPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoPedido " + pedidosOldProductoPedido + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoPedido> attachedPedidosNew = new ArrayList<ProductoPedido>();
            for (ProductoPedido pedidosNewProductoPedidoToAttach : pedidosNew) {
                pedidosNewProductoPedidoToAttach = em.getReference(pedidosNewProductoPedidoToAttach.getClass(), pedidosNewProductoPedidoToAttach.getId());
                attachedPedidosNew.add(pedidosNewProductoPedidoToAttach);
            }
            pedidosNew = attachedPedidosNew;
            producto.setPedidos(pedidosNew);
            producto = em.merge(producto);
            for (ProductoPedido pedidosNewProductoPedido : pedidosNew) {
                if (!pedidosOld.contains(pedidosNewProductoPedido)) {
                    Producto oldProductoOfPedidosNewProductoPedido = pedidosNewProductoPedido.getProducto();
                    pedidosNewProductoPedido.setProducto(producto);
                    pedidosNewProductoPedido = em.merge(pedidosNewProductoPedido);
                    if (oldProductoOfPedidosNewProductoPedido != null && !oldProductoOfPedidosNewProductoPedido.equals(producto)) {
                        oldProductoOfPedidosNewProductoPedido.getPedidos().remove(pedidosNewProductoPedido);
                        oldProductoOfPedidosNewProductoPedido = em.merge(oldProductoOfPedidosNewProductoPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = producto.getCodigo();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProductoPedido> pedidosOrphanCheck = producto.getPedidos();
            for (ProductoPedido pedidosOrphanCheckProductoPedido : pedidosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoPedido " + pedidosOrphanCheckProductoPedido + " in its pedidos field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
