package query_pageable;

import jakarta.persistence.EntityManager;
import utils.EntityManagerHandler;

// TODO. 使用JPA EntityManager实现分页查询
public class JpaPageableQuery {

    public static void main(String[] args) {
        System.out.println("Start application.");

    }

    // 准备分页查询的数据
    private static void initProducts() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();

        for (int index = 2; index < 500; index++) {
            Product product = new Product(index, "name " + index, "description " + index,
                    "location " + index, 10 + index, "url " + index, 100 + index);
            entityManager.persist(product);
        }
        entityManager.getTransaction().commit();
        EntityManagerHandler.close();
    }
}