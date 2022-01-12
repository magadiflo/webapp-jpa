package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.configs.RepositoryJpa;
import org.aguzman.apiservlet.webapp.headers.models.entities.Producto;

import java.util.List;

@RepositoryJpa
@Repository
public class ProductoRepositoryJpaImpl implements CrudRepository<Producto> {

    @Inject
    private EntityManager em;

    @Override
    public List<Producto> listar() throws Exception {
        return this.em.createQuery("FROM Producto", Producto.class).getResultList();
    }

    @Override
    public Producto porId(Long id) throws Exception {
        return this.em.find(Producto.class, id);
    }

    @Override
    public void guardar(Producto producto) throws Exception {
        if(producto.getId() != null && producto.getId() > 0){
            this.em.merge(producto);
        } else {
            this.em.persist(producto);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.em.remove(this.porId(id));
    }
}
