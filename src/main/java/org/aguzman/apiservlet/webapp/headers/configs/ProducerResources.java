package org.aguzman.apiservlet.webapp.headers.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.aguzman.apiservlet.webapp.headers.util.JpaUtil;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerResources {

    @Inject
    private Logger log;

    @Resource(name = "jdbc/mysqlDB")
    private DataSource ds;

    @Produces
    @RequestScoped
    @MysqlConn
    private Connection beanConnection () throws NamingException, SQLException {
        return this.ds.getConnection();
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    public void close(@Disposes @MysqlConn Connection conn) throws SQLException {
        conn.close();
        log.info("Cerrando la conexión a la BD MySQL!!");
    }

    @Produces
    @RequestScoped
    private EntityManager beanEntityManager(){
        return JpaUtil.getEntityManager();
    }

    public void close(@Disposes EntityManager em){
        if(em.isOpen()){
            em.close();
            log.info("Cerrando la conexión del EntityManager");
        }
    }
}
