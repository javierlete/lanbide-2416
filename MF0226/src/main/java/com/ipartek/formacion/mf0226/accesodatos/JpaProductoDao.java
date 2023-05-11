package com.ipartek.formacion.mf0226.accesodatos;

import java.math.BigDecimal;
import java.util.List;

import com.ipartek.formacion.mf0226.modelos.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaProductoDao implements ProductoDao {

	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("com.ipartek.formacion.mf0226.modelos");

	@Override
	public Iterable<Producto> obtenerTodos() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		List<Producto> productos = em.createQuery("from Producto", Producto.class).getResultList();
		
		em.getTransaction().commit();
		em.close();

		return productos;
	}

	@Override
	public Producto obtenerPorId(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Producto producto = em.find(Producto.class, id);
		
		em.getTransaction().commit();
		em.close();

		return producto;
	}

	@Override
	public Producto insertar(Producto producto) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(producto);
		
		em.getTransaction().commit();
		em.close();

		return producto;
	}

	@Override
	public Producto modificar(Producto producto) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.merge(producto);
		
		em.getTransaction().commit();
		em.close();

		return producto;
	}

	@Override
	public void borrar(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.remove(em.find(Producto.class, id));
		
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Iterable<Producto> buscarPorNombre(String parteDelNombre) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		parteDelNombre = "%" + parteDelNombre + "%";
		
		// TODO: cambiar la concatenación por parámetros
		List<Producto> productos = em.createQuery("from Producto where nombre like " + parteDelNombre, Producto.class).getResultList();
		
		em.getTransaction().commit();
		em.close();

		return productos;
	}

	@Override
	public Iterable<Producto> buscarPorPrecio(BigDecimal minimo, BigDecimal maximo) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		List<Producto> productos = em.createQuery("from Producto where precio between " + minimo + " and " + maximo , Producto.class).getResultList();
		
		em.getTransaction().commit();
		em.close();

		return productos;
	}

}