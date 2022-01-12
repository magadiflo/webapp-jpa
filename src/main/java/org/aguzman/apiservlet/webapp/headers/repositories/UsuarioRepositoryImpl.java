package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import org.aguzman.apiservlet.webapp.headers.configs.MysqlConn;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.configs.RepositoryJdbc;
import org.aguzman.apiservlet.webapp.headers.models.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RepositoryJdbc
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Inject
    @MysqlConn
    private Connection conn;

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try(Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios")){
            while(rs.next()){
                Usuario usuario = this.getUsuario(rs);
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try(PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?")){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    usuario = this.getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;
        if(usuario.getId() != null && usuario.getId() > 0){
            sql = "UPDATE usuarios SET username = ?, password = ?, email = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO usuarios(username, password, email) VALUES(?, ?, ?)";
        }
        try(PreparedStatement ps = this.conn.prepareStatement(sql)){
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getEmail());
            if(usuario.getId() != null && usuario.getId() > 0){
                ps.setLong(4, usuario.getId());
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement ps = this.conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM usuarios WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = this.getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    private Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }

}
