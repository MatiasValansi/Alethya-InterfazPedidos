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
import com.mycompany.interfazregistropedidos.logica.Cliente;
import com.mycompany.interfazregistropedidos.logica.Pedido;
import com.mycompany.interfazregistropedidos.logica.ProductoPedido;
import com.mycompany.interfazregistropedidos.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matie
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    //Creo un constructor propio
    public PedidoJpaController(){
        this.emf= Persistence.createEntityManagerFactory("AlethyaPedidosPU"); //Crear una admin de entidades a partir de nuestra unidad de Persistencia.
    } 
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getProductos() == null) {
            pedido.setProductos(new ArrayList<ProductoPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getDni());
                pedido.setCliente(cliente);
            }
            List<ProductoPedido> attachedProductos = new ArrayList<ProductoPedido>();
            for (ProductoPedido productosProductoPedidoToAttach : pedido.getProductos()) {
                if (em.find(productosProductoPedidoToAttach.getClass(), productosProductoPedidoToAttach.getId()) == null) {
                    throw new IllegalArgumentException("La entidad productosProductoPedido con ID " + productosProductoPedidoToAttach.getId() + " no existe en la base de datos.");
                }
                productosProductoPedidoToAttach = em.getReference(productosProductoPedidoToAttach.getClass(), productosProductoPedidoToAttach.getId());
                attachedProductos.add(productosProductoPedidoToAttach);
            }
            pedido.setProductos(attachedProductos);
            em.persist(pedido);
            if (cliente != null) {
                cliente.getPedidos().add(pedido);
                cliente = em.merge(cliente);
            }
            for (ProductoPedido productosProductoPedido : pedido.getProductos()) {
                Pedido oldPedidoOfProductosProductoPedido = productosProductoPedido.getPedido();
                productosProductoPedido.setPedido(pedido);
                productosProductoPedido = em.merge(productosProductoPedido);
                if (oldPedidoOfProductosProductoPedido != null) {
                    oldPedidoOfProductosProductoPedido.getProductos().remove(productosProductoPedido);
                    oldPedidoOfProductosProductoPedido = em.merge(oldPedidoOfProductosProductoPedido);
                }
            }
            em.getTransaction().commit();//Asigna el N° de Remito
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getRemito());
            Cliente clienteOld = persistentPedido.getCliente();
            Cliente clienteNew = pedido.getCliente();
            List<ProductoPedido> productosOld = persistentPedido.getProductos();
            List<ProductoPedido> productosNew = pedido.getProductos();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getDni());
                pedido.setCliente(clienteNew);
            }
            List<ProductoPedido> attachedProductosNew = new ArrayList<ProductoPedido>();
            for (ProductoPedido productosNewProductoPedidoToAttach : productosNew) {
                productosNewProductoPedidoToAttach = em.getReference(productosNewProductoPedidoToAttach.getClass(), productosNewProductoPedidoToAttach.getId());
                attachedProductosNew.add(productosNewProductoPedidoToAttach);
            }
            productosNew = attachedProductosNew;
            pedido.setProductos(productosNew);
            pedido = em.merge(pedido);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getPedidos().remove(pedido);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getPedidos().add(pedido);
                clienteNew = em.merge(clienteNew);
            }
            for (ProductoPedido productosOldProductoPedido : productosOld) {
                if (!productosNew.contains(productosOldProductoPedido)) {
                    productosOldProductoPedido.setPedido(null);
                    productosOldProductoPedido = em.merge(productosOldProductoPedido);
                }
            }
            for (ProductoPedido productosNewProductoPedido : productosNew) {
                if (!productosOld.contains(productosNewProductoPedido)) {
                    Pedido oldPedidoOfProductosNewProductoPedido = productosNewProductoPedido.getPedido();
                    productosNewProductoPedido.setPedido(pedido);
                    productosNewProductoPedido = em.merge(productosNewProductoPedido);
                    if (oldPedidoOfProductosNewProductoPedido != null && !oldPedidoOfProductosNewProductoPedido.equals(pedido)) {
                        oldPedidoOfProductosNewProductoPedido.getProductos().remove(productosNewProductoPedido);
                        oldPedidoOfProductosNewProductoPedido = em.merge(oldPedidoOfProductosNewProductoPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pedido.getRemito();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getRemito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = pedido.getCliente();
            if (cliente != null) {
                cliente.getPedidos().remove(pedido);
                cliente = em.merge(cliente);
            }
            List<ProductoPedido> productos = pedido.getProductos();
            for (ProductoPedido productosProductoPedido : productos) {
                productosProductoPedido.setPedido(null);
                productosProductoPedido = em.merge(productosProductoPedido);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
