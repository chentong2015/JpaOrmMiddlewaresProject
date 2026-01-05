package com.entity.manager.threadsafe;

import com.entity.manager.EntityManagerHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 为不同的线程创建独立的EntityManager对象，并发执行各自查询
public class EntityManagerThreadSafe {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int index = 1; index < 10; index++) {
            String query = "Update Book set name = 'new name' where id = " + index;
            executorService.execute(() -> {
                // Create new EntityManager for multiThreads and close at the end
                EntityManager entityManagerSafe = entityManagerFactory.createEntityManager();
                entityManagerSafe.createQuery(query).executeUpdate();
                entityManagerSafe.close();
            });
        }
        EntityManagerHandler.close();
    }
}
