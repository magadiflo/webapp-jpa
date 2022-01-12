package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.models.entities.Usuario;

import java.util.List;

@Repository
public class UsuarioRepositoryJpaImpl implements UsuarioRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Usuario> listar() throws Exception {
        return this.em.createQuery("FROM Usuario", Usuario.class).getResultList();
    }

    @Override
    public Usuario porId(Long id) throws Exception {
        return this.em.find(Usuario.class, id);
    }

    @Override
    public void guardar(Usuario usuario) throws Exception {
        if (usuario.getId() != null && usuario.getId() > 0) {
            this.em.merge(usuario);
        } else {
            this.em.persist(usuario);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.em.remove(this.porId(id));
    }

    @Override
    public Usuario porUsername(String username) throws Exception {
        return this.em.createQuery("SELECT u FROM Usuario AS u WHERE u.username = :username", Usuario.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
