/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.parking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author thorsten
 */
public class EntityHelper {

    private static final String PERSISTENCE_UNIT_NAME = "pu";
    private static EntityManagerFactory factory = null;

    public static EntityManager getEntitymanager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory.createEntityManager();
    }

    public static Object persist(Object object) {
        EntityManager entitymanager = getEntitymanager();
        entitymanager.getTransaction().begin();
        entitymanager.persist(object);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        return object;
    }

    public static Object merge(Object object) {
        EntityManager entitymanager = getEntitymanager();
        entitymanager.getTransaction().begin();
        entitymanager.merge(object);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        return object;
    }

    public static void clearTable(String tableName) {
        EntityManager entitymanager = EntityHelper.getEntitymanager();
        entitymanager.getTransaction().begin();
        entitymanager.createNativeQuery("DELETE FROM " + tableName).executeUpdate();
        entitymanager.getTransaction().commit();
        entitymanager.close();
    }

    public static Object reload(Class entityClass, Long pKey) {
        EntityManager entitymanager = getEntitymanager();
        entitymanager.getTransaction().begin();
        Object entity = entitymanager.find(entityClass, pKey);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        return entity;
    }

    public static void clearCache() {
        getEntitymanager().getEntityManagerFactory().getCache().evictAll();
    }

    public static void delete(Class entityClass, Long pKey) {
        EntityManager entitymanager = getEntitymanager();
        entitymanager.getTransaction().begin();
        Object entity = entitymanager.find(entityClass, pKey);
        entitymanager.remove(entity);
        entitymanager.getTransaction().commit();
        entitymanager.close();
    }
}
