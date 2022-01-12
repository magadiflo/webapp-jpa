package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.models.entities.Categoria;

import java.util.List;

@Repository
public class CategoriaRepositoryJpaImpl implements CrudRepository<Categoria> {

    @Inject
    private EntityManager em;

    @Override
    public List<Categoria> listar() throws Exception {
        return this.em.createQuery("FROM Categoria", Categoria.class).getResultList();
    }

    @Override
    public Categoria porId(Long id) throws Exception {
        return this.em.find(Categoria.class, id);
    }

    @Override
    public void guardar(Categoria categoria) throws Exception {
        if(categoria.getId() != null && categoria.getId() > 0){
            this.em.merge(categoria);
        } else {
            this.em.persist(categoria);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.em.remove(this.porId(id));
    }
}
