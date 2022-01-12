package org.aguzman.apiservlet.webapp.headers.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory emf = JpaUtil.buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("HibernateJpaPU");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
