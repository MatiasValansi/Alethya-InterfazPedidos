/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interfazregistropedidos.persistencia;

import com.mycompany.interfazregistropedidos.exceptions.NonexistentEntityException;
import com.mycompany.interfazregistropedidos.exceptions.PreexistingEntityException;
import com.mycompany.interfazregistropedidos.logica.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.interfazregistropedidos.logica.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matie
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
        //Creo un constructor propio
    public ClienteJpaController(){
        this.emf= Persistence.createEntityManagerFactory("AlethyaPedidosPU"); //Crear una admin de entidades a partir de nuestra unidad de Persistencia.
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getPedidos() == null) {
            cliente.setPedidos(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedido> attachedPedidos = new ArrayList<Pedido>();
            for (Pedido pedidosPedidoToAttach : cliente.getPedidos()) {
                pedidosPedidoToAttach = em.getReference(pedidosPedidoToAttach.getClass(), pedidosPedidoToAttach.getRemito());
                attachedPedidos.add(pedidosPedidoToAttach);
            }
            cliente.setPedidos(attachedPedidos);
            em.persist(cliente);
            for (Pedido pedidosPedido : cliente.getPedidos()) {
                Cliente oldClienteOfPedidosPedido = pedidosPedido.getCliente();
                pedidosPedido.setCliente(cliente);
                pedidosPedido = em.merge(pedidosPedido);
                if (oldClienteOfPedidosPedido != null) {
                    oldClienteOfPedidosPedido.getPedidos().remove(pedidosPedido);
                    oldClienteOfPedidosPedido = em.merge(oldClienteOfPedidosPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getDni()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getDni());
            List<Pedido> pedidosOld = persistentCliente.getPedidos();
            List<Pedido> pedidosNew = cliente.getPedidos();
            List<Pedido> attachedPedidosNew = new ArrayList<Pedido>();
            for (Pedido pedidosNewPedidoToAttach : pedidosNew) {
                pedidosNewPedidoToAttach = em.getReference(pedidosNewPedidoToAttach.getClass(), pedidosNewPedidoToAttach.getRemito());
                attachedPedidosNew.add(pedidosNewPedidoToAttach);
            }
            pedidosNew = attachedPedidosNew;
            cliente.setPedidos(pedidosNew);
            cliente = em.merge(cliente);
            for (Pedido pedidosOldPedido : pedidosOld) {
                if (!pedidosNew.contains(pedidosOldPedido)) {
                    pedidosOldPedido.setCliente(null);
                    pedidosOldPedido = em.merge(pedidosOldPedido);
                }
            }
            for (Pedido pedidosNewPedido : pedidosNew) {
                if (!pedidosOld.contains(pedidosNewPedido)) {
                    Cliente oldClienteOfPedidosNewPedido = pedidosNewPedido.getCliente();
                    pedidosNewPedido.setCliente(cliente);
                    pedidosNewPedido = em.merge(pedidosNewPedido);
                    if (oldClienteOfPedidosNewPedido != null && !oldClienteOfPedidosNewPedido.equals(cliente)) {
                        oldClienteOfPedidosNewPedido.getPedidos().remove(pedidosNewPedido);
                        oldClienteOfPedidosNewPedido = em.merge(oldClienteOfPedidosNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cliente.getDni();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getDni();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Pedido> pedidos = cliente.getPedidos();
            for (Pedido pedidosPedido : pedidos) {
                pedidosPedido.setCliente(null);
                pedidosPedido = em.merge(pedidosPedido);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
