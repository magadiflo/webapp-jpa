package org.aguzman.apiservlet.webapp.headers.services;

import jakarta.inject.Inject;
import org.aguzman.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.aguzman.apiservlet.webapp.headers.configs.RepositoryJpa;
import org.aguzman.apiservlet.webapp.headers.configs.Service;
import org.aguzman.apiservlet.webapp.headers.interceptors.TransactionalJpa;
import org.aguzman.apiservlet.webapp.headers.models.entities.Categoria;
import org.aguzman.apiservlet.webapp.headers.models.entities.Producto;
import org.aguzman.apiservlet.webapp.headers.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

@Service
@ProductoServicePrincipal
@TransactionalJpa
public class ProductoServiceImpl implements ProductoService {

    @Inject
    @RepositoryJpa
    private CrudRepository<Producto> repositoryJdbc;

    @Inject
    @RepositoryJpa
    private CrudRepository<Categoria> repositoryCategoriaJdbc;

    @Override
    public List<Producto> listar() {
        try {
            return this.repositoryJdbc.listar();
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(this.repositoryJdbc.porId(id));
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            this.repositoryJdbc.guardar(producto);
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            this.repositoryJdbc.eliminar(id);
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return this.repositoryCategoriaJdbc.listar();
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(this.repositoryCategoriaJdbc.porId(id));
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
