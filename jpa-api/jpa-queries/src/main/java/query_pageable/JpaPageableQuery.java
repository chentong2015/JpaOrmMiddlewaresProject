package query_pageable;

import jakarta.persistence.EntityManager;
import utils.EntityManagerHandler;

import java.util.List;

// TODO. 使用JPA API实现分页查询 -> 兼容底层不同DB数据库
public class JpaPageableQuery {

    public static void main(String[] args) {
        System.out.println("Start application.");

        List<Product> products = getProductsPageableJpqlQuery();
        for (Product product: products) {
            System.out.println(product);
        }
    }

    // TODO. 使用JPQL语句实现分页查询
    private static List<Product> getProductsPageableJpqlQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        String query = "Select p from Product p where p.price > :price order by p.id";

        return entityManager.createQuery(query, Product.class)
                .setParameter("price", 1)
                .setFirstResult(1) // Page Offset
                .setMaxResults(20) // Page Size
                .getResultList();
    }

    // 使用Native Query需要做类型转换
    @SuppressWarnings("unchecked")
    private static List<Product> getProductsPageableNativeQuery() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        String query = "SELECT * FROM t_product WHERE price > :price";

        return (List<Product>) em.createNativeQuery(query, Product.class)
                .setParameter("price", 10)
                .setFirstResult(1)
                .setMaxResults(20)
                .getResultList();
    }

    // 插入数据时需要提供事务提交
    private static void initProducts() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();

        for (int index = 1; index < 500; index++) {
            Product product = new Product(index, "name " + index, "description " + index,
                    "location " + index, 10 + index, "url " + index, 100 + index);
            entityManager.persist(product);
        }
        entityManager.getTransaction().commit();
        EntityManagerHandler.close();
    }
}